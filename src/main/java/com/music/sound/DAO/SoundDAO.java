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

@Repository
public class SoundDAO {

        @Autowired
        private JdbcTemplate jdbcTemplate;

        @Autowired
        private EntityManagerFactory entityManagerFactory;

        // sql
        private final String SQL_READ_ALL_SOUND = "SELECT * FROM SOUND";

        private final String SQL_READ_SOUND_BY_ID_SOUND = "SELECT * FROM SOUND WHERE id_sound = ?";

        private final String SQL_READ_ALL_SOUND_BY_ID_USER = "SELECT * FROM SOUND WHERE id_user = ?";

        private final String SQL_READ_ALL_SOUND_BY_ID_PLAYLIST = "SELECT * FROM SOUND_ALBUM WHERE id_playlist = ?";

        private final String SQL_READ_ALL_SOUND_BY_ID_ALBUM = "SELECT * FROM SOUND WHERE id_album = ?";

        private final String SQL_UPDATE_SOUND = "UPDATE SOUND SET name_sound = ?, id_type_sound = ?  WHERE id_sound = ? ";

        public List<Sound> findAllSound() {

                List<Sound> records = jdbcTemplate.query(SQL_READ_ALL_SOUND, new SoundMapper());

                return records;
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
                        System.out.println(sound.getId().toString());
                        entityTransaction.commit();
                } catch (Exception ex) {
                        System.out.println(ex.getMessage());
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

        // tính năng: cập nhật các thông tin của sound
        public void updateSoundByIdSound(Sound sound) {

                String idSound = sound.getId().toString();
                String nameSound = sound.getNameSound();
                TypeSound typeSound = sound.getTypeSound();
                Long idTypeSound = typeSound.getIdTypeSound();

                try {
                        jdbcTemplate.update(SQL_UPDATE_SOUND, nameSound, idTypeSound, idSound);
                        System.out.println("update success");
                } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                }
        }

}
