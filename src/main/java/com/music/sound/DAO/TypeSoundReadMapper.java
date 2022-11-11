package com.music.sound.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TypeSoundReadMapper implements RowMapper<TypeSoundDTO> {
    public TypeSoundDTO mapRow(ResultSet rs, int numRow) throws SQLException {
        TypeSoundDTO dto = new TypeSoundDTO();

        String idTypeSound = rs.getString("id_type_sound");
        String nameTypeSound = rs.getString("name_type_sound");

        dto.setIdTypeSound(idTypeSound);
        dto.setNameTypeSound(nameTypeSound);

        return dto;
    }
}