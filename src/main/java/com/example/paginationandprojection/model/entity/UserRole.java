package com.example.paginationandprojection.model.entity;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data //posiada w sobie zestaw adnotacji lombok m.in @Getter, @Setter, @ToString...
@NoArgsConstructor
@AllArgsConstructor
public class UserRole implements Serializable {

    @Column(name = "role_name")
    private String roleName;
}
