package com.music.sound.DAO;

import java.sql.SQLException;
import java.util.UUID;
import com.music.sound.model.Album;
import com.music.sound.model.Sound;
import com.music.sound.model.TypeSound;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import com.music.sound.model.User;

public class SoundMapper implements RowMapper<Sound> {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private AlbumDAO albumDAO;

    @Autowired
    private TypeSoundDAO typeSoundDAO;

    public Sound mapRow(ResultSet rs, int rowNum) throws SQLException {

        Sound sound = new Sound();

        UUID uuid = UUID.fromString(rs.getString("id_sound"));
        String nameSound = rs.getString("name_sound");
        Long numberView = Long.valueOf(rs.getString("number_viewer"));
        String pathAudio = rs.getString("path_audio");
        String pathImage = rs.getString("path_image");
        String idAlbum = rs.getString("id_album");
        Long idTypeSound = Long.valueOf(rs.getString("id_type_sound"));
        // String idUser = rs.getString("id_user");

        // User user = userDAO.findUserById(idUser);
        // Album album = albumDAO.findAlbumByIdAlbum(idAlbum);
        // TypeSound typeSound = typeSoundDAO.findTypeSoundByIdTypeSound(idTypeSound);

        sound.setId(uuid);
        sound.setNameSound(nameSound);
        sound.setNumberViewer(numberView);
        sound.setPathAudio(pathAudio);
        sound.setPathImage(pathImage);
        // sound.setUser(user);
        // sound.setAlbum(album);
        // sound.setTypeSound(typeSound);

        return sound;
    }
}
