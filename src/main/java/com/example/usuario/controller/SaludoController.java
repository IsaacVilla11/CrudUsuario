package com.example.usuario.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/saludos")
public class SaludoController {

    @GetMapping("/hola")
    public String holaMundo(){
        return "Hola Mundo";
    }
}
