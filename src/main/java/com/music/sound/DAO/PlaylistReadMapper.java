package com.music.sound.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import com.music.sound.model.User;

public class PlaylistReadMapper implements RowMapper<PlaylistDTO> {

    @Autowired
    private UserDAO userDAO;

    public PlaylistDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        PlaylistDTO dto = new PlaylistDTO();

        String idPlaylist = rs.getString("id_playlist");
        String namePlaylist = rs.getString("name_playlist");
        Long numberSound = rs.getLong("number_sound");
        Long numberViewer = rs.getLong("number_viewer");
        String pathImage = rs.getString("path_image");
        String idUser = rs.getString("id_user");

        dto.setIdPlaylist(idPlaylist);
        dto.setNamePlaylist(namePlaylist);
        dto.setNumberSound(numberSound);
        dto.setNumberViewer(numberViewer);
        dto.setPathImage(pathImage);

        if (idUser != null) {
            User user = userDAO.findUserById(idUser);
            String nameUser = user.getNameUser();
            dto.setNameUser(nameUser);
        }

        return dto;
    }
}