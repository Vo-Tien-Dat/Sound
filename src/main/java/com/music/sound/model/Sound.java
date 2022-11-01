package com.music.sound.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "SOUND")
@Data

public class Sound {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    @Column(name = "id_sound", columnDefinition = "VARCHAR(40)")
    private UUID id;

    @Column(name = "name_sound")
    private String nameSound;

    @Column(name = "path_image")
    private String pathImage;

    @Column(name = "number_viewer")
    private Long numberViewer;

    @Column(name = "path_audio", nullable = true)
    private String pathAudio;

    @ManyToOne
    @JoinColumn(name = "id_album", referencedColumnName = "id_album", nullable = true)
    private Album album;

    @ManyToOne
    @JoinColumn(name = "id_playlist", referencedColumnName = "id_playlist", nullable = true)
    private Playlist playlist;

    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", nullable = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_type_sound", referencedColumnName = "id_type_sound", nullable = true)
    private TypeSound typeSound;
}
