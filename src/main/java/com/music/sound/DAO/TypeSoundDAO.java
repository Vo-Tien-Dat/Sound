package com.music.sound.DAO;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.music.sound.model.TypeSound;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;

@Repository
public class TypeSoundDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // sql

    private final String SQL_READ_ALL_TYPE_SOUND = "SELECT * FROM TYPE_SOUND";

    private final String SQL_RAED_TYPE_SOUND_BY_ID_TYPE_SOUND = "SELECT * FROM TYPE_SOUND WHERE id_type_sound = ?";

    public List<TypeSound> findAllTypeSound() {
        List<TypeSound> records = new ArrayList<>();
        try {
            records = jdbcTemplate.query(
                    SQL_READ_ALL_TYPE_SOUND,
                    new TypeSoundMapper());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return records;
    }

    public TypeSound findTypeSoundByIdTypeSound(Long idTypeSound) {
        TypeSound record = new TypeSound();

        try {
            record = jdbcTemplate.queryForObject(
                    SQL_RAED_TYPE_SOUND_BY_ID_TYPE_SOUND,
                    new TypeSoundMapper(),
                    new Object[] { idTypeSound });
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return record;
    }
}
