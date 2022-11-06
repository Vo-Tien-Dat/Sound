package com.music.sound.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;
import lombok.Data;

@Entity
@Table(name = "Role")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_role", nullable = false, length = 10)
    String idRole;

    @Column(name = "name_role")
    String nameRole;

    @Column(name = "description_role")
    String descriptionRole;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
