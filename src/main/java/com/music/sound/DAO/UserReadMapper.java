package com.music.sound.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserReadMapper implements RowMapper<UserDTO> {
    public UserDTO mapRow(ResultSet rs, int numRow) throws SQLException {

        UserDTO dto = new UserDTO();

        String idUser = rs.getString("id_user");
        String password = rs.getString("password");
        String nameUser = rs.getString("name_user");
        String userName = rs.getString("user_name");
        String email = rs.getString("email");
        String description = rs.getString("description");
        String pathImage = rs.getString("path_image");

        dto.setIdUser(idUser);
        dto.setUserName(userName);
        dto.setPassword(password);
        dto.setNameUser(nameUser);
        dto.setEmail(email);
        dto.setDescription(description);
        dto.setPathImage(pathImage);

        return dto;
    }
}
