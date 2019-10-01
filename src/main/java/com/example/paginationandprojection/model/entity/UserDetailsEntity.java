package com.example.paginationandprojection.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "example_user_details")
@Getter
@Setter
@ToString(exclude = "owner") //korzystamy z wartości exclude, by wyłączyć z metody wyświetlanie obiektu złożonego
public class UserDetailsEntity extends ParentEntity {

private String firstName;
private String lastName;
private String pesel;
private LocalDate dateOfBirth;

    /**
     * Możemy mieć pole encji i pole kolumny na tą samą kolumnę. Wtedy pracujemy w kodzie na encji, a pole z kolumną
     * jest polem tylko do odczytu. Można je wykorzystać np. w metodzie toString
     * Przydatne, w sytuacji, gdy fetch type dla pola encji UserEntity ustawiony jest na 'Lazy'.
     */
@OneToOne
@JoinColumn(name = "owner_id")
private UserEntity owner; //pole encji
@Column(name = "owner_id", insertable = false, updatable = false)
private Long ownerId; //pole kolumny

}
