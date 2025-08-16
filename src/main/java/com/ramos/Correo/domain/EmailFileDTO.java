package com.ramos.Correo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmailFileDTO {

    private String[] toUser;
    private String subject;
    private String message;
    private MultipartFile file;
}
