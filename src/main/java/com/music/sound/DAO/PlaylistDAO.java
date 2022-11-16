package com.music.sound.DAO;

import javax.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.music.sound.model.Playlist;
import com.music.sound.model.Sound;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

@Repository
public class PlaylistDAO {
    @Autowired
    private SoundDAO soundDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    // sql
    private final String SQL_READ_ALL_PLAYLIST = "SELECT * FROM PLAYLIST";

    private final String SQL_PLAYLIST_BY_ID_PLAYLIST = "SELECT * FROM PLAYLIST WHERE id_playlist = ?";

    private final String SQL_READ_ALL_PLAYLIST_BY_ID_USER = "SELECT * FROM PLAYLIST FROM id_user = ?";

    private final String SQL_READ_ALL_ALBUM_BY_ID_USER_FROM_FAVORITE_SOUND_USER = "SELECT * FROM FAVORITE_PLAYLIST_USER WHERE id_user = ? ";

    private final String SQL_DELETE_PLAYLIST_BY_ID_PLAYLIST = "DELETE FROM PLAYLIST WHERE id_playlist = ? ";

    private final String SQL_UPDATE_PLAYLIST_BY_ID_PLAYLIST = "UPDATE PLAYLIST SET name_playlist = ? WHERE id_playlist = ? ";

    private final String SQL_CREATE_SOUND_PLAYLIST_BY_ID_PLAYLIST_AND_ID_SOUND = "INSERT INTO SOUND_PLAYLIST(id_sound, id_playlist) VALUES (?, ?)";

    private final String SQL_READ_ALL_SOUND_BY_ID_PLAYLIST_FIRST = "select SOUND.id_sound from SOUND RIGHT JOIN (SELECT id_sound from SOUND_PLAYLIST where id_playlist = ?) as PLAYLIST on SOUND.id_sound <> PLAYLIST.id_sound";

    private final String SQL_READ_ALL_SOUND_BY_ID_PLAYLIST = "SELECT * FROM SOUND_PLAYLIST WHERE id_playlist = ? ";

    private final String SQL_DELETE_SOUND_PLAYLIST_BY_ID_PLAYLIST = "DELETE FROM SOUND_PLAYLIST WHERE id_playlist = ? ";

    private final String SQL_DELETE_SOUND_PLAYLIST_BY_ID_SOUND_AND_ID_PLAYLIST = "DELETE FROM SOUND_PLAYLIST WHERE id_sound = ? and id_playlist = ? ";

    private final String SQL_READ_ALL_PLAYLIST_HAVE_LIMIT_AND_RANDOM = "SELECT * FROM PLAYLIST ORDER BY RAND() LIMIT  ? ";

    public List<Playlist> findAllPlaylist() {

        List<Playlist> records = jdbcTemplate.query(SQL_READ_ALL_PLAYLIST, new PlaylistMapper());

        return records;
    }

    public List<PlaylistDTO> readAllPLaylist() {
        List<PlaylistDTO> records = jdbcTemplate.query(SQL_READ_ALL_PLAYLIST, new PlaylistReadMapper());
        return records;
    }

    public List<PlaylistDTO> readAllPLaylistHaveLimit(int rowLimit) {
        List<PlaylistDTO> records = jdbcTemplate.query(
                SQL_READ_ALL_PLAYLIST_HAVE_LIMIT_AND_RANDOM, new PlaylistReadMapper(), new Object[] { rowLimit });
        return records;
    }

    public PlaylistDTO readPlaylistByIdPlaylist(String idPlaylist) {
        PlaylistDTO record = jdbcTemplate.queryForObject(SQL_PLAYLIST_BY_ID_PLAYLIST, new PlaylistReadMapper(),
                new Object[] { idPlaylist });
        return record;
    }

    public List<AlbumDTO> readAllPlaylistByIdUserFromFavoritePlaylistUser(String idUser) {
        List<AlbumDTO> records = jdbcTemplate.query(
                SQL_READ_ALL_ALBUM_BY_ID_USER_FROM_FAVORITE_SOUND_USER,
                new FavoriteAlbumUserReadMapper(), new Object[] { idUser });
        return records;
    }

    public void createSoundPlaylistByIdSoundAndIdPlaylist(String idSound, String idPlaylist) {
        jdbcTemplate.update(SQL_CREATE_SOUND_PLAYLIST_BY_ID_PLAYLIST_AND_ID_SOUND, idSound, idPlaylist);
    }

    public List<SoundDTO> readAllSoundByIdPlaylistFromSoundPlaylist(String idPlaylist) {
        List<SoundDTO> records = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_READ_ALL_SOUND_BY_ID_PLAYLIST, idPlaylist);
        for (Map<String, Object> row : rows) {
            SoundDTO record = new SoundDTO();
            String idSound = row.get("id_sound").toString();
            record = soundDAO.readSoundByIdSound(idSound);
            records.add(record);
        }
        return records;
    }

    public List<SoundDTO> readAllSoundByIdPlaylistFromSoundPlaylistFirst(String idPlaylist) {
        List<SoundDTO> records = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_READ_ALL_SOUND_BY_ID_PLAYLIST_FIRST, idPlaylist);
        for (Map<String, Object> row : rows) {
            SoundDTO record = new SoundDTO();
            String idSound = row.get("id_sound").toString();
            record = soundDAO.readSoundByIdSound(idSound);
            records.add(record);
        }
        return records;
    }

    public void deletePlaylist(String idPlaylist) {
        jdbcTemplate.update(SQL_DELETE_PLAYLIST_BY_ID_PLAYLIST, idPlaylist);
    }

    public void deleteSoundPlaylistByIdPlaylist(String idPlaylist) {
        jdbcTemplate.update(SQL_DELETE_SOUND_PLAYLIST_BY_ID_PLAYLIST, idPlaylist);
    }

    public void deleteSoundPlaylistByIdSoundAndIdPlaylist(String idSound, String idPlaylist) {
        jdbcTemplate.update(SQL_DELETE_SOUND_PLAYLIST_BY_ID_SOUND_AND_ID_PLAYLIST, idSound, idPlaylist);
    }

    public void updatePlaylist(PlaylistDTO playlist) {
        String idPlaylist = playlist.getIdPlaylist();
        String namePlaylist = playlist.getNamePlaylist();
        jdbcTemplate.update(SQL_UPDATE_PLAYLIST_BY_ID_PLAYLIST, namePlaylist, idPlaylist);
    }

    public String getIdPlaylistBeforeCreatePlaylist() {
        Playlist playlist = new Playlist();
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

    public Playlist findPlaylistByIdPlaylist(String idPlaylist) {
        Playlist record = new Playlist();
        record = jdbcTemplate.queryForObject(
                SQL_PLAYLIST_BY_ID_PLAYLIST,
                new PlaylistMapper(), new Object[] { idPlaylist });

        return record;
    }

    // tính năng: tìm tất cả các playlist bằng id user
    public List<Playlist> findPlaylistByIdUser(String idUser) {

        List<Playlist> records = new ArrayList<>();

        records = jdbcTemplate.query(SQL_READ_ALL_PLAYLIST_BY_ID_USER, new PlaylistMapper(), idUser);

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
