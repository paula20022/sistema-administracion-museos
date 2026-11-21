package org.acme.repository;

import org.acme.model.Artist;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ArtistRepository implements PanacheRepositoryBase<Artist, Integer> {
}