package com.example.paginationandprojection.entity;


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

@Table(name = "example_users", indexes = {@Index(columnList = "username"), @Index(columnList = "password")})
public class UserEntity  extends ParentEntity{

    @Column(unique = true, nullable = false)
private String username;
    @Column(nullable = false)
private String password;
private String email;
private Boolean enabled = Boolean.FALSE;

@OneToOne(mappedBy = "owner", fetch = FetchType.LAZY)
    private UserDetailsEntity details;

@ElementCollection
@CollectionTable(name = "example_users_roles", joinColumns = @JoinColumn(name = "username", referencedColumnName = "username"))
private Set<UserRole> roles = new HashSet<>();
}
