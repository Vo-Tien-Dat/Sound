package com.music.sound.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SoundReadMapper implements RowMapper<SoundDTO> {
    public SoundDTO mapRow(ResultSet rs, int numRow) throws SQLException {
        SoundDTO dto = new SoundDTO();

        String idSound = rs.getString("id_sound");
        String nameSound = rs.getString("name_sound");
        Long numberViewer = rs.getLong("number_viewer");
        String nameSinger = rs.getString("name_singer");
        String pathAudio = rs.getString("path_audio");
        String pathImage = rs.getString("path_image");

        dto.setIdSound(idSound);
        dto.setNameSound(nameSound);
        dto.setNumberViewer(numberViewer);
        dto.setNameSinger(nameSinger);
        dto.setPathAudio(pathAudio);
        dto.setPathImage(pathImage);

        return dto;
    }
}
