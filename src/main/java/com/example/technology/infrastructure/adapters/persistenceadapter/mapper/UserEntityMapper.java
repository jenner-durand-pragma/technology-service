package com.example.technology.infrastructure.adapters.persistenceadapter.mapper;

import com.example.technology.domain.model.User;
import com.example.technology.infrastructure.adapters.persistenceadapter.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    User toModel(UserEntity entity);
    UserEntity toEntity(User user);
}
