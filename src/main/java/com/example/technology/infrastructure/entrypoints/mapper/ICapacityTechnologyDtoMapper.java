package com.example.technology.infrastructure.entrypoints.mapper;

import com.example.technology.domain.model.CapacityTechnology;
import com.example.technology.infrastructure.entrypoints.dto.capacitytechnology.CreateCapacityTechnologyDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICapacityTechnologyDtoMapper {
    default List<CapacityTechnology> toModel(CreateCapacityTechnologyDto dto) {
        return dto.technologyIds()
                .stream()
                .map(technologyId -> CapacityTechnology.builder()
                        .capacityId(dto.capacityId())
                        .technologyId(technologyId)
                        .build())
                .toList();
    }
}
