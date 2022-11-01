package com.music.sound.DAO;

import javax.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.music.sound.model.Playlist;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

@Repository
public class PlaylistDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    // sql
    private final String SQL_FIND_ALL_PLAYLIST = "SELECT * FROM PLAYLIST";

    private final String SQL_FIND_PLAYLIST_BY_ID_PLAYLIST = "SELECT * FROM PLAYLIST FROM id_playlist = ?";

    private final String SQL_FIND_ALL_PLAYLIST_BY_ID_USER = "SELECT * FROM PLAYLIST FROM id_user = ?";

    public List<Playlist> findAllPlaylist() {

        List<Playlist> records = jdbcTemplate.query(SQL_FIND_ALL_PLAYLIST, new PlaylistMapper());

        return records;
    }

    public Playlist findPlaylistByIdPlaylist(String idPlaylist) {

        Playlist record = (Playlist) jdbcTemplate.queryForObject(
                SQL_FIND_PLAYLIST_BY_ID_PLAYLIST,
                new PlaylistMapper(),
                idPlaylist);

        return record;
    }

    // tính năng: tìm tất cả các playlist bằng id user
    public List<Playlist> findPlaylistByIdUser(String idUser) {

        List<Playlist> records = jdbcTemplate.query(SQL_FIND_ALL_PLAYLIST_BY_ID_USER, new PlaylistMapper(), idUser);

        return records;
    }

    // tính năng: thêm một playlist
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

    // tính năng: lấy ra id của một playlist
    public String getIdPlaylistBeforeInsert(Playlist playlist) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        try {
            entityManager.persist(playlist);
            entityTransaction.commit();

            String id = playlist.getId().toString();

            return id;

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }
        return null;
    }

}
