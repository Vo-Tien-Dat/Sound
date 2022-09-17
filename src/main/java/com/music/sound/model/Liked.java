package com.music.sound.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import javax.persistence.GenerationType;

@Table(name = "LIKED")
@Entity
@Data
public class Liked {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ; 

    @OneToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    private User user; 

    @OneToOne
    @JoinColumn(name = "id_sound", referencedColumnName = "id_sound")
    private Sound sound; 
}
