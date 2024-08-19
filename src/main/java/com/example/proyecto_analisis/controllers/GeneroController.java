package com.example.proyecto_analisis.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.proyecto_analisis.models.Genero;
import com.example.proyecto_analisis.services.impl.GeneroServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api")
public class GeneroController {
    @Autowired
    private GeneroServiceImpl generoImpl;

    @GetMapping("/genero/mostrar")
    public List<Genero> mostrarGeneros() {
        return (List<Genero>) generoImpl.mostrarGeneros();
    }
    
    @PostMapping("/genero/ingresar/{generoP}")
    public ResponseEntity<String> ingresarGenero(@PathVariable String generoP){
        try {
            generoImpl.ingresarGenero(generoP);
            return ResponseEntity.ok("Ingresado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: "+e.getMessage());
        }
    }

    @PutMapping("/genero/eliminar/{idGeneroP}")
    public ResponseEntity<String> eliminarGenero(@PathVariable int idGeneroP){
        try {
            generoImpl.eliminarGeneroPorId(idGeneroP);
            return ResponseEntity.ok("eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: "+e.getMessage());
        }
    }
}
