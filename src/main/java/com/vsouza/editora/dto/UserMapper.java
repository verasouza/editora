package com.vsouza.editora.dto;

import com.vsouza.editora.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserDTO userDTO);
    UserDTO toUserDTO(User user);
}
