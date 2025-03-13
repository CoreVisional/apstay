package com.apu.apstay.staff.manager.models.inputmodels;

/**
 *
 * @author alexc
 */
public class UnitEditInputModel extends UnitInputModel {
    
    private boolean active = true;
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
}