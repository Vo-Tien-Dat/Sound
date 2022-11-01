package com.music.sound.DAO;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import com.music.sound.model.TypeSound;
import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class TypeSoundDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String SQL_FIND_ALL_TYPE_SOUND = "SELECT * FROM TYPE_SOUND";

    public List<TypeSound> findAllTypeSound() {

        List<TypeSound> result = new ArrayList<>();

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_FIND_ALL_TYPE_SOUND);

        for (Map<String, Object> row : rows) {
            TypeSound typeSound = new TypeSound();

            String nameType = row.get("name_type").toString();

            typeSound.setNameType(nameType);

            result.add(typeSound);
        }

        return result;
    }
}
