package com.music.sound.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
public class User {
    
    @Id 
    @Column(name = "id_user")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id ; 
    @Column(name = "username")
    private String username; 

    @Column(name = "password")
    private String password; 


    @Column(name = "email")
    private String email; 


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Music> listMusic; 

    public String getUsername(){
        return this.username; 
    }

    public String getPassword(){
        return this.password;
    }

    public Set<Music> getListMusic(){
        return this.listMusic;
    }


    public String getEmail(){
        return this.email;
    }

    public void setUsername(String username){
        this.username = username;
    }

   public void setPassword(String password){
        this.password = password;
   }

   public void setEmail(String email){
        this.email = email;
   }
   
}
