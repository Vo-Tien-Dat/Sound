package com.music.sound.DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.music.sound.config.Constant;
import com.music.sound.model.User;
import com.music.sound.security.EncryptAndDecrypt.DES;
import com.music.sound.security.EncryptAndDecrypt.DESAlgorithm;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import java.lang.Object;
import java.util.List;
import java.util.ArrayList;
import com.music.sound.security.EncryptAndDecrypt.DES;

@Repository
public class UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    // sql
    private final String SQL_ALL_USER = "call SP_ALL_USER()";

    private final String SQL_READ_ALL_ROLE_BY_ID_USER_FROM_USER_ROLE = "call SP_READ_ALL_ROLE_BY_ID_USER_FROM_USER_ROLE(?)";

    private final String SQL_FIND_USER_BY_ID_USER = "call SP_FIND_USER_BY_ID_USER(?)";

    private final String SQL_FIND_USER_BY_USERNAME = "call SP_FIND_USER_BY_USERNAME(?)";

    private final String SQL_READ_USER_BY_USERNAME = "call SP_READ_USER_BY_USERNAME(?)";

    private final String SQL_UPDATE_USER_BY_ID_USER = "call SP_UPDATE_USER_BY_ID_USER(?,?,?,?,?,?)";

    private final String SQL_UPDATE_USER_NAME_AND_EMAIL_AND_NAME_USER_BY_ID_USER = "call SP_UPDATE_USER_NAME_AND_EMAIL_AND_NAME_USER_BY_ID_USER(?,?,?,?)";

    private final String SQL_DELETE_USER_BY_ID_USER = "call SP_DELETE_USER_BY_ID_USER(?)";

    private final String SQL_UPDATE_PATH_IMAGE_BY_ID_USER = "call SP_UPDATE_PATH_IMAGE_BY_ID_USER(?,?)";

    private final String SQL_UPDATE_PASSWORD_BY_ID_USER = "call SP_UPDATE_PASSWORD_BY_ID_USER(?,?)";

    // feature: fina all user

    public List<User> findAllUser() {
        List<User> records = new ArrayList<>();
        records = jdbcTemplate.query(SQL_ALL_USER, new UserMapper());
        return records;
    }

    public List<UserDTO> readAllUser() {
        List<UserDTO> records = new ArrayList<>();
        records = jdbcTemplate.query(SQL_ALL_USER, new UserReadMapper());
        return records;
    }

    public List<RoleDTO> readAllRoleByIdUserFromUserRole(String idUser) {
        List<RoleDTO> records = new ArrayList<>();
        records = jdbcTemplate.query(SQL_READ_ALL_ROLE_BY_ID_USER_FROM_USER_ROLE, new UserRoleMapper(),
                new Object[] { idUser });
        return records;
    }

    public UserDTO readUserByUsername(String username) throws Exception {
        DES des = new DES(Constant.KEY_DES);
        String encryptUserName = des.encrypt(username);
        UserDTO record = new UserDTO();
        record = jdbcTemplate.queryForObject(SQL_READ_USER_BY_USERNAME, new UserReadMapper(), encryptUserName);
        String password = record.getPassword();
        String decryptPassword = des.decrypt(password);
        record.setPassword(decryptPassword);
        return record;
    }

    // feature: show information user
    public User findUserById(String id) {
        User result = (User) jdbcTemplate.queryForObject(SQL_FIND_USER_BY_ID_USER,
                new BeanPropertyRowMapper<>(User.class),
                new Object[] { id });
        return result;
    }

    public UserDTO readUserByIdUser(String idUser) throws Exception {
        DES des = new DES(Constant.KEY_DES);
        UserDTO record = (UserDTO) jdbcTemplate.queryForObject(SQL_FIND_USER_BY_ID_USER, new UserReadMapper(), idUser);
        String userName = record.getUserName();
        String password = record.getPassword();
        String decryptUserName = des.decrypt(userName);
        String decryptPassword = des.decrypt(password);
        record.setUserName(decryptUserName);
        record.setPassword(decryptPassword);
        return record;
    }

    // tinhs năng: lấy thông tin của user qua tên
    public User findUserByUsername(String username) {

        User result = (User) jdbcTemplate.queryForObject(SQL_FIND_USER_BY_USERNAME,
                new UserMapper(),
                username);
        return result;
    }

    // feature: use register User
    public void insertUser(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        try {
            entityManager.persist(user);
            entityTransaction.commit();
        } catch (Exception ex) {
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    public void insertUserByUsernameAndPasswordAndNameUser(User user) throws Exception {
        DES des = new DES(Constant.KEY_DES);
        String userName = user.getUserName();
        String password = user.getPassword();
        String encryptUserName = des.encrypt(userName);
        String encryptPassword = des.encrypt(password);
        user.setUserName(encryptUserName);
        user.setPassword(encryptPassword);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(user);
        entityTransaction.commit();
        entityManager.close();
    }

    public String getIdUserWhileCreateUser(User user) throws Exception {
        String idUser = null;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        try {
            String userName = user.getUserName();
            String password = user.getPassword();

            DES des = new DES(Constant.KEY_DES);
            String encryptUserName = des.encrypt(userName);
            String encryptPassword = des.encrypt(password);

            user.setUserName(encryptUserName);
            user.setPassword(encryptPassword);
            entityManager.persist(user);

            entityTransaction.commit();
            idUser = user.getId().toString();
        } catch (Exception ex) {
            entityTransaction.rollback();
            throw new Exception();
        } finally {
            entityManager.close();
        }
        return idUser;
    }

    // feature: update User
    public void updateUser(UserDTO user) {
        String idUser = user.getIdUser();
        String userName = user.getUserName();
        String password = user.getPassword();
        String email = user.getEmail();
        String nameUser = user.getNameUser();
        String description = user.getDescription();
        jdbcTemplate.update(SQL_UPDATE_USER_BY_ID_USER, userName, password, email, nameUser, description, idUser);
    }

    public void deleteUserByIdUser(String idUser) {
        jdbcTemplate.update(SQL_DELETE_USER_BY_ID_USER, idUser);
    }

    public void updatePathImageByIdUser(String idUser, String pathImage) {
        jdbcTemplate.update(SQL_UPDATE_PATH_IMAGE_BY_ID_USER, pathImage, idUser);
    }

    public void updatePasswordbyIdUser(UserDTO user) throws Exception {
        DES des = new DES(Constant.KEY_DES);
        String password = user.getPassword();
        String encryptPassword = des.encrypt(password);
        String idUser = user.getIdUser();
        jdbcTemplate.update(SQL_UPDATE_PASSWORD_BY_ID_USER, encryptPassword, idUser);
    }

    public void updatePathImageByIdUser(UserDTO user) {
        String pathImage = user.getPathImage();
        String idUser = user.getIdUser();
        jdbcTemplate.update(SQL_UPDATE_PATH_IMAGE_BY_ID_USER, pathImage, idUser);
    }

    public void updateUserNameAndEmailAndNameUserByIdUser(UserDTO user) throws Exception {
        DES des = new DES(Constant.KEY_DES);
        String userName = user.getUserName();
        String encryptUserName = des.encrypt(userName);
        String email = user.getEmail();
        String nameUser = user.getNameUser();
        String idUser = user.getIdUser();
        jdbcTemplate.update(SQL_UPDATE_USER_NAME_AND_EMAIL_AND_NAME_USER_BY_ID_USER, encryptUserName, email, nameUser,
                idUser);
    }

}
