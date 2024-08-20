package com.example.proyecto_analisis.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.proyecto_analisis.models.Empresa;

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
        "\t\tA.URL_LOGO, \n" + //
        "        A.NOMBRE_EMPRESA, \n" + //
        "        B.INDUSTRIA, \n" + //
        "        COUNT(C.ID_EMPRESA) AS OFERTAS_PUBLICADAS,\n" + //
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
        "INNER JOIN ofertas AS C\n" + //
        "ON (A.ID_EMPRESA = C.ID_EMPRESA)\n" + //
        "WHERE C.ID_EMPRESA = :idEmpresa", nativeQuery = true)
    public  Map<String, Object> obtenerInfoEmpresa(@Param("idEmpresa") int idEmpresa);

    // Obtener informacion de empresa para editar
    @Query(value = "SELECT e.nombre_empresa,"+
                    "        e.CORREO,"+
                    "        e.CONTRASENA,"+
                    "        e.TELEFONO as telefonoEmpresa,"+
                    "        e.ID_DIRECCION,"+
                    "        e.ID_INDUSTRIA,"+
                    "        e.SITIO_WEB,"+
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
}
