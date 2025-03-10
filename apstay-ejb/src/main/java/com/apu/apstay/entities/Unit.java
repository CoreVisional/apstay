package com.apu.apstay.entities;

import com.apu.apstay.entities.common.BaseModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

/**
 *
 * @author alexc
 */
@Entity
public class Unit extends BaseModel {

    // <editor-fold defaultstate="collapsed" desc="Properties">
    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL)
    private Set<Resident> residents;
    
    @NotBlank
    @Column(name = "unit_name", unique = true, nullable = false)
    private String unitName;
    
    @NotNull
    @Min(value = 1, message = "Floor number must be at least 1")
    @Column(name = "floor_number", nullable = false)
    private int floorNumber;
    
    @Column(nullable = false)
    private boolean occupied = false;
    
    @Column(nullable = false)
    private boolean active = true;
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public Unit() {
    }

    public Unit(String unitName, int floorNumber, Set<Resident> residents) {
        this.unitName = unitName;
        this.floorNumber = floorNumber;
        this.residents = residents;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Resident> getResidents() {
        return residents;
    }

    public void setResidents(Set<Resident> residents) {
        this.residents = residents;
    }
    // </editor-fold>
}
