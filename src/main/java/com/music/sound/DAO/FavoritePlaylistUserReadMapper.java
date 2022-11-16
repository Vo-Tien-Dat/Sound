package com.music.sound.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

public class FavoritePlaylistUserReadMapper implements RowMapper<PlaylistDTO> {

    @Autowired
    private PlaylistDAO playlistDAO;

    public PlaylistDTO mapRow(ResultSet rs, int numRow) throws SQLException {

        String idPlaylist = rs.getString("id_playlist");

        PlaylistDTO dto = playlistDAO.readPlaylistByIdPlaylist(idPlaylist);

        return dto;
    }
}
