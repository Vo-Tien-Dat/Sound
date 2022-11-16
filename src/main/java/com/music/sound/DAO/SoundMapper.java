package com.music.sound.DAO;

import java.sql.SQLException;
import java.util.UUID;
import com.music.sound.model.Sound;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;

public class SoundMapper implements RowMapper<Sound> {

    public Sound mapRow(ResultSet rs, int rowNum) throws SQLException {

        Sound sound = new Sound();

        UUID uuid = UUID.fromString(rs.getString("id_sound"));
        String nameSound = rs.getString("name_sound");
        Long numberView = Long.valueOf(rs.getString("number_viewer"));
        String pathAudio = rs.getString("path_audio");
        String pathImage = rs.getString("path_image");

        sound.setId(uuid);
        sound.setNameSound(nameSound);
        sound.setNumberViewer(numberView);
        sound.setPathAudio(pathAudio);
        sound.setPathImage(pathImage);

        return sound;
    }
}
