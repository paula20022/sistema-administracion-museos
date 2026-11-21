package org.acme.repository;

import org.acme.model.TourRoom;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TourRoomRepository implements PanacheRepositoryBase<TourRoom, Integer> {
}