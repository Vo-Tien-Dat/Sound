package com.music.sound.model;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "ALBUM")
@Data
public class Album {

    @Id
    @Column(name = "id_album")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    Set<Sound> sounds;
}
