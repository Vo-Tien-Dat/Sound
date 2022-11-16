package com.music.sound.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.springframework.jdbc.core.RowMapper;
import com.music.sound.model.Playlist;

public class PlaylistMapper implements RowMapper<Playlist> {

    public Playlist mapRow(ResultSet rs, int rowNum) throws SQLException {
        Playlist playlist = new Playlist();

        UUID idPlaylist = UUID.fromString(rs.getString("id_playlist"));
        String namePlaylist = rs.getString("name_playlist");
        Long numberSound = Long.valueOf(rs.getString("number_sound"));
        Long numberView = Long.valueOf(rs.getString("number_viewer"));
        String pathImage = rs.getString("path_image");

        playlist.setId(idPlaylist);
        playlist.setNamePlaylist(namePlaylist);
        playlist.setNumberSound(numberSound);
        playlist.setNumberViewer(numberView);
        playlist.setPathImage(pathImage);

        return playlist;
    }
}