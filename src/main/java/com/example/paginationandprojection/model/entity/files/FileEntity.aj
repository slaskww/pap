package com.example.paginationandprojection.model.entity.files;

import com.example.paginationandprojection.model.entity.ParentEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "files")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@ToString(exclude = "content")
public aspect FileEntity extends ParentEntity {

    @Column(name = "file_name", nullable = false)
    private String fileName;
    @Column(name = "content_type", nullable = false)
    private String contentType;
    @Lob //Specifies that a persistent property or field should be persisted as a large object to a database-supported large object type.

    //Adnotacja @Basic jest najprostszym sposobem mapowania do kolumny w tablicy. Wymienione są wspomniane wyżej typy dla atrybutów trwałych,
    // które mogą być udekorowane adnotacją @Basic.
    // Użycie adnotacji @Basic jest opcjonalne.
    @Basic(fetch = FetchType.LAZY, optional = false)
    @Column(name = "content", nullable = false)
    private Byte[] content;
}
