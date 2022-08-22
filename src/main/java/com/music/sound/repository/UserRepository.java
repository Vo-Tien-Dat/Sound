package com.music.sound.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.music.sound.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User findByUsername(String username);
}
