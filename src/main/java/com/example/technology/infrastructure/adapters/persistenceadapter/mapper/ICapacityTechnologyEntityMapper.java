package com.example.technology.infrastructure.adapters.persistenceadapter.mapper;

import com.example.technology.domain.model.CapacityTechnology;
import com.example.technology.infrastructure.adapters.persistenceadapter.entity.CapacityTechnologyEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICapacityTechnologyEntityMapper {
    CapacityTechnologyEntity toEntity(CapacityTechnology mode);
    CapacityTechnology toModel(CapacityTechnologyEntity entity);
}
