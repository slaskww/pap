package com.example.paginationandprojection.startup;

import com.example.paginationandprojection.model.entity.UserEntity;
import com.example.paginationandprojection.model.entity.UserRole;
import com.example.paginationandprojection.model.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Component
public class SetupDataCreator implements ApplicationRunner {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SetupDataCreator(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        System.out.println("--- Tworzenie użytkowników głównych ---");
        createMainUsers();
        System.out.println("--- Tworzenie użytkowników testowych ---");
        createTestUsers();

    }

    private void createUserIfNotExists(String username, String password, String email, String... roles){

        userRepository.findByUsername(username).ifPresentOrElse(
                userEntity -> System.out.println("Użytkownik " + userEntity.getUsername() + " istnieje w bazie"), //ifPresent: jesli istnieje w bazie
                () -> {
                    UserEntity user = new UserEntity();
                    user.setUsername(username);
                    user.setPassword(passwordEncoder.encode(password));
                    user.setEmail(email);
                    user.getRoles().addAll(Arrays.stream(roles).map(s -> "ROLE_".concat(s)).map(s -> new UserRole(s)).collect(Collectors.toSet()));
                    userRepository.save(user);
                }); //orElse: jesli nie, to stwórz set obiektów UserRole na podstawie listy Stringów z nazwami ról
    }

    private void createTestUser(Long id){
        createUserIfNotExists("user" + id, "pass" + id, "user" + id + "@slaskww.pl", "USER");
    }


    private void createTestUsers(){
        LongStream.rangeClosed(1, 100).forEach(number -> createTestUser(number));
    }

    private void createMainUsers(){
        createUserIfNotExists("user", "pass", "user@slaskww.pl", "USER");
        createUserIfNotExists("manager", "pass", "manager@slaskww.pl", "MANAGER");
        createUserIfNotExists("admin", "pass", "admin@slaskww.pl", "ADMIN");
    }
}
