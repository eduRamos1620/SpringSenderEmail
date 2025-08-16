package com.ramos.Correo.controller;

import com.ramos.Correo.domain.EmailDTO;
import com.ramos.Correo.domain.EmailFileDTO;
import com.ramos.Correo.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1")
public class MailController {

    @Autowired
    private IEmailService emailService;

    @PostMapping("/sendMessage")
    public ResponseEntity<?> receiveRequestEmail(@RequestBody EmailDTO emailDTO){

        System.out.println("Mensaje recibido " + emailDTO);

        emailService.sendEmail(
                emailDTO.getToUser(),
                emailDTO.getSubject(),
                emailDTO.getMessage()
        );

        Map<String ,String> response = new HashMap<>();
        response.put("estado", "Enviado");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/sendMessageFile")
    public ResponseEntity<?> receiveRequestEmailWithFile(@ModelAttribute EmailFileDTO emailFileDTO){

        try{
            String fileName = emailFileDTO.getFile().getName();
            Path path = Paths.get("src/main/resources/files", fileName);

            Files.createDirectories(path.getParent());
            Files.copy(emailFileDTO.getFile().getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            File file = path.toFile();

            emailService.sendEmailWithFile(
                    emailFileDTO.getToUser(),
                    emailFileDTO.getSubject(),
                    emailFileDTO.getMessage(),
                    file
            );

            Map<String ,String> response = new HashMap<>();
            response.put("estado", "Enviado");
            response.put("archivo", "fileName");

            return ResponseEntity.ok(response);
        }catch (Exception e){
            throw  new RuntimeException("Error al enviar el email " + e.getMessage());
        }

    }
}
