package DAO;

import models.Admin;
import models.Cliente;
import models.Tecnico;
import models.Usuario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DAOUsuarioSQL implements DAOUsuario {

    @Override
    public boolean create(Usuario usuario, DAOManager dao) {
        String sentencia;

        if (usuario instanceof Admin admin) {
            String ultimoAcceso = admin.getUltimoAcceso() != null ?
                    "'" + admin.getUltimoAcceso().toString() + "'" : "NULL";

            sentencia = "INSERT INTO usuarios VALUES ('"
                    + usuario.getId() + "','"
                    + "admin" + "','"
                    + usuario.getNombre() + "','"
                    + usuario.getApellidos() + "','"
                    + usuario.getEmail() + "','"
                    + usuario.getPass() + "','"
                    + usuario.getTelefono() + "',"
                    + "NULL" + ","  // direccion
                    + "NULL" + ","  // nombre_empresa
                    + "NULL" + ","  // nivel_exp
                    + ultimoAcceso + ")";
        } else if (usuario instanceof Tecnico tecnico) {

            sentencia = "INSERT INTO usuarios VALUES ('"
                    + usuario.getId() + "','"
                    + "tecnico" + "','"
                    + usuario.getNombre() + "','"
                    + usuario.getApellidos() + "','"
                    + usuario.getEmail() + "','"
                    + usuario.getPass() + "','"
                    + usuario.getTelefono() + "',"
                    + "NULL" + ","  // direccion
                    + "NULL" + ","  // nombre_empresa
                    + "'" + tecnico.getNivelExp() + "'" + ","  // nivel_exp
                    + "NULL" + ")";  // ultimo_acceso
        } else if (usuario instanceof Cliente cliente) {

            sentencia = "INSERT INTO usuarios VALUES ('"
                    + usuario.getId() + "','"
                    + "cliente" + "','"
                    + usuario.getNombre() + "','"
                    + usuario.getApellidos() + "','"
                    + usuario.getEmail() + "','"
                    + usuario.getPass() + "','"
                    + cliente.getDireccion() + "','"
                    + usuario.getTelefono() + "','"
                    + cliente.getNombreEmpresa() + "',"
                    + "NULL" + ","  // nivel_exp
                    + "NULL" + ")";  // ultimo_acceso
        } else {
            return false;
        }
        try (Statement stmt = dao.getConn().createStatement()) {
            stmt.executeUpdate(sentencia);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean update(Usuario usuario, DAOManager dao) {
        String sentencia;

        // Construir la sentencia SQL según el tipo de usuario
        if (usuario instanceof Admin admin) {
            String ultimoAcceso = admin.getUltimoAcceso() != null ?
                    "'" + admin.getUltimoAcceso().toString() + "'" : "NULL";

            sentencia = "UPDATE usuarios SET " +
                    "tipo = 'admin', " +
                    "nombre = '" + usuario.getNombre() + "', " +
                    "apellidos = '" + usuario.getApellidos() + "', " +
                    "email = '" + usuario.getEmail() + "', " +
                    "password = '" + usuario.getPass() + "', " +
                    "telefono = '" + usuario.getTelefono() + "', " +
                    "direccion = NULL, " +
                    "nombre_empresa = NULL, " +
                    "nivel_exp = NULL, " +
                    "ultimo_acceso = " + ultimoAcceso + " " +
                    "WHERE id = '" + usuario.getId() + "'";
        } else if (usuario instanceof Tecnico tecnico) {

            sentencia = "UPDATE usuarios SET " +
                    "tipo = 'tecnico', " +
                    "nombre = '" + usuario.getNombre() + "', " +
                    "apellidos = '" + usuario.getApellidos() + "', " +
                    "email = '" + usuario.getEmail() + "', " +
                    "password = '" + usuario.getPass() + "', " +
                    "telefono = '" + usuario.getTelefono() + "', " +
                    "direccion = NULL, " +
                    "nombre_empresa = NULL, " +
                    "nivel_exp = '" + tecnico.getNivelExp() + "', " +
                    "ultimo_acceso = NULL " +
                    "WHERE id = '" + usuario.getId() + "'";
        } else if (usuario instanceof Cliente cliente) {

            sentencia = "UPDATE usuarios SET " +
                    "tipo = 'cliente', " +
                    "nombre = '" + usuario.getNombre() + "', " +
                    "apellidos = '" + usuario.getApellidos() + "', " +
                    "email = '" + usuario.getEmail() + "', " +
                    "password = '" + usuario.getPass() + "', " +
                    "telefono = '" + usuario.getTelefono() + "', " +
                    "direccion = '" + cliente.getDireccion() + "', " +
                    "nombre_empresa = '" + cliente.getNombreEmpresa() + "', " +
                    "nivel_exp = NULL, " +
                    "ultimo_acceso = NULL " +
                    "WHERE id = '" + usuario.getId() + "'";
        } else {
            return false;
        }


        try (Statement stmt = dao.getConn().createStatement()) {
            stmt.executeUpdate(sentencia);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean delete(String id, DAOManager dao) {
        String sentencia;
        sentencia = "DELETE FROM usuarios WHERE id = '" + id + "';";

        try (Statement stmt = dao.getConn().createStatement()) {
            stmt.executeUpdate(sentencia);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public ArrayList<Usuario> readAll(DAOManager dao) {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        String sentencia = "SELECT * FROM usuarios";

        try (Statement stmt = dao.getConn().createStatement();
             ResultSet rs = stmt.executeQuery(sentencia)) {

            while (rs.next()) {
                String tipoUsuario = rs.getString("tipo");
                Usuario usuario = null;

                switch (tipoUsuario) {
                    case "admin":
                        usuario = new Admin(
                                rs.getString("id"),
                                rs.getString("nombre"),
                                rs.getString("apellidos"),
                                rs.getString("email"),
                                rs.getString("password"),
                                rs.getString("telefono")
                        );
                        if (rs.getTimestamp("ultimo_acceso") != null) {
                            ((Admin) usuario).setUltimoAcceso(
                                    rs.getTimestamp("ultimo_acceso").toLocalDateTime()
                            );
                        }
                        break;

                    case "tecnico":
                        usuario = new Tecnico(
                                rs.getString("id"),
                                rs.getString("nombre"),
                                rs.getString("apellidos"),
                                rs.getString("email"),
                                rs.getString("password"),
                                rs.getString("telefono"),
                                rs.getString("nivel_exp")
                        );
                        break;

                    case "cliente":
                        usuario = new Cliente(
                                rs.getString("id"),
                                rs.getString("nombre"),
                                rs.getString("apellidos"),
                                rs.getString("email"),
                                rs.getString("password"),
                                rs.getString("telefono"),
                                rs.getString("direccion"),
                                rs.getString("nombre_empresa")
                        );
                        break;
                }

                if (usuario != null) {
                    usuarios.add(usuario);
                }
            }
        } catch (SQLException ex) {
            return null;
        }

        return usuarios;
    }
}
