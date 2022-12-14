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

@Repository
public class PlaylistDAO {
    @Autowired
    private SoundDAO soundDAO;

    @Autowired
    private AlbumDAO albumDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    // sql
    private final String SQL_CREATE_FAVORITE_PLAYLIST_USER_BY_ID_PLAYLIST_AND_ID_USER = "call SP_CREATE_FAVORITE_PLAYLIST_USER_BY_ID_PLAYLIST_AND_ID_USER(?,?)";

    private final String SQL_CREATE_SOUND_PLAYLIST_BY_ID_PLAYLIST_AND_ID_SOUND = "call SP_CREATE_SOUND_PLAYLIST_BY_ID_PLAYLIST_AND_ID_SOUND(?,?)";

    private final String SQL_READ_ALL_PLAYLIST_BY_ID_USER = "call SP_READ_ALL_PLAYLIST_BY_ID_USER(?) ";

    private final String SQL_READ_ALL_PLAYLIST_HAVE_LIMIT_AND_RANDOM = "call SP_READ_ALL_PLAYLIST_HAVE_LIMIT_AND_RANDOM(?)";

    private final String SQL_READ_ALL_PLAYLIST_BY_ID_USER_FROM_FAVORITE_PLAYLIST_USER = "call SP_READ_ALL_PLAYLIST_BY_ID_USER_FROM_FAVORITE_PLAYLIST_USER(?)";

    private final String SQL_READ_ALL_PLAYLIST = "call SP_READ_ALL_PLAYLIST()";

    private final String SQL_PLAYLIST_BY_ID_PLAYLIST = "call SP_PLAYLIST_BY_ID_PLAYLIST(?)";

    private final String SQL_DELETE_PLAYLIST_BY_ID_PLAYLIST = "call SP_DELETE_PLAYLIST_BY_ID_PLAYLIST(?)";

    private final String SQL_UPDATE_PLAYLIST_BY_ID_PLAYLIST = "call SP_UPDATE_PLAYLIST_BY_ID_PLAYLIST(?,?,?)";

    private final String SQL_UPDATE_NAME_PLAYLIST_BY_ID_PLAYLIST = "call SP_UPDATE_NAME_PLAYLIST_BY_ID_PLAYLIST(?,?)";

    private final String SQL_UPDATE_IMAGE_PLAYLIST_BY_ID_PLAYLIST = "call SP_UPDATE_IMAGE_PLAYLIST_BY_ID_PLAYLIST(?,?)";

    private final String SQL_READ_ALL_SOUND_BY_ID_PLAYLIST_FIRST = "call SP_READ_ALL_SOUND_BY_ID_PLAYLIST_FIRST(?)";

    private final String SQL_READ_ALL_SOUND_BY_ID_PLAYLIST = "call SP_READ_ALL_SOUND_BY_ID_PLAYLIST(?)";

    private final String SQL_READ_ALBUM_WITH_ID_SOUND = "call SP_READ_ALBUM_WITH_ID_SOUND(?)";

    private final String SQL_DELETE_SOUND_PLAYLIST_BY_ID_PLAYLIST = "call SP_DELETE_SOUND_PLAYLIST_BY_ID_PLAYLIST(?)";

    private final String SQL_DELETE_SOUND_PLAYLIST_BY_ID_SOUND_AND_ID_PLAYLIST = "call SP_DELETE_SOUND_PLAYLIST_BY_ID_SOUND_AND_ID_PLAYLIST(?,?)";

    private final String SQL_DELETE_FAVORITE_PLAYLIST_USER_BY_ID_PLAYLIST_AND_ID_USER = "call SP_DELETE_FAVORITE_PLAYLIST_USER_BY_ID_PLAYLIST_AND_ID_USER(?,?)";

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

    public List<PlaylistDTO> readAllPlaylistByIdUserFromFavoritePlaylistUser(String idUser) {
        List<PlaylistDTO> records = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate
                .queryForList(SQL_READ_ALL_PLAYLIST_BY_ID_USER_FROM_FAVORITE_PLAYLIST_USER, idUser);
        for (Map<String, Object> row : rows) {
            PlaylistDTO record = new PlaylistDTO();
            String idPlaylist = row.get("id_playlist").toString();
            record = readPlaylistByIdPlaylist(idPlaylist);
            records.add(record);
        }
        return records;
    }

    public void createFavoritePlaylistUserByIdPlaylistAndIdUser(String idPlaylist, String idUser) {
        jdbcTemplate.update(
                SQL_CREATE_FAVORITE_PLAYLIST_USER_BY_ID_PLAYLIST_AND_ID_USER, new Object[] { idPlaylist, idUser });
    }

    public void deleteFavoritePlaylistUserByIdPlaylistAndIdUser(String idPlayist, String idUser) {
        jdbcTemplate.update(SQL_DELETE_FAVORITE_PLAYLIST_USER_BY_ID_PLAYLIST_AND_ID_USER,
                new Object[] { idPlayist, idUser });
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

    public List<SoundDTO> readAllSoundByIdPlaylistFromSoundPlaylistHome(String idPlaylist) {
        List<SoundDTO> records = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_READ_ALL_SOUND_BY_ID_PLAYLIST, idPlaylist);
        for (Map<String, Object> row : rows) {

            String idSound = row.get("id_sound").toString();
            SoundDTO sound = new SoundDTO();

            if (idSound != null) {
                sound = soundDAO.readSoundByIdSound(idSound);
                AlbumDTO album = jdbcTemplate.queryForObject(SQL_READ_ALBUM_WITH_ID_SOUND,
                        new AlbumReadMapper(), new Object[] { idSound });
                if (album != null) {
                    sound.setNameAlbum(album.getNameAlbum());
                }
            }
            records.add(sound);
        }
        System.out.println(records);
        return records;
    }

    public List<SoundDTO> readAllSoundByIdPlaylistFromSoundPlaylistFirst(String idPlaylist) {
        List<SoundDTO> records = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_READ_ALL_SOUND_BY_ID_PLAYLIST_FIRST,
                idPlaylist);
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
        String pathImage = playlist.getPathImage();
        jdbcTemplate.update(SQL_UPDATE_PLAYLIST_BY_ID_PLAYLIST, namePlaylist, pathImage, idPlaylist);
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

    // t??nh n??ng: t??m t???t c??? c??c playlist b???ng id user
    public List<Playlist> findPlaylistByIdUser(String idUser) {

        List<Playlist> records = new ArrayList<>();

        records = jdbcTemplate.query(SQL_READ_ALL_PLAYLIST_BY_ID_USER, new PlaylistMapper(), idUser);

        return records;
    }

    // t??nh n??ng: th??m m???t playlist
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

    // t??nh n??ng: l???y ra id c???a m???t playlist
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

    public void updateNamePlaylistByIdPlaylist(PlaylistDTO playlist) {
        String idPlaylist = playlist.getIdPlaylist();
        String namePlaylist = playlist.getNamePlaylist();
        jdbcTemplate.update(SQL_UPDATE_NAME_PLAYLIST_BY_ID_PLAYLIST, namePlaylist, idPlaylist);
    }

    public void updateImagePlaylistByIdPlaylist(PlaylistDTO playlist) {
        String idPlaylist = playlist.getIdPlaylist();
        String pathImage = playlist.getPathImage();
        jdbcTemplate.update(SQL_UPDATE_IMAGE_PLAYLIST_BY_ID_PLAYLIST, pathImage, idPlaylist);
    }
}
