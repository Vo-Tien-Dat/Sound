package com.music.sound.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.music.sound.model.Album;
import java.util.UUID;

@Repository
public interface AlbumDAO extends JpaRepository<Album, UUID> {

}
