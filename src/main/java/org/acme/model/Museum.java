package org.acme.model;

import jakarta.persistence.*;

@Entity
@Table(name = "museo")
public class Museum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "latitud")
    private Float latitud;

    @Column(name = "longitud")
    private Float longitud;

    @Column(name = "url_pagina_principal")
    private String urlPaginaPrincipal;

    public Museum() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del museo es obligatorio");
        }
        this.nombre = nombre;
    }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Float getLatitud() { return latitud; }
    public void setLatitud(Float latitud) { this.latitud = latitud; }

    public Float getLongitud() { return longitud; }
    public void setLongitud(Float longitud) { this.longitud = longitud; }

    public String getUrlPaginaPrincipal() { return urlPaginaPrincipal; }
    public void setUrlPaginaPrincipal(String urlPaginaPrincipal) { this.urlPaginaPrincipal = urlPaginaPrincipal; }
}