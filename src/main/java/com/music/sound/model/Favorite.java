package com.music.sound.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import javax.persistence.ManyToOne;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "FAVORITE")
@Data
public class Favorite {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    @Column(name = "id_favorite", columnDefinition = "VARCHAR(40)")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    private User user;

    @ManyToMany
    @JoinTable(name = "favorite_album", joinColumns = @JoinColumn(name = "id_favorite", referencedColumnName = "id_favorite"), inverseJoinColumns = @JoinColumn(name = "id_album", referencedColumnName = "id_album"))
    private List<Album> albums;

    @ManyToMany
    @JoinTable(name = "favorite_playlist", joinColumns = @JoinColumn(name = "id_favorite", referencedColumnName = "id_favorite"), inverseJoinColumns = @JoinColumn(name = "id_playlist", referencedColumnName = "id_playlist"))
    private List<Playlist> playlists;

    @ManyToMany
    @JoinTable(name = "favorite_sound", joinColumns = @JoinColumn(name = "id_favorite", referencedColumnName = "id_favorite"), inverseJoinColumns = @JoinColumn(name = "id_sound", referencedColumnName = "id_sound"))
    private List<Sound> sounds;
}
