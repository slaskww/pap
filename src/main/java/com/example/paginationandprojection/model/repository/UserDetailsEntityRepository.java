package com.example.paginationandprojection.model.repository;

import com.example.paginationandprojection.model.entity.UserDetailsEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailsEntityRepository  extends JpaRepository<UserDetailsEntity, Long> {

    @EntityGraph(attributePaths = {"profileFile.content"})
    UserDetailsEntity getWithProfileFileByOwnerUsername(String username);

    @EntityGraph(attributePaths = {"profileFile.content"})
    Optional<UserDetailsEntity> findWithProfileFileByOwnerUsername(String username);

}
