package com.example.peluquerianeferu.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Cita {

    private int citaId;
    private Usuario usuario;
    private String fecha;
    private String hora;
    private int duracionTotal;
    private double precioTotal;
    private String observaciones;
    private List<CitaServicio> serviciosCita;

    public Cita() {
    }


    public Cita(int citaId,Usuario usuario, String fecha, String hora, int duracionTotal, double precioTotal, String observaciones) {
        this.citaId = citaId;
        this.usuario = usuario;
        this.fecha = fecha;
        this.hora = hora;
        this.duracionTotal = duracionTotal;
        this.precioTotal = precioTotal;
        this.observaciones = observaciones;
    }


    public Cita(int citaId, Usuario usuario, String fecha, String hora, int duracionTotal, double precioTotal, String observaciones, List<CitaServicio> serviciosCita) {
        this.citaId = citaId;
        this.usuario = usuario;
        this.fecha = fecha;
        this.hora = hora;
        this.duracionTotal = duracionTotal;
        this.precioTotal = precioTotal;
        this.observaciones = observaciones;
        this.serviciosCita = serviciosCita;
    }

    public int getCitaId() {
        return citaId;
    }

    public void setCitaId(int citaId) {
        this.citaId = citaId;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getDuracionTotal() {
        return duracionTotal;
    }

    public void setDuracionTotal(int duracionTotal) {
        this.duracionTotal = duracionTotal;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public List<CitaServicio> getServiciosCita() {
        return serviciosCita;
    }

    public void setServiciosCita(List<CitaServicio> serviciosCita) {
        this.serviciosCita = serviciosCita;
    }

    @Override
    public String toString() {
        return "Cita{" +
                "fecha=" + fecha +
                ", hora='" + hora + '\'' +
                ", duracionTotal=" + duracionTotal +
                ", precioTotal=" + precioTotal +
                ", observaciones='" + observaciones + '\'' +
                ", citaId=" + citaId +
                ", usuario=" + usuario +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cita cita = (Cita) o;
        return citaId == cita.citaId && duracionTotal == cita.duracionTotal && Double.compare(precioTotal, cita.precioTotal) == 0 && Objects.equals(usuario, cita.usuario) && Objects.equals(fecha, cita.fecha) && Objects.equals(hora, cita.hora) && Objects.equals(observaciones, cita.observaciones) && Objects.equals(serviciosCita, cita.serviciosCita);
    }

    @Override
    public int hashCode() {
        return Objects.hash(citaId, usuario, fecha, hora, duracionTotal, precioTotal, observaciones, serviciosCita);
    }
}
