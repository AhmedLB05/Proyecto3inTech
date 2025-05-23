package models;

public class Tecnico extends Usuario {

    private String nivelExp; //Nivel de experiencia del tecnico (Junior, Semi-Senior, Senior)

    //Constructor
    public Tecnico(String id, String nombre, String apellidos, String email, String pass, String telefono, String nivelExp) {
        super(id, nombre, apellidos, email, pass, telefono);
        this.nivelExp = nivelExp;
    }

    //Getters y Setters

    public String getNivelExp() {
        return nivelExp;
    }

    public void setNivelExp(String nivelExp) {
        this.nivelExp = nivelExp;
    }

    //Metodos

    //Metodo para saber el tipo de usuario
    @Override
    public String getTipoUsuario() {
        return "tecnico";
    }
}