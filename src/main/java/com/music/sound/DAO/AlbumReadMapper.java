package com.music.sound.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class AlbumReadMapper implements RowMapper<AlbumDTO> {

    public AlbumDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        AlbumDTO dto = new AlbumDTO();

        String idAlbum = rs.getString("id_album");
        String nameAlbum = rs.getString("name_album");
        Long numberSound = rs.getLong("number_sound");
        Long numberViewer = rs.getLong("number_viewer");
        String nameSinger = rs.getString("name_singer");
        String pathImage = rs.getString("path_image");

        dto.setIdAlbum(idAlbum);
        dto.setNameAlbum(nameAlbum);
        dto.setNumberSound(numberSound);
        dto.setNumberViewer(numberViewer);
        dto.setNameSinger(nameSinger);
        dto.setPathImage(pathImage);

        return dto;
    }
}
