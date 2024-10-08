package com.example.proyecto_analisis.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.proyecto_analisis.models.Empresa;

import jakarta.transaction.Transactional;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer>{
    @Query("SELECT e FROM Empresa e WHERE correo = ?1 AND contrasena = ?2")
    public Empresa autenticarDirectorEmpresa(String correo, String contrasena);

    @Query(value = "SELECT concat(A.PRIMER_NOMBRE, ' ' , A.PRIMER_APELLIDO) AS NOMBRE, A.TELEFONO\n" + //
                "FROM personas AS A\n" + //
                "INNER JOIN empresa AS E\n" + //
                "ON (E.ID_DIRECTOR = A.ID_PERSONA)\n" + //
                "WHERE ID_EMPRESA = :idEmpresa", nativeQuery = true)
    public List<Object[]> obtenerInfoDirector(@Param("idEmpresa") int idEmpresa);

    @Query(value = "SELECT ID_OFERTA, TITULO, LEFT(DESCRIPCION, 30) AS DESCRIPCION, \n" + //
                "\tDATE_FORMAT(FECHA_PUBLICACION, '%M %d, %Y') AS FECHA_PUBLICACION \n" + //
                "FROM ofertas\n" + //
                "WHERE ID_EMPRESA = :idEmpresa AND ESTADO_OFERTA = 1;", nativeQuery = true)
    public List<Object[]> obtenerInfoOfertas(@Param("idEmpresa") int idEmpresa);

    @Query(value = "SELECT  A.ID_EMPRESA, \n" + //
        "        A.URL_LOGO, \n" + //
        "        A.NOMBRE_EMPRESA, \n" + //
        "        B.INDUSTRIA, \n" + //
        "        L.NOMBRE_LUGAR,\n" + //
        "        A.DESCRIPCION,\n" + //
        "        A.TELEFONO,\n" + //
        "        A.CORREO,\n" + //
        "        A.SITIO_WEB\n" + //
        "FROM empresa AS A\n" + //
        "INNER JOIN industrias AS B\n" + //
        "ON (B.ID_INDUSTRIA = A.ID_INDUSTRIA)\n" + //
        "INNER JOIN lugares AS L\n" + //
        "ON (L.ID_LUGAR = A.ID_DIRECCION)\n" + //
        "WHERE A.ID_EMPRESA = :idEmpresa", nativeQuery = true)
    public  Map<String, Object> obtenerInfoEmpresa(@Param("idEmpresa") int idEmpresa);

    @Query(value = "SELECT COUNT(*) FROM ofertas \r\n" + //
                "INNER JOIN empresa ON ofertas.ID_EMPRESA = empresa.ID_EMPRESA\r\n" + //
                "WHERE empresa.ID_EMPRESA = :idEmpresaP;", nativeQuery = true)
    public int obtenerOfertasPublicadas(@Param("idEmpresaP") int idEmpresaP);

    // Obtener informacion de empresa para editar
    @Query(value = "SELECT e.nombre_empresa,"+
                    "        e.CORREO,"+
                    "        e.CONTRASENA,"+
                    "        e.TELEFONO as telefonoEmpresa,"+
                    "        e.ID_DIRECCION,"+
                    "        e.ID_INDUSTRIA,"+
                    "        e.SITIO_WEB,"+
                    "        e.descripcion,"+
                    "        p.PRIMER_NOMBRE,"+
                    "        p.SEGUNDO_NOMBRE,"+
                    "        p.PRIMER_APELLIDO,"+
                    "        p.SEGUNDO_APELLIDO,"+
                    "        p.TELEFONO as telefonoDirector,"+
                    "        p.IDENTIFICACION,"+
                    "        p.GENERO_ID_GENERO "+
                    "from empresa e "+
                    "INNER JOIN personas p on e.ID_DIRECTOR = p.ID_PERSONA "+
                    "WHERE e.ID_EMPRESA = :idEmpresaP;", nativeQuery = true)
    public Object[] obtenerEmpresaEditar(@Param("idEmpresaP") int idEmpresaP);

    @Modifying
    @Transactional
    @Query(value = "UPDATE `empresa` SET "+
                    "    `NOMBRE_EMPRESA`=:nombreEmpresaP,"+
                    "    `CORREO`=:correoP,"+
                    "    `CONTRASENA`=:contrasenaP,"+
                    "    `TELEFONO`=:telefonoP,"+
                    "    `ID_DIRECCION`=:idDirenccionP,"+
                    "    `ID_INDUSTRIA`=:idIndustriaP,"+
                    "    `SITIO_WEB`=:sitioWebP,"+
                    "    `DESCRIPCION` =:descripcionP "+
                    "WHERE id_empresa = :idEmpresaP;", nativeQuery = true)
    public void actualizarEmpresa(
        @Param("nombreEmpresaP") String nombreEmpresaP,
        @Param("correoP") String correoP,
        @Param("contrasenaP") String contrasenaP,
        @Param("telefonoP") String telefonoP,
        @Param("idDirenccionP") int idDirenccionP,
        @Param("idIndustriaP") int idIndustriaP,
        @Param("sitioWebP") String sitioWebP,
        @Param("descripcionP") String descripcionP,
        @Param("idEmpresaP") int idEmpresaP
    );

    @Query(value = "SELECT ID_DIRECTOR FROM empresa where id_empresa = :idEmpresaP;", nativeQuery = true)
    public int obtenerIdDirectoEmpresa(@Param("idEmpresaP") int idEmpresaP);

    @Modifying
    @Transactional
    @Query(value = "UPDATE personas SET PRIMER_NOMBRE=:primerNombreP,"+
                    "                    SEGUNDO_NOMBRE=:segundoNombreP,"+
                    "                    PRIMER_APELLIDO=:primerApellidoP,"+
                    "                    SEGUNDO_APELLIDO=:segundoApellidoP,"+
                    "                    TELEFONO=:telefonoP,"+
                    "                    IDENTIFICACION=:identificacionP,"+
                    "                    GENERO_ID_GENERO=:idGeneroP "+
                    "WHERE ID_PERSONA = :idPersonaP;", nativeQuery = true)
    public void actualizarDirector(
        @Param("primerNombreP") String primerNombreP,
        @Param("segundoNombreP") String segundoNombreP,
        @Param("primerApellidoP") String primerApellidoP,
        @Param("segundoApellidoP") String segundoApellidoP,
        @Param("telefonoP") int telefonoP,
        @Param("identificacionP") String identificacionP,
        @Param("idGeneroP") int idGeneroP,
        @Param("idPersonaP") int idPersonaP
    );
}
