package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Admin extends Usuario {

    private LocalDateTime ultimoAcceso; //Fecha y hora del ultimo acceso del admin

    //Constructor
    public Admin(String id, String nombre, String apellidos, String email, String pass, String telefono) {
        super(id, nombre, apellidos, email, pass, telefono);
    }

    //Getters y Setters

    public LocalDateTime getUltimoAcceso() {
        return ultimoAcceso;
    }

    public void setUltimoAcceso(LocalDateTime ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }

    public String getUltimoAccesoFormateado() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return this.ultimoAcceso.format(formato);
    }

    //Metodos

    //Metodo para actualizar el último acceso del admin
    public void actualizarUltimoAcceso() {
        this.ultimoAcceso = LocalDateTime.now();
    }

    //Metodo para saber el tipo de usuario
    @Override
    public String getTipoUsuario() {
        return "admin";
    }
}