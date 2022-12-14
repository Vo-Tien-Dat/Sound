package com.music.sound.DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.music.sound.model.Sound;
import com.music.sound.model.TypeSound;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Repository
public class SoundDAO {

        @Autowired
        private JdbcTemplate jdbcTemplate;

        @Autowired
        private EntityManagerFactory entityManagerFactory;

        // sql

        private final String SQL_CREATE_FAVORITE_SOUND_USER_BY_ID_SOUND_AND_ID_USER = "call SP_CREATE_FAVORITE_SOUND_USER_BY_ID_SOUND_AND_ID_USER(?,?)";

        private final String SQL_READ_ALL_SOUND = "call SP_READ_ALL_SOUND()";

        private final String SQL_READ_SOUND_BY_ID_SOUND = "call SP_READ_SOUND_BY_ID_SOUND(?)";

        private final String SQL_READ_ALL_SOUND_BY_ID_USER = "call SP_READ_ALL_SOUND_BY_ID_USER(?)";

        private final String SQL_READ_ALL_SOUND_BY_ID_PLAYLIST = "call SP_READ_ALL_SOUND_BY_ID_PLAYLIST(?)";

        private final String SQL_READ_ALL_SOUND_BY_ID_ALBUM = "call SP_READ_ALL_SOUND_BY_ID_ALBUM(?)";

        private final String SQL_READ_ALL_SOUND_BY_ID_USER_FROM_FAVORITE_SOUND_USER = "call SP_READ_ALL_SOUND_BY_ID_USER_FROM_FAVORITE_SOUND_USER(?)";

        private final String SQL_READ_ALL_SOUND_HAVE_LIMIT_AND_RANDOM = "call SP_READ_ALL_SOUND_HAVE_LIMIT_AND_RANDOM(?)";

        private final String SQL_READ_ALL_SOUND_WITH_KEY_DETAIL = "SELECT * FROM sound WHERE name_sound = ? ";

        private final String SQL_READ_ALL_SOUND_BY_ID_ALBUM_IS_NULL = "call SP_READ_ALL_SOUND_BY_ID_ALBUM_IS_NULL()";

        private final String SQL_UPDATE_SOUND = "call SP_UPDATE_SOUND(?,?,?) ";

        private final String SQL_UPDATE_SOUND_3_ARGUMENT = "call SP_UPDATE_SOUND_3_ARGUMENT(?,?,?)";

        private final String SQL_UPDATE_NAME_SOUND_AND_NAME_SINGER_BY_ID_SOUND = "call SP_UPDATE_NAME_SOUND_AND_NAME_SINGER_BY_ID_SOUND(?,?,?)";

        private final String SQL_UPDATE_ID_ALBUM_BY_ID_SOUND = "call SP_UPDATE_ID_ALBUM_BY_ID_SOUND(?,?)";

        private final String SQL_UPDATE_ID_ALBUM_IS_NULL_BY_ID_ALBUM_FROM_SOUND = "call SP_UPDATE_ID_ALBUM_IS_NULL_BY_ID_ALBUM_FROM_SOUND(?)";

        private final String SQL_UPDATE_PATH_IMAGE_BY_ID_SOUND = "call SP_UPDATE_PATH_IMAGE_BY_ID_SOUND(?,?)";

        private final String SQL_UPDATE_PATH_AUDIO_BY_ID_SOUND = "call SP_UPDATE_PATH_AUDIO_BY_ID_SOUND(?,?)";

        private final String SQL_DELETE_SOUND_BY_ID_SOUND = "call SP_DELETE_SOUND_BY_ID_SOUND(?)";

        private final String SQL_DELETE_FAVORITE_SOUND_USER_BY_ID_SOUND_AND_ID_USER = "call SP_DELETE_FAVORITE_SOUND_USER_BY_ID_SOUND_AND_ID_USER(?,?)";

        public void createFavoriteSoundUserByIdSoundAndIdUser(String idSound, String idUser) {
                jdbcTemplate.update(
                                SQL_CREATE_FAVORITE_SOUND_USER_BY_ID_SOUND_AND_ID_USER,
                                new Object[] { idSound, idUser });
        }

        private final String SQL_READ_ALL_SOUND_BY_ID_LIST_SOUND = "SELECT * FROM sound WHERE id_type_sound = ?";

        public List<Sound> findAllSound() {

                List<Sound> records = new ArrayList<>();
                records = jdbcTemplate.query(SQL_READ_ALL_SOUND, new SoundMapper());
                return records;
        }

        public List<SoundDTO> readAllSound() {
                List<SoundDTO> records = new ArrayList<>();
                records = jdbcTemplate.query(SQL_READ_ALL_SOUND, new SoundReadMapper());
                return records;
        }

        public List<SoundDTO> readAllSoundByIdAlbumIsNull() {
                List<SoundDTO> records = new ArrayList<>();
                records = jdbcTemplate.query(SQL_READ_ALL_SOUND_BY_ID_ALBUM_IS_NULL, new SoundReadMapper());
                return records;
        }

        public Map<String, List<SoundDTO>> readAllSoundByIdListSound(List<PlaylistDTO> playLists) {
                List<SoundDTO> records = new ArrayList<>();
                Map<String, List<SoundDTO>> playlistDetail = new HashMap<String, List<SoundDTO>>();
                for (PlaylistDTO playlist : playLists) {
                        records = jdbcTemplate.query(
                                        SQL_READ_ALL_SOUND_BY_ID_LIST_SOUND, new SoundReadMapper(),
                                        new Object[] { playlist.getIdPlaylist() });
                        if (records.size() > 0) {
                                playlistDetail.put(playlist.getIdPlaylist(), records);
                        }

                }
                return playlistDetail;
        }

        public List<SoundDTO> readAllSoundHaveLimit(int rowLimit) {
                List<SoundDTO> records = new ArrayList<>();
                records = jdbcTemplate.query(SQL_READ_ALL_SOUND_HAVE_LIMIT_AND_RANDOM, new SoundReadMapper(),
                                new Object[] { rowLimit });
                return records;
        }

        public List<SoundDTO> readAllSoundWithKeyDetail(String key) {
                List<SoundDTO> records = new ArrayList<>();
                records = jdbcTemplate.query(
                                SQL_READ_ALL_SOUND_WITH_KEY_DETAIL, new SoundReadMapper(),
                                new Object[] { key });
                return records;
        }

        public List<SoundDTO> readAllSoundByIdUserFromFavoriteSoundUser(String idUser) {
                List<SoundDTO> records = new ArrayList<>();
                List<Map<String, Object>> rows = jdbcTemplate
                                .queryForList(SQL_READ_ALL_SOUND_BY_ID_USER_FROM_FAVORITE_SOUND_USER, idUser);
                for (Map<String, Object> row : rows) {
                        SoundDTO record = new SoundDTO();
                        String idSound = row.get("id_sound").toString();

                        record = readSoundByIdSound(idSound);
                        records.add(record);
                }
                return records;
        }

        public SoundDTO readSoundByIdSound(String idSound) {
                SoundDTO record = jdbcTemplate.queryForObject(SQL_READ_SOUND_BY_ID_SOUND, new SoundReadMapper(),
                                new Object[] { idSound });
                return record;
        }

        public List<SoundDTO> readAllSoundByIdAlbum(String idAlbum) {
                List<SoundDTO> records = new ArrayList<>();
                records = jdbcTemplate.query(SQL_READ_ALL_SOUND_BY_ID_ALBUM, new SoundReadMapper(),
                                new Object[] { idAlbum });
                return records;
        }

        public void updateIdAlbumIsNullByIdAlbumFromSound(String idAlbum) {
                jdbcTemplate.update(SQL_UPDATE_ID_ALBUM_IS_NULL_BY_ID_ALBUM_FROM_SOUND, idAlbum);
        }

        public Sound findSoundByIdSound(String idSound) {
                Sound record = jdbcTemplate.queryForObject(SQL_READ_SOUND_BY_ID_SOUND, new SoundMapper(), idSound);
                return record;
        }

        public List<Sound> findAllSoundByIdUser(String idUser) {

                List<Sound> records = jdbcTemplate.query(
                                SQL_READ_ALL_SOUND_BY_ID_USER,
                                new SoundMapper(),
                                idUser);

                return records;
        }

        public List<Sound> findAllSoundByIdPlaylist(String idPlaylist) {

                List<Sound> records = jdbcTemplate.query(
                                SQL_READ_ALL_SOUND_BY_ID_PLAYLIST,
                                new SoundPlaylistMapper(),
                                idPlaylist);

                return records;
        }

        public List<Sound> findAllSoundByIdAlbum(String idAlbum) {

                List<Sound> records = jdbcTemplate.query(
                                SQL_READ_ALL_SOUND_BY_ID_ALBUM,
                                new SoundMapper(),
                                idAlbum);

                return records;
        }

        public void insertSound(Sound sound) {
                EntityManager entityManager = entityManagerFactory.createEntityManager();
                EntityTransaction entityTransaction = entityManager.getTransaction();
                entityTransaction.begin();
                try {
                        entityManager.persist(sound);
                        entityTransaction.commit();
                } catch (Exception ex) {
                        entityTransaction.rollback();
                } finally {
                        entityManager.close();
                }
        }

        // tính năng: lấy id trước khi lưu vào cơ sở dữ liệu
        public String getIdSoundBeforeInsert(Sound sound) {
                EntityManager entityManager = entityManagerFactory.createEntityManager();
                EntityTransaction entityTransaction = entityManager.getTransaction();
                entityTransaction.begin();
                try {
                        entityManager.persist(sound);
                        entityTransaction.commit();
                        String id = sound.getId().toString();
                        return id;
                } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                        entityTransaction.rollback();
                } finally {
                        entityManager.close();
                }
                return null;
        }

        // tính năng lấy id Sound trước khi lưu Sound vào cở sở dữ liệu

        public String readIdSoundBeforeCreateSound(Sound sound) {
                EntityManager entityManager = entityManagerFactory.createEntityManager();
                EntityTransaction entityTransaction = entityManager.getTransaction();
                entityTransaction.begin();
                try {
                        entityManager.persist(sound);
                        entityTransaction.commit();
                        String id = sound.getId().toString();
                        return id;
                } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                        entityTransaction.rollback();
                } finally {
                        entityManager.close();
                }
                return null;
        }

        // tính năng: cập nhật các thông tin của sound
        public void updateSoundByIdSound(Sound sound) {

                String idSound = sound.getId().toString();
                String nameSound = sound.getNameSound();
                TypeSound typeSound = sound.getTypeSound();
                Long idTypeSound = typeSound.getIdTypeSound();

                try {
                        jdbcTemplate.update(SQL_UPDATE_SOUND, nameSound, idTypeSound, idSound);
                } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                }
        }

        public void updateSoundByIdSound(SoundDTO sound) {
                String idSound = sound.getIdSound();
                String nameSound = sound.getNameSound();
                String nameSinger = sound.getNameSinger();

                try {
                        jdbcTemplate.update(SQL_UPDATE_SOUND_3_ARGUMENT, nameSound, nameSinger, idSound);
                } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                }
        }

        public void updateIdAlbumByIdSound(String idAlbum, String idSound) {
                jdbcTemplate.update(SQL_UPDATE_ID_ALBUM_BY_ID_SOUND, idAlbum, idSound);
        }

        // cập nhật lại đường dẫn bài ảnh đại diện của bài hát
        public void updatePathImageByIdSound(String idSound, String pathImage) {
                jdbcTemplate.update(SQL_UPDATE_PATH_IMAGE_BY_ID_SOUND, pathImage, idSound);
        }

        public void updateNameSoundAndNameSingerByIdSound(SoundDTO sound) {
                String idSound = sound.getIdSound();
                String nameSound = sound.getNameSound();
                String nameSinger = sound.getNameSinger();
                jdbcTemplate.update(SQL_UPDATE_NAME_SOUND_AND_NAME_SINGER_BY_ID_SOUND, nameSound, nameSinger, idSound);
        }

        // cập nhật lại đường dẫn của link audio
        public void updatePathAudioByIdSound(String idSound, String pathAudio) {
                jdbcTemplate.update(SQL_UPDATE_PATH_AUDIO_BY_ID_SOUND, pathAudio, idSound);
        }

        public void deleteSoundByIdSound(String idSound) throws Exception {
                jdbcTemplate.update(SQL_DELETE_SOUND_BY_ID_SOUND, idSound);
        }

        public void deleteFavoriteSoundUserByIdSoundAndIdUser(String idSound, String idUser) {
                jdbcTemplate.update(SQL_DELETE_FAVORITE_SOUND_USER_BY_ID_SOUND_AND_ID_USER,
                                new Object[] { idSound, idUser });
        }

}
