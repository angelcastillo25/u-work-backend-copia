package com.example.proyecto_analisis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.proyecto_analisis.models.dto.LoginDTO;
import com.example.proyecto_analisis.services.EmpresaService;
import com.example.proyecto_analisis.services.PersonaService;
import com.example.proyecto_analisis.services.SolicitanteService;

@RestController
@RequestMapping("/api")
public class LoginDTOController {
    
    @Autowired
    private SolicitanteService solicitanteImpl;

    @Autowired
    private EmpresaService empresaImpl;

    @Autowired 
    private PersonaService personaImpl;

    @PostMapping("/login/solicitante")
    public ResponseEntity<Integer> autenticarSolicitante(@RequestBody LoginDTO loginDTO){
        try {
            int idPersonaSoli = solicitanteImpl.autenticarSolicitante(loginDTO.getCorreo(), loginDTO.getContrasena());
            return ResponseEntity.ok(idPersonaSoli);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(0);
        }
    }

    @PostMapping("/login/empresa")
    public ResponseEntity<Integer> autenticarDirectorEmpresa(@RequestBody LoginDTO loginDTO){
        try {
            int idDirectorEmpresa = empresaImpl.autenticarDirectorEmpresa(loginDTO.getCorreo(), loginDTO.getContrasena());
            return ResponseEntity.ok(idDirectorEmpresa);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(0);
        }

    }

    @PostMapping("/login/admin")
    public ResponseEntity<Integer> autenticarAdministrador(@RequestBody LoginDTO loginDTO){
        try {
            int idAdmin = personaImpl.autenticarAdmin(loginDTO.getCorreo(), loginDTO.getContrasena());
            return ResponseEntity.ok(idAdmin);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(0);
        }
        
    }

}
