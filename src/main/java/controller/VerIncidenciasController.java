package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Admin;
import models.Incidencia;
import view.MainApp;

public class VerIncidenciasController {

    @FXML
    private TableView<Incidencia> incidenciasTable;

    private Admin admin;
    private final Controlador controlador = Controlador.getInstance();
    private final ObservableList<Incidencia> incidenciasData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configurarTabla();
        incidenciasTable.setItems(incidenciasData);
        cargarIncidencias();
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    private void configurarTabla() {
        TableColumn<Incidencia, String> idCol = crearColumna("ID", "id", 80);
        TableColumn<Incidencia, String> descCol = crearColumna("Descripción", "descripcion", 200);
        TableColumn<Incidencia, String> estadoCol = crearColumna("Estado", "estado", 100);
        TableColumn<Incidencia, String> fechaCol = crearColumna("Fecha Creación", "fechaCreacionFormateada", 120);
        TableColumn<Incidencia, String> tecnicoCol = crearColumna("Técnico", "idTecnicoAsignado", 100);
        TableColumn<Incidencia, String> fechaSolucionCol = crearColumna("Fecha Solución", "fechaSolucionFormateada", 120);

        TableColumn<Incidencia, String> idClienteCol = crearColumna("ID Cliente", "idCliente", 100);
        TableColumn<Incidencia, String> solucionCol = crearColumna("Solución", "solucion", 200);
        TableColumn<Incidencia, String> solucionadaCol = crearColumna("Solucionada", "solucionadaFormateada", 100);

        incidenciasTable.getColumns().addAll(
                idCol, descCol, estadoCol, fechaCol, tecnicoCol,
                fechaSolucionCol, idClienteCol, solucionCol, solucionadaCol
        );
    }

    private TableColumn<Incidencia, String> crearColumna(String titulo, String propiedad, double ancho) {
        TableColumn<Incidencia, String> columna = new TableColumn<>(titulo);
        columna.setCellValueFactory(new PropertyValueFactory<>(propiedad));
        columna.setPrefWidth(ancho);
        return columna;
    }

    private void cargarIncidencias() {
        try {
            incidenciasData.setAll(controlador.getIncidencias());
        } catch (Exception e) {
            mostrarError("Error al cargar incidencias: " + e.getMessage());
        }
    }

    @FXML
    private void handleActualizar() {
        cargarIncidencias();
        incidenciasTable.refresh();
    }

    @FXML
    private void handleVolver() {
        try {
            MainApp.showAdminMenu(admin);
        } catch (Exception e) {
            mostrarError("Error al volver: " + e.getMessage());
        }
    }

    private void mostrarError(String mensaje) {
        System.err.println("[ERROR] " + mensaje);
    }
}