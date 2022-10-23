package com.music.sound.DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.music.sound.model.Sound;
import com.music.sound.model.User;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import com.music.sound.model.Album;

@Repository
public class SoundDAO {

        @Autowired
        private JdbcTemplate jdbcTemplate;

        @Autowired
        private EntityManagerFactory entityManagerFactory;

        @Autowired
        private UserDAO userDAO;

        @Autowired
        private AlbumDAO albumDAO;

        // sql
        private final String SQL_FIND_ALL_SOUND = "SELECT * FROM SOUND";

        private final String SQL_FIND_ALL_SOUND_BY_ID_USER = "SELECT * FROM SOUND WHERE id_user = ?";

        public List<Sound> findAllSound() {
                List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_FIND_ALL_SOUND);

                List<Sound> result = new ArrayList<>();

                for (Map<String, Object> row : rows) {
                        Sound sound = new Sound();

                        UUID idSound = UUID.fromString(row.get("id_sound").toString());
                        String nameSound = row.get("name_sound") != null
                                        ? row.get("name_sound").toString()
                                        : "";
                        Long numberViewer = row.get("number_viewer") != null
                                        ? Long.parseLong(row.get("number_viewer").toString())
                                        : Long.valueOf(0);
                        String pathImage = row.get("path_image") != null
                                        ? row.get("path_image").toString()
                                        : "";
                        String pathAudio = row.get("path_audio") != null
                                        ? row.get("path_auido").toString()
                                        : "";
                        String idUser = row.get("id_user") != null
                                        ? row.get("id_user").toString()
                                        : "";
                        String idAlbum = row.get("id_album") != null
                                        ? row.get("id_album").toString()
                                        : "";

                        User user = userDAO.findUserById(idUser);
                        Album album = albumDAO.findAlbumByIdAlbum(idAlbum);

                        sound.setId(idSound);
                        sound.setNameSound(nameSound);
                        sound.setNumberViewer(numberViewer);
                        sound.setPathAudio(pathAudio);
                        sound.setPathImage(pathImage);
                        sound.setUser(user);
                        sound.setAlbum(album);

                        result.add(sound);

                }
                return result;
        }

        public List<Sound> findSoundByIdUser(String idUser) {
                List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_FIND_ALL_SOUND_BY_ID_USER, idUser);

                List<Sound> result = new ArrayList<>();

                for (Map<String, Object> row : rows) {
                        Sound sound = new Sound();

                        UUID idSound = UUID.fromString(row.get("id_sound").toString());
                        String nameSound = row.get("name_sound") != null
                                        ? row.get("name_sound").toString()
                                        : "";

                        Long numberViewer = row.get("number_viewer") != null
                                        ? Long.parseLong(row.get("number_viewer").toString())
                                        : Long.valueOf(0);

                        sound.setId(idSound);
                        sound.setNameSound(nameSound);
                        sound.setNumberViewer(numberViewer);

                        result.add(sound);

                }
                return result;
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

}
