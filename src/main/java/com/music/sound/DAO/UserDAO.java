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
    private final String SQL_FIND_USER_BY_USERNAME = "SELECT * FROM USER WHERE username = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public User findUserByUsername(String username) {

        User result = (User) jdbcTemplate.queryForObject(SQL_FIND_USER_BY_USERNAME,
                new BeanPropertyRowMapper<>(User.class),
                new Object[] { username });
        return result;
    }

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
}
