package com.music.sound.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import com.music.sound.model.Album;
import com.music.sound.model.User;

public class AlbumReadMapper implements RowMapper<AlbumDTO> {
    @Autowired
    private UserDAO userDAO;

    public AlbumDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        AlbumDTO dto = new AlbumDTO();

        String idAlbum = rs.getString("id_album");
        String nameAlbum = rs.getString("name_album");
        Long numberSound = rs.getLong("number_sound");
        Long numberViewer = rs.getLong("number_viewer");
        String idUser = rs.getString("id_user");

        User user = userDAO.findUserById(idUser);

        dto.setIdAlbum(idAlbum);
        dto.setNameAlbum(nameAlbum);
        dto.setNumberSound(numberSound);
        dto.setNumberViewer(numberViewer);

        if (user != null) {
            String nameUser = user.getNameUser();
            dto.setNameUser(nameUser);
        }

        return dto;
    }
}
