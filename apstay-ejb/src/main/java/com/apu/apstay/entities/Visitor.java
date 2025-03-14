package com.apu.apstay.entities;

import com.apu.apstay.entities.common.BaseModel;
import com.apu.apstay.enums.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author alexc
 */
@Entity
public class Visitor extends BaseModel {
    
    // <editor-fold defaultstate="collapsed" desc="Properties">
    @OneToMany(mappedBy = "visitor")
    private Set<VisitRequest> visitRequests = new HashSet<>();
    
    @NotBlank
    @Column(nullable = false)
    private String name;
    
    @NotBlank
    @Column(nullable = false, unique = true)
    private String identityNumber;
    
    @Column(nullable = false)
    private Gender gender;
    
    @NotBlank
    @Column(nullable = false)
    private String phone;
    // </editor-fold>    

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public Visitor() {
    }

    public Visitor(String name, String identityNumber, Gender gender, String phone) {
        this.name = name;
        this.identityNumber = identityNumber;
        this.gender = gender;
        this.phone = phone;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public Set<VisitRequest> getVisitRequests() {
        return visitRequests;
    }

    public void setVisitRequests(Set<VisitRequest> visitRequests) {
        this.visitRequests = visitRequests;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    // </editor-fold>
}
