package com.example.paginationandprojection.model.repository;

import com.example.paginationandprojection.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Pobieranie użytkownika po nazwie.
     *
     * Wymagane aby użytkownik istniał. Jeżeli użytkownik może nie istnieć
     * to metoda zgłosi wyjątek. Lepiej wtedy użyć: findByUsername(String)
     *
     * @param username nazwa użytkownika
     * @return encja użytkownika
     */
    UserEntity getByUsername(String username);

    /**
     * Wyszukiwanie użytkownika po nazwie.
     *
     * Nie wymagane aby użytkownik istniał.
     *
     * @param username nazwa użytkownika
     * @return encja użytkownika
     */
    Optional<UserEntity> findByUsername(String username);

    /**
     * Pobieranie unikalnych użytkowników na podstawie zbioru ról
     *
     * @param roles zbiór ról
     * @return lista użytkowników należących do przynajmniej jednej z ról
     */
    List<UserEntity> findDistinctAllByRoles_RoleNameIn(Set<String> roles);

    /**
     * Pobieranie użytkowników aktywnych
     *
     * @return lista aktywnych użytkowników
     */
    List<UserEntity> findAllByEnabledIsTrue();

    /**
     * Pobieranie użytkowników nieaktywnych
     *
     * @return lista nieaktywnych użytkowników
     */
    List<UserEntity> findAllByEnabledIsFalse();

    /**
     * Pobieranie listy 100 ostatnio utworzonych użytkowników
     *
     * @return lista użytkowników
     */
    List<UserEntity> findFirst100ByOrderByCreateOnDesc();

    /**
     * Pobieranie listy 100 ostatnio utworzonych użytkowników (zapytanie natywne)
     *
     * @return lista użytkowników
     */
    @Query(nativeQuery = true,
            value = "SELECT u.* FROM example_users u ORDER BY u.create_on DESC LIMIT 100"
    )
    List<UserEntity> findLast100Users();

}
