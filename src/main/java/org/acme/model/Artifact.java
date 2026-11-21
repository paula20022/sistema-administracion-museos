package org.acme.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "obra")
public class Artifact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "url_imagen")
    private String urlImagen;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_adquisicion")
    private LocalDateTime fechaAdquisicion;

    @Column(name = "porcentaje_conservacion")
    private Integer porcentajeConservacion;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Category categoria;

    @ManyToOne
    @JoinColumn(name = "artista_id")
    private Artist artista;

    @ManyToOne
    @JoinColumn(name = "sala_exposicion_id")
    private ExhibitionRoom salaActual;

    public Artifact() {
    }

    public boolean requiereRestauracion() {
        return this.porcentajeConservacion != null && this.porcentajeConservacion < 60;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(LocalDateTime fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public Integer getPorcentajeConservacion() {
        return porcentajeConservacion;
    }

    public void setPorcentajeConservacion(Integer porcentajeConservacion) {
        if (porcentajeConservacion != null && (porcentajeConservacion < 0 || porcentajeConservacion > 100)) {
            throw new IllegalArgumentException("El estado de conservación debe estar entre 0 y 100%");
        }
        this.porcentajeConservacion = porcentajeConservacion;
    }

    public Category getCategoria() {
        return categoria;
    }

    public void setCategoria(Category categoria) {
        this.categoria = categoria;
    }

    public Artist getArtista() {
        return artista;
    }

    public void setArtista(Artist artista) {
        this.artista = artista;
    }

    public ExhibitionRoom getSalaActual() {
        return salaActual;
    }

    public void setSalaActual(ExhibitionRoom salaActual) {
        this.salaActual = salaActual;
    }
}