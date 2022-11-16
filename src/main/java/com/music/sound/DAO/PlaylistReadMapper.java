package com.music.sound.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class PlaylistReadMapper implements RowMapper<PlaylistDTO> {

    public PlaylistDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        PlaylistDTO dto = new PlaylistDTO();

        String idPlaylist = rs.getString("id_playlist");
        String namePlaylist = rs.getString("name_playlist");
        Long numberSound = rs.getLong("number_sound");
        Long numberViewer = rs.getLong("number_viewer");
        String pathImage = rs.getString("path_image");

        dto.setIdPlaylist(idPlaylist);
        dto.setNamePlaylist(namePlaylist);
        dto.setNumberSound(numberSound);
        dto.setNumberViewer(numberViewer);
        dto.setPathImage(pathImage);

        return dto;
    }
}