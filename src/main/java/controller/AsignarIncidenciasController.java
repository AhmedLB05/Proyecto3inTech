package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.Admin;
import models.Incidencia;
import models.Tecnico;
import view.MainApp;

public class AsignarIncidenciasController {

    @FXML
    private TextField idIncidenciaField;
    @FXML
    private TextField emailTecnicoField;
    @FXML
    private Label mensajeLabel;
    private Admin admin;
    private Controlador controlador = Controlador.getInstance(); // Singleton

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    @FXML
    private void handleBuscarIncidencia() {
        if (idIncidenciaField.getText().isEmpty()) {
            mostrarError("Ingrese un ID de incidencia");
            return;
        }
        try {
            Incidencia incidencia = controlador.buscaIncidenciaByIdAdmin(idIncidenciaField.getText(), admin);
            mostrarMensaje(incidencia != null ? "Incidencia encontrada" : "No encontrada");
        } catch (Exception e) {
            mostrarError("Error al buscar");
        }
    }

    @FXML
    private void handleBuscarTecnico() {
        if (emailTecnicoField.getText().isEmpty()) {
            mostrarError("Ingrese un email");
            return;
        }
        try {
            Tecnico tecnico = controlador.buscaTecnicoByEmail(emailTecnicoField.getText());
            mostrarMensaje(tecnico != null ? "Técnico encontrado" : "No encontrado");
        } catch (Exception e) {
            mostrarError("Error al buscar");
        }
    }

    @FXML
    private void handleAsignar() {
        String idIncidencia = idIncidenciaField.getText().trim();
        String emailTecnico = emailTecnicoField.getText().trim();

        // 1. Validar campos vacíos
        if (idIncidencia.isEmpty() || emailTecnico.isEmpty()) {
            mostrarError("Complete todos los campos");
            return;
        }

        try {
            // 2. Buscar incidencia y técnico (con verificación explícita)
            Incidencia incidencia = controlador.buscaIncidenciaByIdAdmin(idIncidencia, admin);
            if (incidencia == null) {
                mostrarError("La incidencia no existe o no tiene permisos");
                return;
            }

            Tecnico tecnico = controlador.buscaTecnicoByEmail(emailTecnico);
            if (tecnico == null) {
                mostrarError("El técnico no está registrado");
                return;
            }

            // 3. Validar estado de la incidencia
            if (!incidencia.getEstado().equals("Pendiente")) { // Ajusta según tu implementación
                mostrarError("La incidencia no está disponible para asignar");
                return;
            }

            // 4. Intentar asignación
            if (controlador.asignaIncidencia(incidencia, tecnico)) {
                mostrarMensaje("Asignación exitosa a " + tecnico.getNombre());
                limpiarCampos(); // Método nuevo para resetear campos
            } else {
                mostrarError("Error interno al asignar");
            }

        } catch (IllegalArgumentException e) {
            mostrarError("Datos inválidos: " + e.getMessage());
        } catch (Exception e) {
            mostrarError("Error crítico: " + e.getClass().getSimpleName());
            e.printStackTrace(); // Para depuración
        }
    }

    private void limpiarCampos() {
        idIncidenciaField.clear();
        emailTecnicoField.clear();
    }

    @FXML
    private void handleVolver() {
        try {
            MainApp.showAdminMenu(admin);
        } catch (Exception e) {
            mostrarError("Error al volver");
        }
    }

    private void mostrarMensaje(String mensaje) {
        mensajeLabel.setText(mensaje);
    }

    private void mostrarError(String mensaje) {
        new Alert(Alert.AlertType.ERROR, mensaje).show();
    }
}