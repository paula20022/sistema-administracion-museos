package org.acme.repository;

import org.acme.model.Artifact;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ArtifactRepository implements PanacheRepositoryBase<Artifact, Integer> {


    public List<Artifact> buscarQueRequierenRestauracion() {
        return list("porcentajeConservacion < 60");
    }
}