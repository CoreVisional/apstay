package com.apu.apstay.entities;

import com.apu.apstay.entities.common.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 *
 * @author alexc
 */
@Entity
@Table(name = "visit_code")
public class VisitCode extends BaseModel {
    
    // <editor-fold defaultstate="collapsed" desc="Properties">
    @OneToOne
    @JoinColumn(name = "visit_request_id", nullable = false)
    private VisitRequest visitRequest;
    
    @Column(unique = true, nullable = false)
    private String code;
    
    @Column(nullable = false)
    private boolean active = true;
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public VisitCode() {
    }

    public VisitCode(VisitRequest visitRequest, String code) {
        this.visitRequest = visitRequest;
        this.code = code;
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public VisitRequest getVisitRequest() {
        return visitRequest;
    }

    public void setVisitRequest(VisitRequest visitRequest) {
        this.visitRequest = visitRequest;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    //</editor-fold>
}
