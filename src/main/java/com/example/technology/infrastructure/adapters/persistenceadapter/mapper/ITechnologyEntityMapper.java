package com.example.technology.infrastructure.adapters.persistenceadapter.mapper;

import com.example.technology.domain.model.Technology;
import com.example.technology.infrastructure.adapters.persistenceadapter.entity.TechnologyEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ITechnologyEntityMapper {
    Technology toModel(TechnologyEntity entity);
    TechnologyEntity toEntity(Technology model);
}
