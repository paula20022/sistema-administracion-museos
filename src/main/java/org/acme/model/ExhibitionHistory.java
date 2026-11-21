package org.acme.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "historial_exhibicion")
public class ExhibitionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "obra_id", nullable = false)
    private Artifact obra;

    @ManyToOne
    @JoinColumn(name = "sala_exposicion_id", nullable = false)
    private ExhibitionRoom salaExposicion;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;

    public ExhibitionHistory() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Artifact getObra() {
        return obra;
    }

    public void setObra(Artifact obra) {
        this.obra = obra;
    }

    public ExhibitionRoom getSalaExposicion() {
        return salaExposicion;
    }

    public void setSalaExposicion(ExhibitionRoom salaExposicion) {
        this.salaExposicion = salaExposicion;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }
}