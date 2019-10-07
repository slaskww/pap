package com.example.paginationandprojection.dto.mappers;

import com.example.paginationandprojection.dto.UserEntityDto;
import com.example.paginationandprojection.model.entity.UserEntity;

public class UserMapper {

    public static UserEntity toEntity(UserEntityDto userDto){
        UserEntity user = new UserEntity();
        return user;
    }

    public static UserEntityDto toDto(UserEntity user){

        UserEntityDto userDto = new UserEntityDto();
        return userDto;
    }
}
