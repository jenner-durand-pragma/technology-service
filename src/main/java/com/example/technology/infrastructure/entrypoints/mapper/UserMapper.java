package com.example.technology.infrastructure.entrypoints.mapper;

import com.example.technology.domain.model.User;
import com.example.technology.infrastructure.entrypoints.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    User userDTOToUser(UserDTO userDTO);
}
