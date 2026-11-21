package org.acme.controller;

import org.acme.model.*;
import org.acme.repository.*;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;

@Path("/api/obras")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ArtifactController {

    @Inject ArtifactRepository artifactRepository;
    @Inject ArtistRepository artistRepository;
    @Inject CategoryRepository categoryRepository;
    @Inject ExhibitionRoomRepository roomRepository;
    @Inject ExhibitionHistoryRepository historyRepository;

    @GET
    public List<Artifact> listarTodas() {
        return artifactRepository.listAll();
    }

    @POST
    @Transactional
    public Response registrarObra(Artifact obra) {
        // 1. Asociar entidades reales desde la BD usando sus IDs
        if (obra.getArtista() != null && obra.getArtista().getId() != null) {
            obra.setArtista(artistRepository.findById(obra.getArtista().getId()));
        }
        if (obra.getCategoria() != null && obra.getCategoria().getId() != null) {
            obra.setCategoria(categoryRepository.findById(obra.getCategoria().getId()));
        }
        if (obra.getSalaActual() != null && obra.getSalaActual().getId() != null) {
            obra.setSalaActual(roomRepository.findById(obra.getSalaActual().getId()));
        }

        if (obra.getFechaAdquisicion() == null) {
            obra.setFechaAdquisicion(LocalDateTime.now());
        }


        artifactRepository.persist(obra);

        
        if (obra.getSalaActual() != null) {
            ExhibitionHistory historial = new ExhibitionHistory();
            historial.setObra(obra);
            historial.setSalaExposicion(obra.getSalaActual());
            historial.setFechaInicio(LocalDateTime.now());
            historyRepository.persist(historial);
        }

        return Response.status(Response.Status.CREATED).entity(obra).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response eliminar(@PathParam("id") Integer id) {
        boolean borrado = artifactRepository.deleteById(id);
        return borrado ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}