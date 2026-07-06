package com.example.technology.infrastructure.entrypoints.mapper;

import com.example.technology.domain.model.Technology;
import com.example.technology.infrastructure.entrypoints.dto.technology.CreateTechnologyDto;
import com.example.technology.infrastructure.entrypoints.dto.technology.TechnologyDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ITechnologyDtoMapper {
    Technology fromCreateToModel(CreateTechnologyDto dto);
    TechnologyDto fromModelToResponse(Technology model);
}
