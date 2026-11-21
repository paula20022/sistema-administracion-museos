package org.acme.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sala_exposicion")
public class ExhibitionRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "museo_id", nullable = false)
    private Museum museo;

    @Column(name = "nombre", nullable = false, length = 120)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "capacidad")
    private Integer capacidad;

    public ExhibitionRoom() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Museum getMuseo() {
        return museo;
    }

    public void setMuseo(Museum museo) {
        this.museo = museo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        if (capacidad != null && capacidad < 0) {
            throw new IllegalArgumentException("La capacidad no puede ser negativa");
        }
        this.capacidad = capacidad;
    }
}