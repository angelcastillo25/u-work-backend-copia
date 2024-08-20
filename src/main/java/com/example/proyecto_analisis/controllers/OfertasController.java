package com.example.proyecto_analisis.controllers;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.proyecto_analisis.models.Oferta;
import com.example.proyecto_analisis.models.dto.NvaOfertaDTO;
import com.example.proyecto_analisis.models.dto.OfertaDTO;
import com.example.proyecto_analisis.services.OfertaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/ofertas")
public class OfertasController {
    
    @Autowired
    private OfertaService ofertaService;

    @GetMapping("/detalle")
    public ResponseEntity<OfertaDTO> obtenerDetalleOferta(@RequestParam("idOferta") int idOferta, @RequestParam("idSolicitante") int idSolicitante) {
        try {
            return ResponseEntity.ok(ofertaService.obtenerDetalleOferta(idOferta, idSolicitante));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/ingresar/{idEmpresaP}")
    public ResponseEntity<Object> ingresarOfertaDetalle(@PathVariable int idEmpresaP, @RequestBody NvaOfertaDTO nvaOfertaDTO){

        try {
            // Agg oferta
            Oferta nvaOferta = new Oferta();

            nvaOferta.setIdEmpresa(idEmpresaP);
            nvaOferta.setIdTipoEmpleo(nvaOfertaDTO.getTipoEmpleo());
            nvaOferta.setIdNivelAcademico(nvaOfertaDTO.getNivelAcademico());
            nvaOferta.setIdLugar(nvaOfertaDTO.getLugar());
            nvaOferta.setIdModalidad(nvaOfertaDTO.getModalidad());
            nvaOferta.setIdContrato(nvaOfertaDTO.getTipoContrato());
            nvaOferta.setTitulo(nvaOfertaDTO.getTitulo());
            nvaOferta.setDescripcion(nvaOfertaDTO.getDescripcion());
            nvaOferta.setFechaExpiracion(nvaOfertaDTO.getFechaExpiracion());
            nvaOferta.setPlazasDisponibles(nvaOfertaDTO.getPlazasDisponibles());
            nvaOferta.setFechaPublicacion(new Date(System.currentTimeMillis()));
            nvaOferta.setEstadoOferta(true);

            int idOferta = ofertaService.ingresarNvaOferta(nvaOferta);
            
            // Agg oferta-puestos
            List<Integer> puestos = nvaOfertaDTO.getPuestos();

            for (Integer idPuesto : puestos) {
                ofertaService.ingresarPuestoOferta(idPuesto, idOferta);
            }

            // Agg ofera-idiomas
            List<Map<String,Integer>> idiomasInfo = nvaOfertaDTO.getIdiomas();

            for (Map<String,Integer> map : idiomasInfo) {
                Integer idIdioma = map.get("idIdioma");
                Integer idNivelIdioma = map.get("idNivelIdioma");

                ofertaService.ingresarIdiomaOferta(idNivelIdioma, idOferta, idIdioma);
            }

            // Agg requisitos academicos
            List<Integer> reqAca = nvaOfertaDTO.getRequisitosAcademicos();
            for (Integer idReqAca : reqAca) {
                ofertaService.ingresarReqAcadeOferta(idOferta, idReqAca);
            }

            // Agg experiencia laboral
            List<Integer> reqLab = nvaOfertaDTO.getExperienciaLaboral();
            for (Integer idReqLab : reqLab) {
                ofertaService.ingresarReqLaboOferta(idReqLab,idOferta);
            }

            return ResponseEntity.ok("Oferta ingresada");
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al ingresar: " + e.getMessage());
        }
    }
    @GetMapping("/detalleEmpresa/{idOferta}")
    public ResponseEntity<OfertaDTO> obtenerDetalleOfertaEmpresa(@PathVariable int idOferta) {
        try {
            return ResponseEntity.ok(ofertaService.obtenerDetalleOfertaEmpresa(idOferta));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    
    //Obtener ofertas por empresa id
    @GetMapping("/mostrar/{idEmpresa}")
    public ResponseEntity<Object> obtenrOfertasEmpresaId(@PathVariable int idEmpresa){
        try {
            
            List<Map<String,Object>> ofertasEmpresa = ofertaService.obtenerOfertasPorEmpresaId(idEmpresa);

            return ResponseEntity.ok(ofertasEmpresa);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: "+e.getMessage());
        }
    }

    @GetMapping("/obtener/{idOferta}")
    public ResponseEntity<Object> obtenerOfertaEditable(@PathVariable int idOferta){
        try {
            
            NvaOfertaDTO nvaOfertaDTO = ofertaService.obtenerOfertaEditable(idOferta);

        if (nvaOfertaDTO != null) {
            return ResponseEntity.ok(nvaOfertaDTO);
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Oferta no encontrada");
        }

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: "+e.getMessage());
        }
    }
    
    @PutMapping("/editar/{idOfertaP}")
    public ResponseEntity<Object> editarOferta(@PathVariable int idOfertaP, @RequestBody NvaOfertaDTO nvaOfertaDTO){
        try {
            ofertaService.editarOferta(idOfertaP, nvaOfertaDTO);
            return ResponseEntity.ok("Actualizacion exitosa :)");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: "+e.getMessage());
        }
    }

    //Obtener aplicantes oferta
    @GetMapping("/aplicantes/{idOfertaP}")
    public ResponseEntity<Object> obtenerDetalleAplicanteOferta(@PathVariable int idOfertaP){
        try {
            Map<String,Object> aplicantes = ofertaService.obtenerDetalleAplicanteOferta(idOfertaP);

            return ResponseEntity.ok(aplicantes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al obtener aplicantes: "+e.getMessage());
        }
    }

    @GetMapping("/tipo-empleo/{idTipoEmpleo}")
    public ResponseEntity<Object> obtenerOfertasPorTipoEmpleo(@PathVariable int idTipoEmpleo){
        try {
            List<Map<String,Object>> ofertas = ofertaService.obtenerOfertasPorTipoEmpleo(idTipoEmpleo);

            return ResponseEntity.ok(ofertas);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al obtener las ofertas: "+e.getMessage());
        }
    }

}
