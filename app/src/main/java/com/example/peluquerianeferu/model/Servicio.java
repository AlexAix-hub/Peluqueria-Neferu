// Este código está licenciado bajo la Licencia Creative Commons Attribution-ShareAlike 4.0 Internacional.
// Para más información, visita: https://creativecommons.org/licenses/by-sa/4.0/
// Autor: Alejandro Aix Utreras - Año: 2025

package com.example.peluquerianeferu.model;

import java.util.List;
import java.util.Objects;

public class Servicio {

    private int servicioId;
    private String nombre;
    private double precio;
    private int duracion; //minutos
    private List<CitaServicio> citaServicios;

    public Servicio() {
    }

    public Servicio(int servicioId, String nombre, double precio, int duracion) {
        this.servicioId = servicioId;
        this.nombre = nombre;
        this.precio = precio;
        this.duracion = duracion;
    }

    public Servicio(String nombre, double precio, int duracion) {
        this.nombre = nombre;
        this.precio = precio;
        this.duracion = duracion;
    }

    public Servicio(int servicioId, String nombre, double precio, int duracion, List<CitaServicio> citaServicios) {
        this.servicioId = servicioId;
        this.nombre = nombre;
        this.precio = precio;
        this.duracion = duracion;
        this.citaServicios = citaServicios;
    }

    public int getServicioId() {
        return servicioId;
    }

    public void setServicioId(int servicioId) {
        this.servicioId = servicioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public List<CitaServicio> getCitaServicios() {
        return citaServicios;
    }

    public void setCitaServicios(List<CitaServicio> citaServicios) {
        this.citaServicios = citaServicios;
    }

    @Override
    public String toString() {
        return "Servicio{" +
                "servicioId=" + servicioId +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", duracion=" + duracion +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Servicio servicio = (Servicio) o;
        return servicioId == servicio.servicioId && Double.compare(precio, servicio.precio) == 0 && duracion == servicio.duracion && Objects.equals(nombre, servicio.nombre) && Objects.equals(citaServicios, servicio.citaServicios);
    }

    @Override
    public int hashCode() {
        return Objects.hash(servicioId, nombre, precio, duracion, citaServicios);
    }
}
