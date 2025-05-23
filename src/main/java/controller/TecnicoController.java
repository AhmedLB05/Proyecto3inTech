package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Incidencia;
import models.Tecnico;
import view.MainApp;

import java.util.List;

public class TecnicoController {

    @FXML
    private Label welcomeLabel;
    @FXML
    private TableView<Incidencia> incidenciasTable;

    private Tecnico tecnico;
    private final Controlador controlador = Controlador.getInstance();

    @FXML
    public void initialize() {
        configurarTabla();
        cargarIncidencias();
    }

    private void configurarTabla() {
        incidenciasTable.getColumns().forEach(column -> {
            if ("idCol".equals(column.getId())) {
                column.setCellValueFactory(new PropertyValueFactory<>("id"));
            } else if ("descCol".equals(column.getId())) {
                column.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
            } else if ("estadoCol".equals(column.getId())) {
                column.setCellValueFactory(new PropertyValueFactory<>("estado"));
            } else if ("clienteCol".equals(column.getId())) {
                column.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
            } else if ("solucionadaCol".equals(column.getId())) {
                column.setCellValueFactory(new PropertyValueFactory<>("solucionadaFormateada"));
            } else if ("solucionCol".equals(column.getId())) {
                column.setCellValueFactory(new PropertyValueFactory<>("solucion"));
            } else if ("fechaCreacionCol".equals(column.getId())) {
                column.setCellValueFactory(new PropertyValueFactory<>("fechaCreacionFormateada"));
            } else if ("fechaSolucionCol".equals(column.getId())) {
                column.setCellValueFactory(new PropertyValueFactory<>("fechaSolucionFormateada"));
            }
        });
    }

    public void setTecnico(Tecnico tecnico) {
        if (tecnico == null) {
            throw new IllegalArgumentException("Técnico no puede ser nulo");
        }
        System.out.println("[DEBUG] Técnico asignado: " + tecnico.getNombre() + " (ID: " + tecnico.getId() + ")");
        this.tecnico = tecnico;
        welcomeLabel.setText("Bienvenido, " + tecnico.getNombre());
        cargarIncidencias();
    }

    private void cargarIncidencias() {
        List<Incidencia> incidencias = controlador.buscaIncidenciasAsignadas(tecnico);
        System.out.println("[DEBUG] Incidencias recuperadas: " + incidencias.size());
        incidenciasTable.getItems().setAll(incidencias);
    }

    @FXML
    private void handleResolverIncidencia() {
        Incidencia seleccionada = incidenciasTable.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            MainApp.showResolverIncidencia(tecnico, seleccionada);
            incidenciasTable.refresh();
            cargarIncidencias();
        } else {
            mostrarError("Seleccione una incidencia para resolver");
        }
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private void handleActualizar() {
        cargarIncidencias();
    }

    @FXML
    private void handleCerrarSesion() {
        Stage stage = (Stage) welcomeLabel.getScene().getWindow();
        stage.close();
        MainApp.showLogin();
    }
}