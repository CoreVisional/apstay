package com.apu.apstay.entities;

import com.apu.apstay.enums.ApprovalStatus;
import com.apu.apstay.entities.common.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author alexc
 */
@Entity
@Table(name = "account_registration")
public class AccountRegistration extends BaseModel {

    // <editor-fold defaultstate="collapsed" desc="Properties">
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "registrant_id", nullable = false)
    private User registrant;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reviewer_id")
    private User reviewer;
    
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;
    
    @NotBlank
    @Column(nullable = false)
    private String name;
    
    @NotBlank
    @Column(name = "identity_number", nullable = false)
    private String identityNumber;
    
    @NotBlank
    @Email(message = "Please provide a valid email address.")
    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String gender;
    
    @Column(nullable = false)
    private String phone;
    
    @Column(columnDefinition = "TEXT")
    private String address;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApprovalStatus status;
    
    @Column(columnDefinition = "TEXT", nullable = false)
    private String remarks = null;
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public AccountRegistration() {
    }

    public AccountRegistration(User registrant, User reviewer, Unit unit, String name, String identityNumber, String email, String gender, String phone, String address, ApprovalStatus status) {
        this.registrant = registrant;
        this.reviewer = reviewer;
        this.unit = unit;
        this.name = name;
        this.identityNumber = identityNumber;
        this.email = email;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.status = status;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public User getRegistrant() {
        return registrant;
    }

    public void setRegistrant(User registrant) {
        this.registrant = registrant;
    }
    
    public User getReviewer() {
        return reviewer;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ApprovalStatus getStatus() {
        return status;
    }

    public void setStatus(ApprovalStatus status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    // </editor-fold>
}

