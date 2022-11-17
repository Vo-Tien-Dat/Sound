package com.music.sound.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserRoleMapper implements RowMapper<RoleDTO> {
    public RoleDTO mapRow(ResultSet rs, int numRow) throws SQLException {

        RoleDTO dto = new RoleDTO();

        String roleUser = rs.getString("id_role");
        String idUuser = rs.getString("id_user");

        dto.setIdUser(idUuser);
        dto.setRoleUser(roleUser);

        return dto;
    }
}
