package com.example.proyecto_analisis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.proyecto_analisis.models.Genero;

import jakarta.transaction.Transactional;

public interface GeneroRepository extends JpaRepository<Genero, Integer>{
    
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO generos (GENERO) VALUES (:GeneroP)",nativeQuery = true )
    public void ingresarGenero(@Param("GeneroP") String GeneroP);
}
