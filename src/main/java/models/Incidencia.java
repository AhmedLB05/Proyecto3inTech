package models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Incidencia {

    private String id; //ID de la incidencia
    private String descripcion; //Descripción que explica motivo de la incidencia
    private String estado; //Estado de la incidencia (pendiente/en curso/resuelta)
    private String idCliente; //ID del cliente que ha creado la incidencia
    private String idTecnicoAsignado; //ID del tecnico que ha sido asignado
    private boolean solucionada; //Indica si la incidencia ha sido solucionada
    private String solucion; //Solucion o informe sobre la resolucion de la incidencia
    private LocalDate fechaCreacion; //Fecha en la que se ha creado la incidencia
    private LocalDate fechaSolucion; //Fecha en la que se ha dado por solucionada la incidencia

    //Constructor
    public Incidencia(String id, String descripcion, String idCliente) {
        this.id = id;
        this.descripcion = descripcion;
        this.estado = "Pendiente";
        this.idCliente = idCliente;
        this.fechaCreacion = LocalDate.now();
    }

    //Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
        this.solucionada = "Resuelta".equals(estado);
    }

    public String getSolucion() {
        return this.solucion != null ? this.solucion : "Sin solución";
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdTecnicoAsignado() {
        return idTecnicoAsignado;
    }

    public void setIdTecnicoAsignado(String idTecnicoAsignado) {
        this.idTecnicoAsignado = idTecnicoAsignado;
    }

    public boolean isSolucionada() {
        return solucionada;
    }

    public String getSolucionadaFormateada() {
        return this.solucionada ? "Sí" : "No";
    }

    public void setSolucionada(boolean solucionada) {
        this.solucionada = solucionada;
    }

    public void setSolucion(String solucion) {
        this.solucion = solucion;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaCreacionFormateada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return fechaCreacion.format(formatter);
    }

    public LocalDate getFechaSolucion() {
        return fechaSolucion;
    }

    public void setFechaSolucion(LocalDate fechaSolucion) {
        this.fechaSolucion = fechaSolucion;
    }

    public String getFechaSolucionFormateada() {
        if (fechaSolucion != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return fechaSolucion.format(formatter);
        } else {
            return "No solucionada";
        }
    }

    @Override
    public String toString() {
        return "────────────────────────────────────────────────\n" + "ID Incidencia         : " + id + "\n" + "Descripción           : " + descripcion + "\n" + "Fecha de creación     : " + getFechaCreacionFormateada() + "\n" + "Estado                : " + estado + "\n" + "ID Cliente            : " + idCliente + "\n" + "ID Técnico asignado   : " + (idTecnicoAsignado != null ? idTecnicoAsignado : "No asignado") + "\n" + "Solucionada           : " + (solucionada ? "Sí" : "No") + "\n" + "Solución              : " + (solucion != null ? solucion : "-----------") + "\n" + "Fecha de solución     : " + getFechaSolucionFormateada() + "\n" + "────────────────────────────────────────────────\n";
    }
}