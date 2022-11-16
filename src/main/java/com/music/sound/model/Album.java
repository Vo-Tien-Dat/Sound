package com.music.sound.model;

import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "ALBUM")
@Data
public class Album {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    @Column(name = "id_album", columnDefinition = "VARCHAR(40)")
    private UUID id;

    @Column(name = "name_album")
    private String nameAlbum;

    @Column(name = "name_singer")
    private String nameSinger;

    @Column(name = "number_viewer")
    private Long numberViewer;

    @Column(name = "number_sound")
    private Long numberSound;

    @Column(name = "path_image")
    private String pathImage;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    private Set<Sound> sounds;

    @ManyToMany
    @JoinTable(name = "favorite_album_user", joinColumns = @JoinColumn(name = "id_album", referencedColumnName = "id_album"), inverseJoinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id_user"))
    private List<User> users;

    public Album() {
        this.id = null;
        this.nameAlbum = null;
        this.numberViewer = Long.valueOf(0);
        this.numberSound = Long.valueOf(0);
        this.pathImage = null;
    }
}
