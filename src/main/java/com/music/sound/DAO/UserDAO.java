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
    private final String SQL_ALL_USER = "SELECT * FROM USER";

    private final String SQL_FIND_USER_BY_ID_USER = "SELECT * FROM USER WHERE id_user = ?";

    private final String SQL_FIND_USER_BY_USERNAME = "SELECT * FROM USER WHERE username = ?";

    private final String SQL_UPDATE_USER_BY_ID_USER = "UPDATE USER SET user_name = ?, password = ?,email = ?, name_user = ?, description = ? WHERE id_user = ?";

    private final String SQL_DELETE_USER_BY_ID_USER = "DELETE FROM USER WHERE id_user = ? ";
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
            System.out.println(ex.getMessage());
        } finally {
            entityManager.close();
        }
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
}
