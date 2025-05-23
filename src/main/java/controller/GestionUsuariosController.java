package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Admin;
import models.Usuario;
import view.MainApp;

public class GestionUsuariosController {

    @FXML
    private TableView<Usuario> usuariosTable;
    @FXML
    private TextField buscarField;

    private Admin admin;
    private Controlador controlador = Controlador.getInstance();
    private ObservableList<Usuario> usuariosData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configurarColumnas();
        cargarUsuarios();
        usuariosTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
        });
    }

    private void configurarColumnas() {
        usuariosTable.getColumns().forEach(column -> {
            String id = column.getId();
            if (id == null) {
                System.err.println("Columna sin id encontrada, ignorando");
                return;
            }
            switch (id) {
                case "idColumn":
                    column.setCellValueFactory(new PropertyValueFactory<>("id"));
                    break;
                case "nombreColumn":
                    column.setCellValueFactory(new PropertyValueFactory<>("nombre"));
                    break;
                case "apellidosColumn":
                    column.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
                    break;
                case "emailColumn":
                    column.setCellValueFactory(new PropertyValueFactory<>("email"));
                    break;
                case "tipoColumn":
                    column.setCellValueFactory(new PropertyValueFactory<>("tipoUsuario"));
                    break;
                default:
                    System.err.println("Columna con id desconocido: " + id);
                    break;
            }
        });
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    private void cargarUsuarios() {
        usuariosData.setAll(controlador.getUsuarios());
        usuariosTable.setItems(usuariosData);
    }

    @FXML
    private void handleAltaUsuario() {
        try {
            MainApp.showFormularioUsuario(admin, null);
            usuariosTable.refresh();
        } catch (Exception e) {
            mostrarError("Error al abrir el formulario de usuario: " + e.getMessage());
            System.err.println("Error detallado:");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBajaUsuario() {
        Usuario seleccionado = usuariosTable.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarError("Seleccione un usuario de la tabla");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar baja");
        alert.setHeaderText("¿Eliminar al usuario " + seleccionado.getNombre() + " " + seleccionado.getApellidos() + "?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (controlador.bajaUsuario(seleccionado.getId())) {
                    cargarUsuarios();
                } else {
                    mostrarError("No se pudo eliminar el usuario");
                }
            }
        });
    }

    @FXML
    private void handleModificarUsuario() {
        Usuario seleccionado = usuariosTable.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarError("Seleccione un usuario de la tabla");
            return;
        }

        try {
            MainApp.showFormularioUsuario(admin, seleccionado);
            usuariosTable.refresh();
        } catch (Exception e) {
            mostrarError("Error al editar el usuario: " + e.getMessage());
            System.err.println("Error detallado:");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBuscarUsuario() {
        String texto = buscarField.getText().trim().toLowerCase();

        if (!texto.isEmpty()) {
            usuariosData.setAll(controlador.getUsuarios().stream().filter(u -> u.getNombre().toLowerCase().contains(texto) || u.getApellidos().toLowerCase().contains(texto) || u.getEmail().toLowerCase().contains(texto)).toList());
        } else {
            cargarUsuarios();
        }
    }

    @FXML
    private void handleVolver() {
        try {
            MainApp.showAdminMenu(admin);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}