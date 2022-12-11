package com.music.sound.DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.music.sound.model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import java.lang.Object;
import java.util.List;
import java.util.ArrayList;

@Repository
public class UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    // sql
    private final String SQL_ALL_USER = "SELECT * FROM user";

    private final String SQL_READ_ALL_ROLE_BY_ID_USER_FROM_USER_ROLE = "SELECT * FROM user_role WHERE id_user = ?";

    private final String SQL_FIND_USER_BY_ID_USER = "SELECT * FROM user WHERE id_user = ?";

    private final String SQL_FIND_USER_BY_USERNAME = "SELECT * FROM user WHERE user_name = ?";

    private final String SQL_READ_USER_BY_USERNAME = "SELECT * FROM user WHERE user_name = ? ";

    private final String SQL_UPDATE_USER_BY_ID_USER = "UPDATE user SET user_name = ?, password = ?,email = ?, name_user = ?, description = ? WHERE id_user = ?";

    private final String SQL_UPDATE_USER_NAME_AND_EMAIL_AND_NAME_USER_BY_ID_USER = "UPDATE user set user_name = ?, email = ? , name_user = ? WHERE id_user = ?";

    private final String SQL_DELETE_USER_BY_ID_USER = "DELETE FROM user WHERE id_user = ? ";

    private final String SQL_UPDATE_PATH_IMAGE_BY_ID_USER = "UPDATE user set path_image = ? where id_user = ? ";

    private final String SQL_UPDATE_PASSWORD_BY_ID_USER = "UPDATE user set password = ? where id_user = ?";

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

    public UserDTO readUserByUsername(String username) {
        UserDTO record = new UserDTO();
        record = jdbcTemplate.queryForObject(SQL_READ_USER_BY_USERNAME, new UserReadMapper(), username);
        return record;
    }

    // feature: show information user
    public User findUserById(String id) {
        User result = (User) jdbcTemplate.queryForObject(SQL_FIND_USER_BY_ID_USER,
                new BeanPropertyRowMapper<>(User.class),
                new Object[] { id });
        return result;
    }

    public UserDTO readUserByIdUser(String idUser) {
        UserDTO record = (UserDTO) jdbcTemplate.queryForObject(SQL_FIND_USER_BY_ID_USER, new UserReadMapper(), idUser);
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

    public void updatePasswordbyIdUser(UserDTO user) {
        String password = user.getPassword();
        String idUser = user.getIdUser();
        jdbcTemplate.update(SQL_UPDATE_PASSWORD_BY_ID_USER, password, idUser);
    }

    public void updatePathImageByIdUser(UserDTO user) {
        String pathImage = user.getPathImage();
        String idUser = user.getIdUser();
        jdbcTemplate.update(SQL_UPDATE_PATH_IMAGE_BY_ID_USER, pathImage, idUser);
    }

    public void updateUserNameAndEmailAndNameUserByIdUser(UserDTO user) {
        String userName = user.getUserName();
        String email = user.getEmail();
        String nameUser = user.getNameUser();
        String idUser = user.getIdUser();
        jdbcTemplate.update(SQL_UPDATE_USER_NAME_AND_EMAIL_AND_NAME_USER_BY_ID_USER, userName, email, nameUser, idUser);
    }

}
