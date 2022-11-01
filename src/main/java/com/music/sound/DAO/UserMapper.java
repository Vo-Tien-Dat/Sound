package com.music.sound.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.springframework.jdbc.core.RowMapper;
import com.music.sound.model.User;

public class UserMapper implements RowMapper<User> {
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        UUID idUser = UUID.fromString(rs.getString("id_user"));
        String username = rs.getString("username");
        String email = rs.getString("email");
        String password = rs.getString("password");
        String pathImgAvatar = rs.getString("path_img_avatar");

        user.setId(idUser);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setPathImgAvatar(pathImgAvatar);

        return user;
    }
}
