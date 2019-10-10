package com.example.paginationandprojection.dto.mappers;

import com.example.paginationandprojection.dto.UserEntityDto;
import com.example.paginationandprojection.model.entity.UserDetailsEntity;
import com.example.paginationandprojection.model.entity.UserEntity;

public class UserMapper {

    public static UserEntity toEntity(UserEntityDto userDto, UserEntity user){
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());

        if (user.getDetails() == null){
            UserDetailsEntity details = new UserDetailsEntity();
            details.setDateOfBirth(userDto.getDateOfBirth());
            details.setFirstName(userDto.getFirstName());
            details.setLastName(userDto.getLastName());
            details.setPesel(userDto.getPesel());
            details.setOwner(user);
            user.setDetails(details);
        } else{
            user.getDetails().setDateOfBirth(userDto.getDateOfBirth());
            user.getDetails().setFirstName(userDto.getFirstName());
            user.getDetails().setLastName(userDto.getLastName());
            user.getDetails().setPesel(userDto.getPesel());
        }
        return user;
    }

    public static UserEntityDto toDto(UserEntity user){
        UserEntityDto userDto = new UserEntityDto();
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setEnabled(user.getEnabled());
        userDto.setId(user.getId());
        if (user.getDetails() != null){
            userDto.setFirstName(user.getDetails().getFirstName());
            userDto.setLastName(user.getDetails().getLastName());
            userDto.setDateOfBirth(user.getDetails().getDateOfBirth());
            userDto.setPesel(user.getDetails().getPesel());
        }
        return userDto;
    }
}
