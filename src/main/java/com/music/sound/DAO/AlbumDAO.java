package com.music.sound.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.music.sound.model.Album;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

@Repository
public class AlbumDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    // sql
    private final String SQL_READ_ALL_ALBUM = "SELECT * FROM ALBUM";

    private final String SQL_READ_ALBUM_BY_ID_ALBUM = "SELECT * FROM ALBUM WHERE id_album = ?";

    private final String SQL_READ_ALBUM_BY_ID_USER = "SELECT * FROM FROM ALBUM WHERE id_user = ? ";

    public Album findAlbumByIdAlbum(String idAlbum) {
        Album album = jdbcTemplate.queryForObject(SQL_READ_ALBUM_BY_ID_ALBUM, new AlbumMapper(), idAlbum);
        return album;
    }

    public List<Album> findAllAlbum() {

        List<Album> records = jdbcTemplate.query(SQL_READ_ALL_ALBUM, new AlbumMapper());

        return records;
    }

    public List<Album> findAlbumByIdUser(String idUser) {

        List<Album> records = jdbcTemplate.query(SQL_READ_ALBUM_BY_ID_USER, new AlbumMapper(), idUser);

        return records;
    }

    // feature: get all album

    public void insertAlbum(Album album) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();
        try {
            entityManager.persist(album);
            entityTransaction.commit();
        } catch (Exception ex) {
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }
    }
}
