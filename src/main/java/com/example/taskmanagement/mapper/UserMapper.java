package com.example.taskmanagement.mapper;

import com.example.taskmanagement.dto.UserResponse;
import com.example.taskmanagement.entity.Role;
import com.example.taskmanagement.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roles", source = "roles", qualifiedByName = "rolesToStringSet")
    UserResponse toResponse(User user);

    @Named("rolesToStringSet")
    default Set<String> rolesToStringSet(Set<Role> roles) {
        if (roles == null) return Set.of();
        return roles.stream()
                .map(Enum::name)
                .collect(Collectors.toSet());
    }
}