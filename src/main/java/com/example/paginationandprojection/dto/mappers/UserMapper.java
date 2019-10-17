package com.example.paginationandprojection.dto.mappers;

import com.example.paginationandprojection.dto.FileEntityDto;
import com.example.paginationandprojection.dto.UserEntityDto;
import com.example.paginationandprojection.model.entity.UserDetailsEntity;
import com.example.paginationandprojection.model.entity.UserEntity;
import com.example.paginationandprojection.model.entity.files.FileEntity;

public class UserMapper {

    public static UserEntity toEntity(UserEntityDto userDto, UserEntity user) {
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());

        FileEntity file = null;

        if (userDto.getFileEntityDto() != null){
            file = new FileEntity();
            file.setFileName(userDto.getFileEntityDto().getFileName());
            file.setContentType(userDto.getFileEntityDto().getContentType());
            file.setContent(userDto.getFileEntityDto().getContent());
        }


        if (user.getDetails() == null) {
            UserDetailsEntity details = new UserDetailsEntity();
            details.setDateOfBirth(userDto.getDateOfBirth());
            details.setFirstName(userDto.getFirstName());
            details.setLastName(userDto.getLastName());
            details.setPesel(userDto.getPesel());
            details.setProfileFile(file);
            details.setOwner(user);
            user.setDetails(details);

        } else {
            user.getDetails().setDateOfBirth(userDto.getDateOfBirth());
            user.getDetails().setFirstName(userDto.getFirstName());
            user.getDetails().setLastName(userDto.getLastName());
            user.getDetails().setPesel(userDto.getPesel());
            user.getDetails().setProfileFile(file);
        }
        return user;
    }

    public static UserEntityDto toDto(UserEntity user) {
        UserEntityDto userDto = new UserEntityDto();
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setEnabled(user.getEnabled());
        userDto.setId(user.getId());
        if (user.getDetails() != null) {
            userDto.setFirstName(user.getDetails().getFirstName());
            userDto.setLastName(user.getDetails().getLastName());
            userDto.setDateOfBirth(user.getDetails().getDateOfBirth());
            userDto.setPesel(user.getDetails().getPesel());
            if (user.getDetails().getProfileFileId() != null) {
                userDto.setProfileFileId(user.getDetails().getProfileFileId());

                FileEntityDto fileDto = new FileEntityDto();
                FileEntity file = user.getDetails().getProfileFile();
                fileDto.setContentType(file.getContentType());
                fileDto.setFileName(file.getFileName());
                fileDto.setContent(file.getContent());
                userDto.setFileEntityDto(fileDto);
            }

        }
        return userDto;
    }
}
