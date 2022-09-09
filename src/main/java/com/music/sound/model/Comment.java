package com.music.sound.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

import javax.persistence.GenerationType;


@Table(name = "COMMENT")
@Entity
@Data
public class Comment {
    @Id
    @Column(name = "id_comment")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    @Column(name = "content")
    private String content; 

    @OneToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    private User user ; 

    @OneToOne
    @JoinColumn(name = "id_sound", referencedColumnName = "id_sound")
    private Sound sound; 
}
