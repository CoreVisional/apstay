package com.apu.apstay.staff.manager.models.inputmodels;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author alexc
 */
public class UnitInputModel {
    private Long id;
    
    @NotBlank(message = "Unit Name must not be blank!")
    private String unitName;
    
    @NotNull(message = "Floor number must not be null!")
    @Min(value = 1, message = "Floor number must be at least 1")
    private int floorNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
