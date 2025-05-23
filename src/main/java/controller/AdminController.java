package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import models.Admin;
import models.Usuario;
import view.MainApp;

public class AdminController {

    @FXML
    private Label welcomeLabel;

    private Admin admin;
    private final Controlador controlador = Controlador.getInstance();

    // Métodos públicos con @FXML
    @FXML
    public void handleGestionUsuarios() {
        try {
            MainApp.showGestionUsuarios(admin);
        } catch (Exception e) {
            mostrarError("No se pudo cargar la gestión de usuarios");
        }
    }

    @FXML
    public void handleAsignarIncidencias() {
        try {
            MainApp.showAsignarIncidencias(admin);
        } catch (Exception e) {
            mostrarError("Error al cargar la asignación de incidencias");
        }
    }

    @FXML
    public void handleVerIncidencias() {
        try {
            MainApp.showVerIncidencias(admin);
        } catch (Exception e) {
            mostrarError("Error al cargar el listado de incidencias");
        }
    }

    @FXML
    public void handleLogout() {
        try {
            admin.setUltimoAcceso(java.time.LocalDateTime.now());
            controlador.actualizaUsuario(admin);
            MainApp.showLogin();
        } catch (Exception e) {
            mostrarError("Error al cerrar sesión");
        }
    }

    // Método para mostrar errores
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(mensaje);
        alert.show();
    }

    public void setUsuario(Usuario usuario) {
        if (!(usuario instanceof Admin)) {
            throw new IllegalArgumentException("El usuario debe ser de tipo Admin");
        }
        this.admin = (Admin) usuario;
        actualizarInterfaz();
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
        actualizarInterfaz();
    }

    private void actualizarInterfaz() {
        if (admin != null) {
            welcomeLabel.setText("Bienvenido, " + admin.getNombre() + " " + admin.getApellidos());
        }
    }
}