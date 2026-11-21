package org.acme.controller;

import org.acme.model.*;
import org.acme.repository.*;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/catalogo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CatalogoController {

    @Inject ArtistRepository artistRepo;
    @Inject CategoryRepository categoryRepo;
    @Inject ExhibitionRoomRepository roomRepo;
    @Inject MuseumRepository museumRepo;
    @Inject TourRepository tourRepo;
    @Inject ExhibitionHistoryRepository historyRepo;


    @GET @Path("/artistas")
    public List<Artist> getArtistas() { return artistRepo.listAll(); }

    @POST @Path("/artistas") @Transactional
    public Artist crearArtista(Artist a) { artistRepo.persist(a); return a; }


    @GET @Path("/categorias")
    public List<Category> getCategorias() { return categoryRepo.listAll(); }

    @POST @Path("/categorias") @Transactional
    public Category crearCategoria(Category c) { categoryRepo.persist(c); return c; }


    @GET @Path("/salas")
    public List<ExhibitionRoom> getSalas() { return roomRepo.listAll(); }

    @POST @Path("/salas") @Transactional
    public ExhibitionRoom crearSala(ExhibitionRoom s) {
        if (s.getMuseo() != null && s.getMuseo().getId() != null) {
            s.setMuseo(museumRepo.findById(s.getMuseo().getId()));
        }
        roomRepo.persist(s);
        return s;
    }


    @GET @Path("/recorridos")
    public List<Tour> getRecorridos() { return tourRepo.listAll(); }

    @POST @Path("/recorridos") @Transactional
    public Tour crearRecorrido(Tour t) { tourRepo.persist(t); return t; }

    @GET @Path("/historial")
    public List<ExhibitionHistory> getHistorial() { return historyRepo.listAll(); }
}