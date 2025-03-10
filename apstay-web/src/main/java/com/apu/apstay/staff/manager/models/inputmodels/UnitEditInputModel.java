package com.apu.apstay.staff.manager.models.inputmodels;

/**
 *
 * @author alexc
 */
public class UnitEditInputModel extends UnitInputModel {
    
    private boolean occupied;
    
    public boolean isOccupied() {
        return occupied;
    }
    
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}
