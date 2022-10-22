package com.music.sound.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.music.sound.model.Playlist;

@Repository
public interface PlaylistDAO extends JpaRepository<Playlist, Long> {

}
