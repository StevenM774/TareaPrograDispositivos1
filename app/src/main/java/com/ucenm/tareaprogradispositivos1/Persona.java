package com.ucenm.tareaprogradispositivos1;

import com.google.gson.annotations.SerializedName;

public class Persona {
    @SerializedName("id")
    private int id;
    @SerializedName("nombres")
    private String nombres;
    @SerializedName("apellidos")
    private String apellidos;
    @SerializedName("direccion")
    private String direccion;
    @SerializedName("telefono")
    private String telefono;
    @SerializedName("foto")
    private String foto;

    public Persona() {
    }

    public Persona(String nombres, String apellidos, String direccion, String telefono, String foto) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
