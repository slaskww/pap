package com.example.paginationandprojection.model.entity.projections;

import java.util.Set;

public interface UzytkownikZNazwaRoli extends  Projection {

    String getUsername();

    Set<RoleName> getRoles();

    interface RoleName {

        String getRoleName();
    }
}
