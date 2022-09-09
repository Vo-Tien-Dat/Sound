package com.music.sound.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.music.sound.model.User;
@Repository
public interface UserDAO extends JpaRepository<User, Long>{
    User findByUsername(String username);
}
