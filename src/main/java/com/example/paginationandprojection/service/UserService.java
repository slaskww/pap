package com.example.paginationandprojection.service;

import com.example.paginationandprojection.dto.UserEntityDto;
import com.example.paginationandprojection.dto.mappers.UserMapper;
import com.example.paginationandprojection.model.entity.UserEntity;
import com.example.paginationandprojection.model.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(UserEntityDto userDto){

        UserEntity user = UserMapper.toEntity(userDto);
        repository.save(user);
    }
}
