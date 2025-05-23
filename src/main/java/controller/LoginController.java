package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.Admin;
import models.Cliente;
import models.Tecnico;
import models.Usuario;
import view.MainApp;

public class LoginController {

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;

    private final Controlador controlador = Controlador.getInstance();

    @FXML
    private void handleLogin() {
        errorLabel.setVisible(false);
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        if (email.isEmpty() || password.isEmpty()) {
            showError("Email y contraseña son obligatorios");
            return;
        }

        Usuario usuario = controlador.inicioSesion(email, password);

        if (usuario != null) {
            try {
                if (usuario.getTipoUsuario() == null) {
                    showError("El usuario no tiene tipo definido");
                    return;
                }
                String tipoUsuario = usuario.getTipoUsuario().toLowerCase();
                switch (tipoUsuario) {
                    case "admin" -> MainApp.showAdminMenu((Admin) usuario);
                    case "tecnico" -> MainApp.showTecnicoMenu((Tecnico) usuario);
                    case "cliente" -> MainApp.showClienteMenu((Cliente) usuario);
                    default -> showError("Tipo de usuario no reconocido");
                }
                clearFields();
            } catch (Exception e) {
                showError("Error al cargar la interfaz: " + e.getMessage());
                System.err.println("Error detallado:");
                e.printStackTrace();
            }
        } else {
            showError("Credenciales incorrectas. Verifique email y/o contraseña");
        }
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    private void clearFields() {
        emailField.clear();
        passwordField.clear();
    }
}