package com.music.sound.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import com.music.sound.model.Sound;

public class SoundPlaylistMapper implements RowMapper<Sound> {

    @Autowired
    private SoundDAO soundDAO;

    public Sound mapRow(ResultSet rs, int numRow) throws SQLException {
        Sound sound = soundDAO.findSoundByIdSound(null);
        return sound;
    }
}
