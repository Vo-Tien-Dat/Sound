package com.music.sound.DAO;

import javax.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.music.sound.model.Playlist;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import com.music.sound.model.User;

@Repository
public class PlaylistDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private UserDAO userDAO;

    // sql
    private final String SQL_FIND_ALL_PLAYLIST = "SELECT * FROM PLAYLIST";

    private final String SQL_FIND_ALL_PLAYLIST_BY_ID_USER = "SELECT * FROM PLAYLIST FROM id_user = ?";

    public List<Playlist> findAllPlaylist() {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_FIND_ALL_PLAYLIST);

        List<Playlist> result = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            Playlist playlist = new Playlist();

            UUID idPlaylist = UUID.fromString(row.get("id_playlist").toString());
            String namePlaylist = row.get("name_playlist") != null
                    ? row.get("name_playlist").toString()
                    : "";
            String pathImage = row.get("path_image") != null
                    ? row.get("path_image").toString()
                    : "";
            Long numberViewer = row.get("number_viewer") != null
                    ? Long.parseLong(row.get("number_viewer").toString())
                    : Long.valueOf(0);
            String idUser = row.get("id_user") != null
                    ? row.get("id_user").toString()
                    : "";

            User user = userDAO.findUserById(idUser);

            playlist.setId(idPlaylist);
            playlist.setNamePlaylist(namePlaylist);
            playlist.setPathImage(pathImage);
            playlist.setNumberViewer(numberViewer);
            playlist.setUser(user);

            result.add(playlist);
        }
        return result;
    }

    // feature: get all playlist in user
    public List<Playlist> findPlaylistByIdUser(String idUser) {

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_FIND_ALL_PLAYLIST_BY_ID_USER, idUser);

        List<Playlist> result = new ArrayList<>();

        for (Map<String, Object> row : rows) {

            Playlist playlist = new Playlist();

            UUID idPlaylist = UUID.fromString(row.get("id_playlist").toString());
            String namePlaylist = row.get("name_playlist") != null
                    ? row.get("name_playlist").toString()
                    : "";
            String pathImage = row.get("path_image") != null
                    ? row.get("path_image").toString()
                    : "";

            playlist.setId(idPlaylist);
            playlist.setNamePlaylist(namePlaylist);
            playlist.setPathImage(pathImage);

            result.add(playlist);
        }
        return result;
    }

    // feature: add new playlist
    public void insertPlaylist(Playlist playlist) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        try {
            entityManager.persist(playlist);
            entityTransaction.commit();
        } catch (Exception ex) {
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }
    }

}
