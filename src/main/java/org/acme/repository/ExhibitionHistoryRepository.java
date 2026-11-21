package org.acme.repository;

import org.acme.model.ExhibitionHistory;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ExhibitionHistoryRepository implements PanacheRepositoryBase<ExhibitionHistory, Integer> {
}