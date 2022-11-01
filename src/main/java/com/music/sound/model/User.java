package com.music.sound.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import java.util.Set;
import java.util.UUID;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "USER")
@Data
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    @Column(name = "id_user", columnDefinition = "VARCHAR(40)")
    private UUID id;

    @Column(name = "username", nullable = false, length = 50, unique = true)
    private String username;

    @Column(name = "name_user", nullable = false, length = 40)
    private String nameUser;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, length = 50, unique = true)
    private String email;

    @Column(name = "location")
    private String location;

    @Column(name = "number_album")
    private Long numberAlbum;

    @Column(name = "number_follower")
    private Long numberFollower;

    @Column(name = "number_sound")
    private Long numberSound;

    @Column(name = "path_img_avatar")
    private String pathImgAvatar;

    @Column(name = "path_img_background")
    private String pathImgBackground;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Sound> sounds;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Playlist> playlists;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Album> albums;

}
