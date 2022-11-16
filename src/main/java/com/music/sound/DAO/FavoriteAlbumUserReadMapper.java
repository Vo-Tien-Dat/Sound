package com.music.sound.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

public class FavoriteAlbumUserReadMapper implements RowMapper<AlbumDTO> {

    @Autowired
    private AlbumDAO albumDAO;

    public AlbumDTO mapRow(ResultSet rs, int numRow) throws SQLException {

        String idAlbum = rs.getString("id_album");

        AlbumDTO dto = albumDAO.readAlbumByIdAlbum(idAlbum);

        return dto;
    }
}
