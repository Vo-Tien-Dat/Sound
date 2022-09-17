package com.music.sound.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.music.sound.model.Sound;

@Repository
public interface SoundDAO extends JpaRepository<Sound, Long>{
    
}
