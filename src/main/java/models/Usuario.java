package models;

public abstract class Usuario {

    protected String id; //ID de usuario
    protected String nombre; //Nombre del usuario
    protected String apellidos; //Apellidos del usuario
    protected String email; //Email del usuario
    protected String pass; //Contraseña incio sesion usuario
    protected String telefono; //Telefono del usuario

    public Usuario(String id, String nombre, String apellidos, String email, String pass, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.pass = pass;
        this.telefono = telefono;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    //Metodo para saber el tipo de usuario
    public abstract String getTipoUsuario(); //cliente - admin - tecnico

    //Metodo login
    public boolean login(String email, String pass) {
        if (email == null || pass == null) return false;
        return (email.equalsIgnoreCase(this.email) && pass.equals(this.pass));
    }
}
