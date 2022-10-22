package com.music.sound.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import lombok.Data;


@Entity
@Table(name = "PLAYLIST")
@Data

public class Playlist {
    @Id
    @Column(name = "id_playlist")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name_playlist", unique = true)
    private String namePlaylist;

    @Column(name = "number_view", nullable = false)
    private Long numberView;

    @Column(name = "number_sound", nullable = false)
    private Long numberSound;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

}
