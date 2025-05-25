// Este código está licenciado bajo la Licencia Creative Commons Attribution-ShareAlike 4.0 Internacional.
// Para más información, visita: https://creativecommons.org/licenses/by-sa/4.0/
// Autor: Alejandro Aix Utreras - Año: 2025

package com.example.peluquerianeferu.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Usuario {
    private int usuarioId;
    private String nombre;
    private String nombreUsuario;
    private String contrasena;
    private String rol;
    private String telefono;
    private List<Cita> citas;

    public Usuario() {
    }

    public Usuario(int usuarioId, String nombre) {
        this.usuarioId = usuarioId;
        this.nombre = nombre;
    }

    public Usuario(String nombre, String nombreUsuario, String contrasena, String rol, String telefono) {
        this.nombre = nombre;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.rol = rol;
        this.telefono = telefono;
    }

    public Usuario(int usuarioId, String nombre, String nombreUsuario, String contrasena, String rol, String telefono, ArrayList<Cita> citas) {
        this.usuarioId = usuarioId;
        this.nombre = nombre;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.rol = rol;
        this.telefono = telefono;
        this.citas = new ArrayList<>();
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Cita> getCitas() {
        return citas;
    }

    public void setCitas(List<Cita> citas) {
        this.citas = citas;
    }

    public void agregarCita(Cita cita) {
        this.citas.add(cita);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "usuarioId=" + usuarioId +
                ", nombre='" + nombre + '\'' +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", rol='" + rol + '\'' +
                ", telefono='" + telefono + '\'' +
                ", citas=" + citas.size() + " citas" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return usuarioId == usuario.usuarioId && Objects.equals(nombre, usuario.nombre) && Objects.equals(nombreUsuario, usuario.nombreUsuario) && Objects.equals(contrasena, usuario.contrasena) && Objects.equals(rol, usuario.rol) && Objects.equals(telefono, usuario.telefono) && Objects.equals(citas, usuario.citas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId, nombre, nombreUsuario, contrasena, rol, telefono, citas);
    }
}
