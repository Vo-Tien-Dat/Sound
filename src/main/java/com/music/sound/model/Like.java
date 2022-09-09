package com.music.sound.model;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;

@Table(name = "LIKE")
@Entity
@Data
public class Like {
    @Id 
    @Column(name = "id_sound")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ; 


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user_01", referencedColumnName = "id_user")
    private User user; 

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_sound", referencedColumnName = "id_sound")
    private Sound sound; 
}
