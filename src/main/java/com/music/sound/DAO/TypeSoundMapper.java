package com.music.sound.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.music.sound.model.TypeSound;

public class TypeSoundMapper implements RowMapper<TypeSound> {
    public TypeSound mapRow(ResultSet rs, int numRow) throws SQLException {
        TypeSound typeSound = new TypeSound();

        Long id = Long.valueOf(rs.getString("id_type_sound"));
        String nameType = rs.getString("name_type");

        typeSound.setId(id);
        typeSound.setNameType(nameType);

        return typeSound;
    }
}
