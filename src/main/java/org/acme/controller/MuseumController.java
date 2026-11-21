package org.acme.controller;

import org.acme.model.Museum;
import org.acme.repository.MuseumRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/museos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MuseumController {

    @Inject
    MuseumRepository museumRepository;

    @GET
    public List<Museum> listar() {
        return museumRepository.listAll();
    }

    @POST
    @Transactional
    public Response crear(Museum museum) {
        museumRepository.persist(museum);
        return Response.status(Response.Status.CREATED).entity(museum).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response eliminar(@PathParam("id") Integer id) {
        boolean eliminado = museumRepository.deleteById(id);
        return eliminado ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}