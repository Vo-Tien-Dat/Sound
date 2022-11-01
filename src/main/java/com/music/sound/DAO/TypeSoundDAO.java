package com.music.sound.DAO;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.music.sound.model.TypeSound;
import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class TypeSoundDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // sql

    private final String SQL_FIND_ALL_TYPE_SOUND = "SELECT * FROM TYPE_SOUND";

    private final String SQL_FIND_TYPE_SOUND_BY_ID_TYPE_SOUND = "SELECT * FROM TYPE_SOUND WHERE id_type_sound = ?";

    public List<TypeSound> findAllTypeSound() {

        List<TypeSound> records = jdbcTemplate.query(
                SQL_FIND_ALL_TYPE_SOUND,
                new TypeSoundMapper());

        return records;
    }

    public TypeSound findTypeSoundByIdTypeSound(Long idTypeSound) {

        TypeSound record = jdbcTemplate.queryForObject(
                SQL_FIND_TYPE_SOUND_BY_ID_TYPE_SOUND,
                new TypeSoundMapper(),
                idTypeSound);

        return record;
    }
}
