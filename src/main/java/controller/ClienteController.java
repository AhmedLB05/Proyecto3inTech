package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Cliente;
import models.Incidencia;
import view.MainApp;

public class ClienteController {

    @FXML
    private Label welcomeLabel;
    @FXML
    private TableView<Incidencia> incidenciasTable;

    private Cliente cliente;
    private final Controlador controlador = Controlador.getInstance();

    @FXML
    public void initialize() {
        configurarTabla();
    }

    private void configurarTabla() {
        // Configurar columnas de la tabla de incidencias
        incidenciasTable.getColumns().forEach(column -> {
            switch (column.getId()) {
                case "idCol" -> column.setCellValueFactory(new PropertyValueFactory<>("id"));
                case "descCol" -> column.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
                case "estadoCol" -> column.setCellValueFactory(new PropertyValueFactory<>("estado"));
            }
        });
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        welcomeLabel.setText("Bienvenido, " + cliente.getNombre());
        cargarIncidencias();
    }

    private void cargarIncidencias() {
        if (cliente != null) {
            incidenciasTable.getItems().setAll(controlador.buscaIncidenciasCliente(cliente));
        }
    }

    @FXML
    private void handleNuevaIncidencia() {
        MainApp.showFormularioIncidencia(cliente);
    }

    @FXML
    private void handleActualizar() {
        cargarIncidencias();
    }

    @FXML
    private void handleCerrarSesion() {
        MainApp.showLogin();
    }
}
