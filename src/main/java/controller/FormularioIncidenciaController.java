package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import models.Cliente;

public class FormularioIncidenciaController {

    @FXML
    private TextArea descripcionTextArea;
    private Cliente cliente;
    private final Controlador controlador = Controlador.getInstance();

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @FXML
    private void handleCrear() {
        if (descripcionTextArea.getText().isEmpty()) {
            // Mostrar error
            return;
        }

        if (controlador.creaIncidenciaCliente(cliente, descripcionTextArea.getText())) {
            cerrarVentana();
        }
    }

    @FXML
    private void handleCancelar() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        descripcionTextArea.getScene().getWindow().hide();
    }
}