package com.music.sound.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "TYPE_SOUND")
@Data
public class TypeSound {
    @Id
    @Column(name = "id_type_sound")
    Long id;

    @Column(name = "name_type", unique = true)
    private String nameType;
}
