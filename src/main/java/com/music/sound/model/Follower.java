package com.music.sound.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import javax.persistence.GenerationType;

@Table(name = "FOLLOWER")
@Entity
@Data
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @OneToOne
    @JoinColumn(name = "id_user_first", referencedColumnName = "id_user")
    private User userFirst;

    @OneToOne
    @JoinColumn(name = "id_user_second", referencedColumnName = "id_user")
    private User userSecond;

    @Column(name = "relationship")
    private String relationship;
}
