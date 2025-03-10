package com.apu.apstay.entities;

import com.apu.apstay.entities.common.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author alexc
 */
@Entity
public class Role extends BaseModel {
    
    // <editor-fold defaultstate="collapsed" desc="Properties">
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();
    
    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public Set<User> getUsers() {
        return Collections.unmodifiableSet(users);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    // </editor-fold>
}
