package com.music.sound.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.Set;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Table(name = "USER",uniqueConstraints = @UniqueConstraint(columnNames = {"username", "email"}))
@Data
public class User {
    
    @Id 
    @Column(name = "id_user")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id ; 
    @Column(name = "username", unique = true)
    private String username; 

    
    @Column(name = "password")
    private String password; 


    @Column(name = "email", unique = true)
    private String email; 


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Sound> sounds;    
}
