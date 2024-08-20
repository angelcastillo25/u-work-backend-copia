package com.example.proyecto_analisis.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.proyecto_analisis.models.Lugar;
import com.example.proyecto_analisis.services.LugarService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api")
public class LugarController {
    
    @Autowired
    private LugarService lugarImpl;

    //Departamentos
    @GetMapping("/lugares/dep")
    public List<Lugar> mostrarDepartamentos(){
        try {
            return lugarImpl.mostrarLugares(2, 1);
        } catch (Exception e) {
            return null;
        }
    }
    
    //Municipios
    @GetMapping("/lugares/mun/{id_lugar_departamento}")
    public List<Lugar> mostrarMunicipios(@PathVariable int id_lugar_departamento){
        try {
            return lugarImpl.mostrarLugares(3, id_lugar_departamento);
        } catch (Exception e) {
            return null;
        }

    }
    
}
