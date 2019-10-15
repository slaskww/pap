package com.example.paginationandprojection.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileEntityDto {

    private String fileName;
    private String contentType;
    private byte[] content;
}
