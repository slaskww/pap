package com.example.paginationandprojection.service;

import com.example.paginationandprojection.dto.UserEntityDto;
import com.example.paginationandprojection.dto.mappers.UserMapper;
import com.example.paginationandprojection.model.entity.UserDetailsEntity;
import com.example.paginationandprojection.model.entity.UserEntity;
import com.example.paginationandprojection.model.entity.UserRole;
import com.example.paginationandprojection.model.repository.UserDetailsEntityRepository;
import com.example.paginationandprojection.model.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {


    private final UserRepository userRepository;
    private final UserDetailsEntityRepository userDetailsRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserDetailsEntityRepository userDetailsRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(UserEntityDto userDto){

        UserEntity user = UserMapper.toEntity(userDto, new UserEntity());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.getRoles().add(new UserRole("ROLE_USER"));
        userRepository.save(user);
    }

    public int countByUsername(String username){
        return userRepository.countByUsername(username);
    }

    public int countByEmail(String email){
        return userRepository.countByEmail(email);
    }

    public UserEntityDto getUserWithDetails(String username){
       return UserMapper.toDto(userRepository.findByUsername(username).get());
    }

    public void updateUserData(UserEntityDto userDto){

        UserEntity user = userRepository.getByUsername(userDto.getUsername());
        UserDetailsEntity userDetails = user.getDetails();
        user = UserMapper.toEntity(userDto, user);

        if (userDetails == null){
            userRepository.save(user);
        } else {
            userDetailsRepository.save(user.getDetails());
        }
    }

    public UserDetailsEntity getDetails(String username){
       return userDetailsRepository.getWithProfileFileByOwnerUsername(username);
    }
}
