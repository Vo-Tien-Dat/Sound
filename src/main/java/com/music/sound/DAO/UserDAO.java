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

@Repository
public class UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    // sql
    private final String SQL_FIND_USER_BY_ID = "SELECT * FROM USER WHERE id_user = ?";

    private final String SQL_FIND_USER_BY_USERNAME = "SELECT * FROM USER WHERE username = ?";

    // feature: show information user
    public User findUserById(String id) {
        User result = (User) jdbcTemplate.queryForObject(SQL_FIND_USER_BY_ID, new BeanPropertyRowMapper<>(User.class),
                new Object[] { id });
        return result;
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
}
