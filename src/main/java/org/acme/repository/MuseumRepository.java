package org.acme.repository;

import org.acme.model.Museum;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MuseumRepository implements PanacheRepositoryBase<Museum, Integer> {
}