package com.apu.apstay.entities;

import com.apu.apstay.enums.CommuteMode;
import com.apu.apstay.entities.common.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author alexc
 */
@Entity
@Table(name = "security")
public class Security extends BaseModel {
    
    // <editor-fold defaultstate="collapsed" desc="Properties">
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CommuteMode commuteMode;
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public Security() {
    }

    public Security(User user, CommuteMode commuteMode) {
        this.user = user;
        this.commuteMode = commuteMode;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CommuteMode getCommuteMode() {
        return commuteMode;
    }

    public void setCommuteMode(CommuteMode commuteMode) {
        this.commuteMode = commuteMode;
    }
    // </editor-fold>
}
