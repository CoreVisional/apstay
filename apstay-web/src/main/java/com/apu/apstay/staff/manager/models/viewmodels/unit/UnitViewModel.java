package com.apu.apstay.staff.manager.models.viewmodels.unit;

import com.apu.apstay.dtos.UnitDto;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author alexc
 */
public record UnitViewModel(
        Long id,
        String unitName,
        int floorNumber,
        int capacity,
        int currentOccupancy,
        boolean hasAvailableSpace,
        boolean active
) {
    public static List<UnitViewModel> from(List<UnitDto> dtos) {
        if (dtos == null) {
            return List.of();
        }
        return dtos.stream()
                  .map(UnitViewModel::from)
                  .collect(Collectors.toList());
    }
    
    public static UnitViewModel from(UnitDto dto) {
        if (dto == null) {
            return null;
        }
        return new UnitViewModel(
            dto.id(),
            dto.unitName(),
            dto.floorNumber(),
            dto.capacity(),
            dto.currentOccupancy(),
            dto.hasAvailableSpace(),
            dto.active()
        );
    }
}
