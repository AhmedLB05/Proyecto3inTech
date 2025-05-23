package DAO;

import models.Incidencia;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DAOIncidenciaSQL implements DAOIncidencia {

    @Override
    public boolean create(Incidencia incidencia, DAOManager dao) {
        String sentencia = "INSERT INTO incidencias VALUES ('" +
                incidencia.getId() + "','" +
                incidencia.getDescripcion() + "','" +
                incidencia.getEstado() + "','" +
                incidencia.getIdCliente() + "'," +
                (incidencia.getIdTecnicoAsignado() != null ? "'" + incidencia.getIdTecnicoAsignado() + "'" : "NULL") + "," +
                incidencia.isSolucionada() + "," +
                (incidencia.getSolucion() != null ? "'" + incidencia.getSolucion() + "'" : "NULL") + ",'" +
                incidencia.getFechaCreacion() + "'," +
                (incidencia.getFechaSolucion() != null ? "'" + incidencia.getFechaSolucion() + "'" : "NULL") + ")";
        try (Statement stmt = dao.getConn().createStatement()) {
            stmt.executeUpdate(sentencia);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean update(Incidencia incidencia, DAOManager dao) {
        String sentencia = "UPDATE incidencias SET " +
                "descripcion = '" + incidencia.getDescripcion() + "', " +
                "estado = '" + incidencia.getEstado() + "', " +
                "id_cliente = '" + incidencia.getIdCliente() + "', " +
                "id_tecnico = " + (incidencia.getIdTecnicoAsignado() != null ? "'" + incidencia.getIdTecnicoAsignado() + "'" : "NULL") + ", " +
                "solucionada = " + incidencia.isSolucionada() + ", " +
                "solucion = " + (incidencia.getSolucion() != null ? "'" + incidencia.getSolucion() + "'" : "NULL") + ", " +
                "fecha_creacion = '" + incidencia.getFechaCreacion() + "', " +
                "fecha_solucion = " + (incidencia.getFechaSolucion() != null ? "'" + incidencia.getFechaSolucion() + "'" : "NULL") + " " +
                "WHERE id = '" + incidencia.getId() + "'";



        try (Statement stmt = dao.getConn().createStatement()) {
            return stmt.executeUpdate(sentencia) > 0;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean delete(String id, DAOManager dao) {
        String sentencia = "DELETE FROM incidencias WHERE id = '" + id + "'";



        try (Statement stmt = dao.getConn().createStatement()) {
            return stmt.executeUpdate(sentencia) > 0;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public ArrayList<Incidencia> readAll(DAOManager dao) {
        ArrayList<Incidencia> incidencias = new ArrayList<>();
        String sentencia = "SELECT * FROM incidencias";

        try (Statement stmt = dao.getConn().createStatement();
             ResultSet rs = stmt.executeQuery(sentencia)) {

            while (rs.next()) {
                Incidencia incidencia = new Incidencia(
                        rs.getString("id"),
                        rs.getString("descripcion"),
                        rs.getString("id_cliente")
                );

                incidencia.setEstado(rs.getString("estado"));
                incidencia.setIdTecnicoAsignado(rs.getString("id_tecnico"));
                incidencia.setSolucionada(rs.getBoolean("solucionada"));
                incidencia.setSolucion(rs.getString("solucion"));

                incidencia.setFechaCreacion(rs.getDate("fecha_creacion").toLocalDate());
                Date fechaSolucion = rs.getDate("fecha_solucion");
                if (fechaSolucion != null) {
                    incidencia.setFechaSolucion(fechaSolucion.toLocalDate());
                }
                incidencias.add(incidencia);
            }
        } catch (SQLException ex) {
            return null;
        }

        return incidencias;
    }
}