package com.music.sound.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class FavoriteSoundUserReadMapper implements RowMapper<SoundDTO> {
    @Autowired
    private SoundDAO soundDAO;

    public SoundDTO mapRow(ResultSet rs, int numRow) throws SQLException {
        String idSound = rs.getString("id_sound");
        SoundDTO dto = soundDAO.readSoundByIdSound(idSound);
        return dto;
    }
}
