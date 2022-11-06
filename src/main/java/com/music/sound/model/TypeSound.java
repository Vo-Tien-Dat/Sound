package com.music.sound.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import java.util.Set;
import javax.persistence.CascadeType;

@Entity
@Table(name = "TYPE_SOUND")
@Data
public class TypeSound {
    @Id
    @Column(name = "id_type_sound")
    private Long idTypeSound;

    @Column(name = "name_type_sound", unique = true)
    private String nameTypeSound;

    @OneToMany(mappedBy = "typeSound", cascade = CascadeType.ALL)
    private Set<Sound> sounds;
}
