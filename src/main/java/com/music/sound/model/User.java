package com.music.sound.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import java.util.UUID;
import lombok.Data;
import java.util.List;
import javax.persistence.JoinTable;

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

    @Column(name = "name_user", nullable = true, length = 40)
    private String nameUser;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, length = 50, unique = true)
    private String email;

    @Column(name = "number_album")
    private Long numberAlbum;

    @Column(name = "number_follower")
    private Long numberFollower;

    @Column(name = "number_sound")
    private Long numberSound;

    @Column(name = "path_img_avatar")
    private String pathImgAvatar;

    @Column(name = "path_image")
    private String pathImg;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Sound> sounds;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Playlist> playlists;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Album> albums;

    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id_user"), inverseJoinColumns = @JoinColumn(name = "id_role", referencedColumnName = "id_role"))
    private List<Role> roles;

    public User() {
        this.numberAlbum = Long.valueOf(0);
        this.numberFollower = Long.valueOf(0);
        this.numberSound = Long.valueOf(0);
    }

}
