package com.example.paginationandprojection.dto.mappers;

import com.example.paginationandprojection.dto.UserEntityDto;
import com.example.paginationandprojection.model.entity.UserEntity;

public class UserMapper {

    public static UserEntity toEntity(UserEntityDto userDto, UserEntity user){
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setUsername(userDto.getUsername());
        return user;
    }

    public static UserEntityDto toDto(UserEntity user){
        UserEntityDto userDto = new UserEntityDto();
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setEnabled(user.getEnabled());
        userDto.setId(user.getId());
        userDto.setFirstName(user.getDetails().getFirstName());
        userDto.setLastName(user.getDetails().getLastName());
        userDto.setDateOfBirth(user.getDetails().getDateOfBirth());
        userDto.setPesel(user.getDetails().getPesel());
        return userDto;
    }
}
