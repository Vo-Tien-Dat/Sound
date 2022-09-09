package com.music.sound.model;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "FOLLOWER")
@Entity
@Data
public class Follower {
    @Id 
    @Column(name = "id_follower")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ; 

    // @OneToOne
    // @JoinColumn(name = "id_user_first", referencedColumnName = "id_user")
    // private User idUserFirst; 


    // @OneToOne
    // @JoinColumn(name = "id_user_second", referencedColumnName = "id_user")
    // private User idUserSecond; 

    @Column(name = "both")
    private String both; 

}
