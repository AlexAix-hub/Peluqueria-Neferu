// Este código está licenciado bajo la Licencia Creative Commons Attribution-ShareAlike 4.0 Internacional.
// Para más información, visita: https://creativecommons.org/licenses/by-sa/4.0/
// Autor: Alejandro Aix Utreras - Año: 2025

package com.example.peluquerianeferu.model;

import java.util.Objects;

public class CitaServicio {

    private int citaServicioId;
    private int citaId;
    private int servicioId;

    public CitaServicio(int citaServicioId, int citaId, int servicioId) {
        this.citaServicioId = citaServicioId;
        this.citaId = citaId;
        this.servicioId = servicioId;
    }

    public CitaServicio(int citaId, Servicio servicio) {
        this.citaId = citaId;
        this.servicioId = servicio.getServicioId();
    }


    public int getCitaServicioId() {
        return citaServicioId;
    }

    public void setCitaServicioId(int citaServicioId) {
        this.citaServicioId = citaServicioId;
    }

    public int getCitaId() {
        return citaId;
    }

    public void setCitaId(int citaId) {
        this.citaId = citaId;
    }

    public int getServicioId() {
        return servicioId;
    }

    public void setServicioId(int servicioId) {
        this.servicioId = servicioId;
    }

    @Override
    public String toString() {
        return "CitaServicio{" +
                "citaServicioId=" + citaServicioId +
                ", citaId=" + citaId +
                ", servicioId=" + servicioId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CitaServicio that = (CitaServicio) o;
        return citaServicioId == that.citaServicioId && citaId == that.citaId && servicioId == that.servicioId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(citaServicioId, citaId, servicioId);
    }
}
