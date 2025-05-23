package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import models.Admin;
import models.Cliente;
import models.Tecnico;
import models.Usuario;

public class FormularioUsuarioController {

    @FXML
    private TextField nombreField;
    @FXML
    private TextField apellidosField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passField;
    @FXML
    private TextField telefonoField;
    @FXML
    private ComboBox<String> tipoUsuarioCombo;

    @FXML
    private HBox clienteFields;
    @FXML
    private TextField direccionField;
    @FXML
    private TextField empresaField;

    @FXML
    private HBox tecnicoFields;
    @FXML
    private TextField nivelExpField;

    private Admin admin;
    private Usuario usuarioEditando;
    private Controlador controlador = Controlador.getInstance();

    @FXML
    public void initialize() {
        tipoUsuarioCombo.getItems().addAll("Cliente", "Técnico", "Administrador");
        tipoUsuarioCombo.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            actualizarCamposVisibles(newVal);
        });
    }

    public void setDatos(Admin admin, Usuario usuario) {
        this.admin = admin;
        this.usuarioEditando = usuario;

        if (usuario != null) {
            cargarDatosExistente();
        } else {
            tipoUsuarioCombo.setValue("Cliente");
            actualizarCamposVisibles("Cliente");
        }
    }

    private void cargarDatosExistente() {
        nombreField.setText(usuarioEditando.getNombre());
        apellidosField.setText(usuarioEditando.getApellidos());
        emailField.setText(usuarioEditando.getEmail());
        passField.setText(usuarioEditando.getPass());
        telefonoField.setText(usuarioEditando.getTelefono());

        if (usuarioEditando instanceof Cliente) {
            tipoUsuarioCombo.setValue("Cliente");
            Cliente c = (Cliente) usuarioEditando;
            direccionField.setText(c.getDireccion());
            empresaField.setText(c.getNombreEmpresa());
        } else if (usuarioEditando instanceof Tecnico) {
            tipoUsuarioCombo.setValue("Técnico");
            Tecnico t = (Tecnico) usuarioEditando;
            nivelExpField.setText(t.getNivelExp());
        } else if (usuarioEditando instanceof Admin) {
            tipoUsuarioCombo.setValue("Administrador");
        }
    }

    private void actualizarCamposVisibles(String tipoUsuario) {
        clienteFields.setVisible(false);
        tecnicoFields.setVisible(false);

        switch (tipoUsuario) {
            case "Cliente":
                clienteFields.setVisible(true);
                break;
            case "Técnico":
                tecnicoFields.setVisible(true);
                break;
            case "Administrador":
                clienteFields.setVisible(false);
                tecnicoFields.setVisible(false);
                break;
        }
    }

    @FXML
    private void handleGuardar() {
        if (validarCampos()) {
            if (usuarioEditando == null) {
                crearNuevoUsuario();
            } else {
                actualizarUsuarioExistente();
            }
            cerrarFormulario();
        }
    }

    private boolean validarCampos() {
        if (nombreField.getText().isEmpty() ||
                apellidosField.getText().isEmpty() ||
                emailField.getText().isEmpty() ||
                passField.getText().isEmpty()) {

            mostrarError("Todos los campos obligatorios deben estar completos");
            return false;
        }

        if ("Cliente".equals(tipoUsuarioCombo.getValue()) && direccionField.getText().isEmpty()) {
            mostrarError("La dirección es obligatoria para clientes");
            return false;
        }

        if (usuarioEditando == null) {
            if (controlador.buscaUsuarioByEmail(emailField.getText()) != null) {
                mostrarError("El email ya está registrado");
                return false;
            }
        } else {
            if (!emailField.getText().equals(usuarioEditando.getEmail()) &&
                    controlador.buscaUsuarioByEmail(emailField.getText()) != null) {
                mostrarError("El email ya está registrado");
                return false;
            }
        }

        return true;
    }

    private void crearNuevoUsuario() {
        String tipo = tipoUsuarioCombo.getValue();
        boolean resultado = false;

        switch (tipo) {
            case "Cliente":
                resultado = controlador.creaNuevoCliente(
                        nombreField.getText(),
                        apellidosField.getText(),
                        emailField.getText(),
                        passField.getText(),
                        telefonoField.getText(),
                        direccionField.getText(),
                        empresaField.getText()
                );
                break;

            case "Técnico":
                resultado = controlador.creaNuevoTecnico(
                        nombreField.getText(),
                        apellidosField.getText(),
                        emailField.getText(),
                        passField.getText(),
                        telefonoField.getText(),
                        nivelExpField.getText()
                );
                break;

            case "Administrador":
                resultado = controlador.creaNuevoAdmin(
                        nombreField.getText(),
                        apellidosField.getText(),
                        emailField.getText(),
                        passField.getText(),
                        telefonoField.getText()
                );
                break;
        }

        if (!resultado) {
            mostrarError("Error al crear el usuario");
        }
    }

    private void actualizarUsuarioExistente() {
        usuarioEditando.setNombre(nombreField.getText());
        usuarioEditando.setApellidos(apellidosField.getText());
        usuarioEditando.setEmail(emailField.getText());
        usuarioEditando.setPass(passField.getText());
        usuarioEditando.setTelefono(telefonoField.getText());

        if (usuarioEditando instanceof Cliente) {
            Cliente c = (Cliente) usuarioEditando;
            c.setDireccion(direccionField.getText());
            c.setNombreEmpresa(empresaField.getText());
        } else if (usuarioEditando instanceof Tecnico) {
            Tecnico t = (Tecnico) usuarioEditando;
            t.setNivelExp(nivelExpField.getText());
        }

        if (!controlador.actualizaUsuario(usuarioEditando)) {
            mostrarError("Error al actualizar el usuario");
        }
    }

    @FXML
    private void handleCancelar() {
        cerrarFormulario();
    }

    private void cerrarFormulario() {
        emailField.getScene().getWindow().hide();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}