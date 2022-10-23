package com.music.sound.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.music.sound.model.Album;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import com.music.sound.model.User;

@Repository
public class AlbumDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private UserDAO userDAO;

    // sql
    private final String SQL_FIND_ALL_ALBUM = "SELECT * FROM ALBUM";

    private final String SQL_FIND_ALBUM_BY_ID_ALUBM = "SELECT * FROM ALBUM WHERE id_album = ?";

    private final String SQL_FIND_ALL_ALBUM_BY_ID_USER = "SELECT * FROM FROM ALBUM WHERE id_user = ? ";

    public Album findAlbumByIdAlbum(String idAlbum) {
        Album album = jdbcTemplate.queryForObject(SQL_FIND_ALBUM_BY_ID_ALUBM, new AlbumMapper(), idAlbum);
        return album;
    }

    public List<Album> findAlbumByIdUser(String idUser) {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_FIND_ALL_ALBUM_BY_ID_USER, idUser);

        List<Album> result = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Album album = new Album();

            UUID idPlaylist = UUID.fromString(row.get("id_album").toString());
            String nameAlbum = row.get("name_album") != null
                    ? row.get("name_album").toString()
                    : "";
            Long numberView = row.get("number_view") != null
                    ? Long.parseLong(row.get("number_view").toString())
                    : Long.valueOf(0);
            Long numberSound = row.get("number_sound") != null
                    ? Long.parseLong(row.get("number_sound").toString())
                    : Long.valueOf(0);

            album.setId(idPlaylist);
            album.setNameAlbum(nameAlbum);
            album.setNumberViewer(numberView);
            album.setNumberSound(numberSound);

            result.add(album);
        }

        return result;
    }

    // feature: get all album
    public List<Album> findAllAlbum() {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_FIND_ALL_ALBUM);

        List<Album> result = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            Album album = new Album();

            UUID idPlaylist = UUID.fromString(row.get("id_album").toString());
            String nameAlbum = row.get("name_album") != null
                    ? row.get("name_album").toString()
                    : "";
            Long numberView = row.get("number_view") != null
                    ? Long.parseLong(row.get("number_view").toString())
                    : Long.valueOf(0);
            Long numberSound = row.get("number_sound") != null
                    ? Long.parseLong(row.get("number_sound").toString())
                    : Long.valueOf(0);
            String idUser = row.get("id_user") != null
                    ? row.get("id_user").toString()
                    : "";

            User user = userDAO.findUserById(idUser);

            album.setId(idPlaylist);
            album.setNameAlbum(nameAlbum);
            album.setNumberViewer(numberView);
            album.setNumberSound(numberSound);
            album.setUser(user);

            result.add(album);
        }
        return result;
    }

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
