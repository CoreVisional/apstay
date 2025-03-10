package com.apu.apstay.staff.manager.models.viewmodels.unit;

import com.apu.apstay.dtos.UnitDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author alexc
 */
public class UnitViewModel {
    
    // <editor-fold defaultstate="collapsed" desc="Variables">
    private final Long id;
    private final String unitName;
    private final int floorNumber;
    private final boolean occupied;
    private final boolean active;
    private final LocalDateTime createdAt;
    private final Long createdBy;
    private final LocalDateTime modifiedAt;
    private final Long modifiedBy;
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">    
    public UnitViewModel(UnitDto dto) {
        this.id = dto.id();
        this.unitName = dto.unitName();
        this.floorNumber = dto.floorNumber();
        this.occupied = dto.occupied();
        this.active = dto.active();
        this.createdAt = dto.createdAt();
        this.createdBy = dto.createdBy();
        this.modifiedAt = dto.modifiedAt();
        this.modifiedBy = dto.modifiedBy();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters">
    public Long getId() { return id; }
    public String getUnitName() { return unitName; }
    public int getFloorNumber() { return floorNumber; }
    public boolean isOccupied() { return occupied; }
    public boolean isActive() { return active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Long getCreatedBy() { return createdBy; }
    public LocalDateTime getModifiedAt() { return modifiedAt; }
    public Long getModifiedBy() { return modifiedBy; }
    // </editor-fold>
    
    public static List<UnitViewModel> from(List<UnitDto> dtos) {
        return dtos.stream()
                   .map(UnitViewModel::new)
                   .collect(Collectors.toList());
    }
    
    public static UnitViewModel from(UnitDto dto) {
        if (dto == null) {
            return null;
        }
        return new UnitViewModel(dto);
    }
}
