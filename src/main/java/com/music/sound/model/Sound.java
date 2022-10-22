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
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "SOUND")
@Data

public class Sound {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
            @Parameter(name = "uuid_gen_strategy_class", value = "org.hibernate.id.uuid.CustomVersionOneStrategy")
    })
    @Column(name = "id_sound")
    private UUID id;

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
