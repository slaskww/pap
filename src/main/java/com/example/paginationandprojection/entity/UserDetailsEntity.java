package com.example.paginationandprojection.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "example_user_details")
@Getter
@Setter
@ToString(exclude = "owner")
public class UserDetailsEntity extends ParentEntity {

private String firstName;
private String lastName;
private String pesel;
private LocalDate dateOfBirth;

@OneToOne
@JoinColumn(name = "owner_id")
private UserEntity owner;
@Column(name = "owner_id", insertable = false, updatable = false)
private Long ownerId;

}
