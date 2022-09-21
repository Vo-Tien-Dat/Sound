package com.music.sound.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Data;
@Entity
//@Table(name = "USER",uniqueConstraints = @UniqueConstraint(name = "unique_constraints_user",  columnNames = {"username", "email"}))
@Data
public class User {
    
    @Id 
    @Column(name = "id_user")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id ; 

    
    @Column(name = "username", nullable = false, length = 50, unique = true)
    private String username; 

    
    @Column(name = "password", nullable = false)
    private String password; 


    @Column(name = "email", nullable = false, length = 50, unique = true)
    private String email; 


    @Column(name = "location")
    private String location; 


    @Column(name = "path_img_avatar")
    private String pathImgAvatar; 

    @Column(name = "path_img_background")
    private String pathImgBackground; 

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Sound> sounds;    
}
