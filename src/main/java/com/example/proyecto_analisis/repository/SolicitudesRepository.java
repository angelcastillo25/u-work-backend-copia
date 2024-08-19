package com.example.proyecto_analisis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.proyecto_analisis.models.Solicitante;

public interface SolicitudesRepository extends JpaRepository<Solicitante, Integer>{
    

    @Modifying
    @Transactional
    @Query(value = "update solicitudes "+
                    "set id_estado_solicitud = :idEstadoSolicitud "+
                    "where id_solicitud = :idSolicitud", nativeQuery = true)
    public void actualizarEstadoSolicitud(
    @Param(value = "idSolicitud") int idSolicitud, 
    @Param(value = "idEstadoSolicitud") int idEstadoSolicitud);

    @Modifying
    @Transactional
    @Query(value = "CALL APLICAR_OFERTA(:idOferta,:idSolicitante, :idEstadoSolicitud, :emisorSolicitud, :descripcion);", nativeQuery = true)
    public void crearSolicitud(
    @Param(value = "idOferta") int idOferta,
    @Param(value = "idSolicitante") int idSolicitante,
    @Param(value = "idEstadoSolicitud") int idEstadoSolicitud,
    @Param(value = "emisorSolicitud") int emisorSolicitud,
    @Param(value = "descripcion") String descripcion);

    // Enviar notificacion de aceptado/rechazado
    @Modifying
    @Transactional
    @Query(value = "CALL enviarNotificacionEmpresa(:idSolicitanteP,:idOfertaP,:idEstadoSolicitudP,:idSolicitudP)", nativeQuery = true)
    public void enviarNotificacionEmpresa(@Param("idSolicitanteP") int idSolicitanteP,@Param("idOfertaP") int idOfertaP,@Param("idEstadoSolicitudP") int idEstadoSolicitudP,@Param("idSolicitudP") int idSolicitudP);

    //Enviar notificacion solicitante
    @Modifying
    @Transactional
    @Query(value = "CALL enviarNotificacionSolicitante(:idEstadoSolicitudP,:idSolicitudP)",nativeQuery = true)
    public void enviarNotificacionSolicitante(@Param("idEstadoSolicitudP") int idEstadoSolicitudP,@Param("idSolicitudP") int idSolicitudP);

    //obtener id oferta
    @Query(value = "SELECT id_oferta from solicitudes where id_solicitud=:idSolicitudP AND id_solicitante = :idSolicitanteP", nativeQuery = true)
    public int obtenerIdOfertaSolicitud(@Param("idSolicitudP")int idSolicitudP, @Param("idSolicitanteP") int idSolicitanteP);
}
