package com.example.paginationandprojection.model.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString(exclude = "password")

/**
 * na encję nałozone sa indeksy ułatwiające przeszukiwanie tabeli po nazwie użytkownika i adresie email
 */
@Table(name = "example_users", indexes = {@Index(columnList = "username"), @Index(columnList = "password")})
public class UserEntity  extends ParentEntity{

@Column(unique = true, nullable = false)
private String username;
@Column(nullable = false)
private String password;
private String email;
private Boolean enabled = Boolean.TRUE; //czy użytkownik jest aktywny, domyślnie jest zdezaktywowany

    /**
     * relacja oneTone z obiektem UserDetailsEntity
     */
    @OneToOne(mappedBy = "owner", fetch = FetchType.LAZY)
    private UserDetailsEntity details;

    /**
     * encja posiada kolekcję obiektów UserRole, bez ustawiania jakiejkolwiek relacji (np OneToMany), bo klasa UserRole nie jest encją - nie ma wlasnej tabeli
     * (nie ma @Entity, więc takiej relacji być nie może) jest klasą osadzalną - @Embeddable, więc korzystamy
     * z @ElementCollection (przeznaczoną dla kolekcji typów prostych i łańcuchów String oraz obiektów osadzonych embeddable).
     * Kolekcja taka należy do obiektu ją zawierającego i zostaje usunięta razem z usunięciem obiektu UserEntity.
     * 	Na polu dodatkowo używamy adnotacji
     * 	@CollectionTable(name = "example_users_roles", joinColumns = @JoinColumn(name = "username", referencedColumnName = "username").
     * 	Dla zbioru ról powstanie dedykowana tabela. Zostanie w niej utworzona kolumna wynikająca z klasy UserRole ale również
     * 	kolumna klucza głównego (domyślnie, gdy nie uzyjemy opcji referencedColumnName, kolumną tą będzie pole id).
     * 	My jednak relację między tabelami opieramy nie na polu id, a na polu username. Dzięki temu tabela
     * 	example_users_roles będzie składała się z pary kolumn username i role_name i przykładowych wartości:
     * 						akowalski, ROLE_USER
     * 						akowalski, ROLE_MANAGER
     * 						admin, ROLE_ADMIN
     * 	DO TEJ TABELI BĘDZIEMY SIĘ ODWOŁYWAĆ W KLASIE SECURITY W KONFIGURACJI AUTENTYKACJI UZYTKOWNIKÓW Z BAZY DANYCH
     */

@ElementCollection

/**
 *  Adnotacja określa tabelę która ma by c użyta do zmapowania kolekcji typów prostych
 *  lub zagnieżdżonych. Stosowany do pól typu kolekcji typów prostych lub zagnieżdżonych.
 */
@CollectionTable(name = "example_users_roles", joinColumns = @JoinColumn(name = "username", referencedColumnName = "username"))
private Set<UserRole> roles = new HashSet<>();
}
