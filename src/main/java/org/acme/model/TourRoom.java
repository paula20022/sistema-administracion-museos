package org.acme.model;

import jakarta.persistence.*;

@Entity
@Table(name = "recorrido_sala")
public class TourRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "recorrido_id", nullable = false)
    private Tour recorrido;

    @ManyToOne
    @JoinColumn(name = "sala_exposicion_id", nullable = false)
    private ExhibitionRoom salaExposicion;

    @Column(name = "numero_secuencia", nullable = false)
    private Integer numeroSecuencia;

    public TourRoom() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tour getRecorrido() {
        return recorrido;
    }

    public void setRecorrido(Tour recorrido) {
        this.recorrido = recorrido;
    }

    public ExhibitionRoom getSalaExposicion() {
        return salaExposicion;
    }

    public void setSalaExposicion(ExhibitionRoom salaExposicion) {
        this.salaExposicion = salaExposicion;
    }

    public Integer getNumeroSecuencia() {
        return numeroSecuencia;
    }

    public void setNumeroSecuencia(Integer numeroSecuencia) {
        this.numeroSecuencia = numeroSecuencia;
    }
}