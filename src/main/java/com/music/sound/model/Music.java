package com.music.sound.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity

public class Music {
    @Id 
    @Column(name = "id_music")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id ; 


    @Column(name = "music_name")
    private String musicName;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false, referencedColumnName = "id_user")
    private User user; 

    public String getMusicname(){
        return this.musicName;
    }

    public User getUser(){
        return this.user;
    }
}
