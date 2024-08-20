package com.example.proyecto_analisis.services;

import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.proyecto_analisis.models.Empresa;
import com.example.proyecto_analisis.models.dto.EmpresaDirectorDTO;
import com.example.proyecto_analisis.models.dto.VistaPerfilEmpresa_DTO;
import com.example.proyecto_analisis.repository.EmpresaRepository;

@Service
public class EmpresaService {
    
    @Autowired
    private EmpresaRepository empresaRepositorio;

    public void ingresarEmpresa(Empresa empresa){
        empresaRepositorio.save(empresa);
    }

    public int autenticarDirectorEmpresa(String correo, String contrasena){
        Empresa empresa = empresaRepositorio.autenticarDirectorEmpresa(correo, contrasena);

        if (empresa == null) {
            return 0;
        } else {
            return empresa.getIdEmpresa();
        }
    }

     // Obtener datos para la vista perfil de la empresa
     public VistaPerfilEmpresa_DTO obtenerVistaPerfilEmpresa(int idEmpresa) {

        VistaPerfilEmpresa_DTO objVista = new VistaPerfilEmpresa_DTO();
    
        int ofertasPublicadas = empresaRepositorio.obtenerOfertasPublicadas(idEmpresa);

        Map<String, Object> objEmpresaInfo = empresaRepositorio.obtenerInfoEmpresa(idEmpresa);
    
        objVista.setIdEmpresa(idEmpresa);
        objVista.setNombreEmpresa(objEmpresaInfo.get("NOMBRE_EMPRESA").toString());
        objVista.setNombreIndustria(objEmpresaInfo.get("INDUSTRIA").toString());
        objVista.setPais(objEmpresaInfo.get("NOMBRE_LUGAR").toString());
        objVista.setDescripcion(objEmpresaInfo.get("DESCRIPCION").toString());
        objVista.setTelefono(objEmpresaInfo.get("TELEFONO").toString());
        objVista.setCorreo(objEmpresaInfo.get("CORREO").toString());
        objVista.setSitioWeb(objEmpresaInfo.get("SITIO_WEB").toString());
        objVista.setOfertasPublicadas(ofertasPublicadas);

        List<Object[]> directorData = empresaRepositorio.obtenerInfoDirector(idEmpresa);
        List<Map<String, Object>> directorList = directorData.stream()
            .map(obj -> {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("nombre", obj[0]);
                map.put("telefono", obj[1]);
                return map;
            }).collect(Collectors.toList());
        objVista.setDirector(directorList);
    
        List<Object[]> ofertasData = empresaRepositorio.obtenerInfoOfertas(idEmpresa);
        List<Map<String, Object>> ofertasList = ofertasData.stream()
            .map(obj -> {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("idOfertas", obj[0]);
                map.put("nombreOferta", obj[1]);
                map.put("descripcion", obj[2]);
                map.put("fechaPublicacion", obj[3]);
                return map;
            }).collect(Collectors.toList());
        objVista.setOfertas(ofertasList);
    
        return objVista;
    }
    
    // Obtener empresa para editar
    public EmpresaDirectorDTO obtenerEmpresaEditar(int idEmpresaP){

        EmpresaDirectorDTO empresaDirectorDTO = new EmpresaDirectorDTO();

        Object[] objEmpresa = empresaRepositorio.obtenerEmpresaEditar(idEmpresaP);
        Object[] data = (Object[]) objEmpresa[0];

        empresaDirectorDTO.setNombreEmpresa(data[0] != null ? data[0].toString() : null);
        empresaDirectorDTO.setCorreoEmpresa(data[1] != null ? data[1].toString() : null);
        empresaDirectorDTO.setContrasena(data[2] != null ? data[2].toString() : null);
        empresaDirectorDTO.setTelefonoEmpresa(data[3] != null ? data[3].toString() : null);
        empresaDirectorDTO.setIdDireccionPais(data[4] != null ? data[4].toString() : null);
        empresaDirectorDTO.setIdIndustria(data[5] != null ? data[5].toString() : null);
        empresaDirectorDTO.setSitioWeb(data[6] != null ? data[6].toString() : null);
        empresaDirectorDTO.setDescripcion(data[7] != null ? data[7].toString() : null);
        empresaDirectorDTO.setPrimerNombre(data[8] != null ? data[8].toString() : null);
        empresaDirectorDTO.setSegundoNombre(data[9] != null ? data[9].toString() : null);
        empresaDirectorDTO.setPrimerApellido(data[10] != null ? data[10].toString() : null);
        empresaDirectorDTO.setSegundoApellido(data[11] != null ? data[11].toString() : null);
        empresaDirectorDTO.setTelefonoPersona(data[12] != null ? data[12].toString() : null);
        empresaDirectorDTO.setIdentificacion(data[13] != null ? data[13].toString() : null);
        empresaDirectorDTO.setIdGenero(data[14] != null ? data[14].toString() : null);

        return empresaDirectorDTO;

    }

    // Actualizar empresa
    public void actualizarEmpresa(int idEmpresaP, EmpresaDirectorDTO empresaDirectorDTO){

        
            String nombreEmpresa = empresaDirectorDTO.getNombreEmpresa();
            String correoEmpresa = empresaDirectorDTO.getCorreoEmpresa();
            String contrasenaEmpresa = empresaDirectorDTO.getContrasena();
            String telefonoEmpresa = empresaDirectorDTO.getTelefonoEmpresa();
            int idDirenccion = Integer.parseInt(empresaDirectorDTO.getIdDireccionPais());
            int idIndustria = Integer.parseInt(empresaDirectorDTO.getIdIndustria());
            String sitioWeb = empresaDirectorDTO.getSitioWeb();
            String descripcion = empresaDirectorDTO.getDescripcion();

            empresaRepositorio.actualizarEmpresa(
                nombreEmpresa,
                correoEmpresa,
                contrasenaEmpresa,
                telefonoEmpresa,
                idDirenccion,
                idIndustria,
                sitioWeb,
                descripcion,
                idEmpresaP
            );


            //director
            int idDirectorP = empresaRepositorio.obtenerIdDirectoEmpresa(idEmpresaP);

            String primerNombre = empresaDirectorDTO.getPrimerNombre();
            String segundoNombre = empresaDirectorDTO.getSegundoNombre();
            String primerApellido = empresaDirectorDTO.getPrimerApellido();
            String segundoApellido = empresaDirectorDTO.getSegundoApellido();
            int telefono = Integer.parseInt(empresaDirectorDTO.getTelefonoPersona());
            String identificacion = empresaDirectorDTO.getIdentificacion();
            int idGenero = Integer.parseInt(empresaDirectorDTO.getIdGenero());

            empresaRepositorio.actualizarDirector(primerNombre, segundoNombre, primerApellido, segundoApellido, telefono, identificacion, idGenero, idDirectorP);
        

    }


}
