package models;

public class Cliente extends Usuario {

    private String direccion; //Direccion del cliente
    private String nombreEmpresa; //Nombre de la empresa del cliente

    //Constructor
    public Cliente(String id, String nombre, String apellidos, String email, String pass, String telefono, String direccion, String nombreEmpresa) {
        super(id, nombre, apellidos, email, pass, telefono);
        this.direccion = direccion;
        this.nombreEmpresa = nombreEmpresa;
    }

    //Getters y Setters
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    //Metodos

    //Metodo para saber el tipo de usuario
    @Override
    public String getTipoUsuario() {
        return "cliente";
    }
}