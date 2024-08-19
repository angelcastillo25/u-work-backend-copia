package com.example.proyecto_analisis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.proyecto_analisis.models.Industria;

import jakarta.transaction.Transactional;

public interface IndustriaRepository extends JpaRepository<Industria, Integer> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO industrias (INDUSTRIA) VALUES (:industriaP)",nativeQuery = true )
    public void ingresarIndustria(@Param("industriaP") String industriaP);
}
