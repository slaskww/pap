package com.example.paginationandprojection.service;

import com.example.paginationandprojection.dto.UserEntityDto;
import com.example.paginationandprojection.dto.mappers.UserMapper;
import com.example.paginationandprojection.model.entity.UserEntity;
import com.example.paginationandprojection.model.entity.UserRole;
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

        UserEntity user = UserMapper.toEntity(userDto, new UserEntity());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.getRoles().add(new UserRole("ROLE_USER"));
        repository.save(user);
    }

    public int countByUsername(String username){
        return repository.countByUsername(username);
    }

    public int countByEmail(String email){
        return repository.countByEmail(email);
    }

    public UserEntityDto getUserWithDetails(String username){
       return UserMapper.toDto(repository.findByUsername(username).get());
    }

    public void updateUserData(UserEntityDto userDto){

        UserEntity user = repository.getByUsername(userDto.getUsername());
        user = UserMapper.toEntity(userDto, user);
        repository.save(user);
    }
}
