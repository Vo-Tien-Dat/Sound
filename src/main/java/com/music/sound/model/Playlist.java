package com.music.sound.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import java.util.UUID;
import lombok.Data;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@Table(name = "PLAYLIST")
@Data

public class Playlist {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    @Column(name = "id_playlist", columnDefinition = "VARCHAR(40)")
    private UUID id;

    @Column(name = "name_playlist")
    private String namePlaylist;

    @Column(name = "path_image")
    private String pathImage;

    @Column(name = "number_viewer")
    private Long numberViewer;

    @Column(name = "number_sound")
    private Long numberSound;

    @ManyToMany(mappedBy = "playlists")
    private List<Sound> sounds;

    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    private User user;

    public Playlist() {
        this.id = null;
        this.namePlaylist = null;
        this.pathImage = null;
        this.numberSound = Long.valueOf(0);
        this.numberViewer = Long.valueOf(0);
        this.user = null;
    }
}
