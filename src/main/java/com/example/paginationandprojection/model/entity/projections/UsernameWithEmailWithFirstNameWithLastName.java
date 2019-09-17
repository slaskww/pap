package com.example.paginationandprojection.model.entity.projections;

public interface UsernameWithEmailWithFirstNameWithLastName extends Projection {

    String getUsername();

    String getEmail();

    UserDetails getDetails();

    interface UserDetails {

        String getFirstName();

        String getLastName();
    }
}
