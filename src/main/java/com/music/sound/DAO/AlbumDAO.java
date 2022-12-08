package com.music.sound.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.music.sound.model.Album;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

@Repository
public class AlbumDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    // sql
    private final String SQL_READ_ALL_ALBUM = "SELECT * FROM album";

    private final String SQL_READ_ALBUM_BY_ID_ALBUM = "SELECT * FROM album WHERE id_album = ?";

    private final String SQL_READ_ALBUM_BY_ID_USER = "SELECT * FROM FROM album WHERE id_user = ? ";

    private final String SQL_READ_ALL_ALBUM_BY_ID_USER_FROM_FAVORITE_ALBUM_USER = "SELECT * FROM  favorite_album_user WHERE id_user = ? ";

    private final String SQL_CREATE_FAVORITE_ALBUM_USER_BY_ID_ALBUM_AND_ID_USER = "INSERT INTO favorite_album_user(id_album, id_user) VALUES (?,?)";

    private final String SQL_DELETE_FAVORITE_ALBUM_USER_BY_ID_ALBUM_AND_ID_USER = "DELETE FROM favorite_album_user WHERE id_album = ? AND id_user = ? ";

    private final String SQL_UPDATE_ALBUM_BY_ID_ALBUM = "UPDATE album SET name_album = ?, name_singer = ?, path_image = ? WHERE id_album = ?";

    private final String SQL_UPDATE_ALBUM_BY_ID_ALBUM_2_ARGUMENT = "UPDATE album set name_album = ?, name_singer = ? WHERE id_album = ?";

    private final String SQL_UPDATE_IMAGE_ALBUM_BY_ID_ALBUM = "UPDATE album set path_image = ? WHERE id_album = ? ";

    private final String SQL_READ_ALL_ALBUM_HAVE_LIMIT_AND_RANDOM = "SELECT * FROM album LIMIT  ? ";

    private final String SQL_DELETE_ALBUM_BY_ID_ALBUM = "DELETE FROM album WHERE id_album = ? ";

    private final String SQL_DELETE_ALBUM_BY_NAME_ALBUM_IS_NULL_AND_NAME_SINGER_IS_NULL = "DELETE FROM album WHERE name_album is null and name_singer is null";

    public Album findAlbumByIdAlbum(String idAlbum) {
        Album album = jdbcTemplate.queryForObject(SQL_READ_ALBUM_BY_ID_ALBUM, new AlbumMapper(), idAlbum);
        return album;
    }

    public List<Album> findAllAlbum() {

        List<Album> records = jdbcTemplate.query(SQL_READ_ALL_ALBUM, new AlbumMapper());

        return records;
    }

    public List<AlbumDTO> readAllAlbum() {
        List<AlbumDTO> records = jdbcTemplate.query(SQL_READ_ALL_ALBUM, new AlbumReadMapper());
        return records;
    }

    public List<AlbumDTO> readAllAlbumHaveLimit(int rowLimit) {
        List<AlbumDTO> records = jdbcTemplate.query(
                SQL_READ_ALL_ALBUM_HAVE_LIMIT_AND_RANDOM, new AlbumReadMapper(),
                new Object[] { rowLimit });
        return records;
    }

    public AlbumDTO readAlbumByIdAlbum(String idAlbum) {
        AlbumDTO record = jdbcTemplate.queryForObject(SQL_READ_ALBUM_BY_ID_ALBUM, new AlbumReadMapper(),
                new Object[] { idAlbum });
        return record;
    }

    public List<AlbumDTO> readAllAlbumByIdUserFromFavoriteAlbumUser(String idUser) {
        List<AlbumDTO> records = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate
                .queryForList(SQL_READ_ALL_ALBUM_BY_ID_USER_FROM_FAVORITE_ALBUM_USER, idUser);
        for (Map<String, Object> row : rows) {
            AlbumDTO record = new AlbumDTO();
            String idAlbum = row.get("id_album").toString();
            record = readAlbumByIdAlbum(idAlbum);
            records.add(record);
        }
        return records;
    }

    public void createFavoriteAlbumUserByIdAlbumAndIdUser(String idAlbum, String idUser) {
        jdbcTemplate.update(SQL_CREATE_FAVORITE_ALBUM_USER_BY_ID_ALBUM_AND_ID_USER, new Object[] { idAlbum, idUser });
    }

    public void deleteFavoriteAlbumUserByIdAlbumAndIdUser(String idAlbum, String idUser) {
        jdbcTemplate.update(SQL_DELETE_FAVORITE_ALBUM_USER_BY_ID_ALBUM_AND_ID_USER, new Object[] { idAlbum, idUser });
    }

    public String getIdAblbumBeforeCreateAlbum() {
        Album album = new Album();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        try {
            entityManager.persist(album);
            entityTransaction.commit();
            String id = album.getId().toString();
            return id;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }
        return null;
    }

    public String getIdAlbumBeforeCreateAlbum(Album album) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        try {
            entityManager.persist(album);
            entityTransaction.commit();
            String id = album.getId().toString();
            return id;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }
        return null;
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

    public void deleteAlbumByIdAlbum(String idAlbum) {
        jdbcTemplate.update(SQL_DELETE_ALBUM_BY_ID_ALBUM, idAlbum);
    }

    public void updateAlbum(AlbumDTO album) {
        String idAlbum = album.getIdAlbum();
        String nameAlbum = album.getNameAlbum();
        String nameSinger = album.getNameSinger();
        String pathImage = album.getPathImage();
        jdbcTemplate.update(SQL_UPDATE_ALBUM_BY_ID_ALBUM, nameAlbum, nameSinger, pathImage, idAlbum);
    }

    public void updateAlbum2Arg(AlbumDTO album) {
        String idAlbum = album.getIdAlbum();
        String nameAlbum = album.getNameAlbum();
        String nameSinger = album.getNameSinger();
        jdbcTemplate.update(SQL_UPDATE_ALBUM_BY_ID_ALBUM_2_ARGUMENT, nameAlbum, nameSinger, idAlbum);
    }

    public void updateImageAlbumByIdAlbum(AlbumDTO album) {
        String idAlbum = album.getIdAlbum();
        String pathImage = album.getPathImage();

        jdbcTemplate.update(SQL_UPDATE_IMAGE_ALBUM_BY_ID_ALBUM, pathImage, idAlbum);

    }

    public void deleteAlbumByNameAlbumIsNullAndNameSingerIsNull() {
        jdbcTemplate.update(SQL_DELETE_ALBUM_BY_NAME_ALBUM_IS_NULL_AND_NAME_SINGER_IS_NULL);
    }
}
