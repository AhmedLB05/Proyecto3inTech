package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import models.Incidencia;
import models.Tecnico;

import java.util.Arrays;

public class ResolverIncidenciaController {

    @FXML
    private Label infoIncidenciaLabel;
    @FXML
    private ComboBox<String> estadoComboBox;
    @FXML
    private TextArea solucionTextArea;

    private Tecnico tecnico;
    private Incidencia incidencia;
    private final Controlador controlador = Controlador.getInstance();

    public void setDatos(Tecnico tecnico, Incidencia incidencia) {
        this.tecnico = tecnico;
        this.incidencia = incidencia;
        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
        estadoComboBox.getItems().addAll(
                Arrays.asList("Pendiente", "En progreso", "Resuelta")
        );
        estadoComboBox.setValue(incidencia.getEstado());
        infoIncidenciaLabel.setText("Incidencia #" + incidencia.getId() + "\n" + incidencia.getDescripcion());
        solucionTextArea.setText(incidencia.getSolucion());
    }

    @FXML
    private void handleConfirmar() {
        String estado = estadoComboBox.getValue();
        String solucion = solucionTextArea.getText().trim();

        if (estado == null) {
            mostrarError("Seleccione un estado");
            return;
        }

        if ("Resuelta".equals(estado) && solucion.isEmpty()) {
            mostrarError("Ingrese la solución para marcar como Resuelta");
            return;
        }

        incidencia.setEstado(estado);
        incidencia.setSolucion(solucion.isEmpty() ? null : solucion);

        if (controlador.actualizaIncidencia(incidencia)) {
            cerrarVentana();
        } else {
            mostrarError("Error al guardar. Verifique la conexión o los datos.");
        }
    }

    @FXML
    private void handleCancelar() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        solucionTextArea.getScene().getWindow().hide();
    }

    private void mostrarError(String mensaje) {
        System.err.println("[ERROR] " + mensaje);
    }
}