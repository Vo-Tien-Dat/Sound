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
@Table(name = "SOUND")
@Entity
@Data

public class Sound {
    @Id 
    @Column(name = "id_sound")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id ; 

    @Column(name = "name_sound", unique = true)
    private String nameSound;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = true, referencedColumnName = "id_user")
    private User user; 

    @Column(name = "viewer")
    private Long viewer; 

    @Column(name = "link_sound", nullable = false)
    private String linkSound; 

    @ManyToOne
    @JoinColumn(name = "id_album", nullable = true, referencedColumnName = "id_album")
    private Album album; 
}
