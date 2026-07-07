package com.example.technology.infrastructure.entrypoints.mapper;

import com.example.technology.domain.model.Technology;
import com.example.technology.infrastructure.entrypoints.dto.technology.CreateTechnologyDto;
import com.example.technology.infrastructure.entrypoints.dto.technology.TechnologyDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ITechnologyDtoMapper {
    @Mapping(target = "id", ignore = true)
    Technology fromCreateToModel(CreateTechnologyDto dto);
    TechnologyDto fromModelToResponse(Technology model);
}
