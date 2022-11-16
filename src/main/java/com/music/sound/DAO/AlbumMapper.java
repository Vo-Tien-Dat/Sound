package com.music.sound.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.springframework.jdbc.core.RowMapper;
import com.music.sound.model.Album;

public class AlbumMapper implements RowMapper<Album> {

    public Album mapRow(ResultSet rs, int rowNum) throws SQLException {
        Album album = new Album();
        UUID idAlbum = UUID.fromString(rs.getString("id_album"));
        String nameAlbum = rs.getString("name_album");
        Long numberSound = rs.getLong("number_sound");
        Long numberViewer = rs.getLong("number_view");
        String pathImage = rs.getString("path_image");

        album.setId(idAlbum);
        album.setNameAlbum(nameAlbum);
        album.setNumberSound(numberSound);
        album.setNumberViewer(numberViewer);
        album.setPathImage(pathImage);

        return album;
    }
}
