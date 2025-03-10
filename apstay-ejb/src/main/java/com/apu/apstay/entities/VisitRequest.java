package com.apu.apstay.entities;

import com.apu.apstay.enums.VisitRequestStatus;
import com.apu.apstay.entities.common.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 *
 * @author alexc
 */
@Entity
@Table(name = "visit_request")
public class VisitRequest extends BaseModel {
    
    // <editor-fold defaultstate="collapsed" desc="Properties">
    @ManyToOne
    @JoinColumn(name = "visitor_id", nullable = false)
    private Visitor visitor;
    
    @ManyToOne
    @JoinColumn(name = "resident_id", nullable = false)
    private Resident resident;
    
    @NotNull
    @Column(name = "arrival_date_time", nullable = false)
    private LocalDateTime arrivalDateTime;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VisitRequestStatus status;
    
    @Column(nullable = false)
    private boolean active = true;
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public VisitRequest() {
    }

    public VisitRequest(Visitor visitor, Resident resident, LocalDateTime arrivalDateTime, VisitRequestStatus status) {
        this.visitor = visitor;
        this.resident = resident;
        this.arrivalDateTime = arrivalDateTime;
        this.status = status;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    public Resident getResident() {
        return resident;
    }

    public void setResident(Resident resident) {
        this.resident = resident;
    }

    public LocalDateTime getArrivalDateTime() {
        return arrivalDateTime;
    }

    public void setArrivalDateTime(LocalDateTime arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
    }

    public VisitRequestStatus getStatus() {
        return status;
    }

    public void setStatus(VisitRequestStatus status) {
        this.status = status;
    }
    
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    // </editor-fold>
}