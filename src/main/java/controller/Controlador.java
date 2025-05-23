package controller;

import DAO.DAOIncidenciaSQL;
import DAO.DAOManager;
import DAO.DAOUsuarioSQL;
import models.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Controlador {


    public final DAOUsuarioSQL daoUsuarioSQL = new DAOUsuarioSQL();
    public final DAOIncidenciaSQL daoIncidenciaSQL = new DAOIncidenciaSQL();
    public final DAOManager dao;
    private final ArrayList<Usuario> usuarios;
    private final ArrayList<Incidencia> incidencias;
    private static final Controlador instance = new Controlador();

    private Controlador() {
        // Constructor actual
        dao = DAOManager.getSinglentonInstance();
        try {
            dao.open();
            usuarios = daoUsuarioSQL.readAll(dao);
            dao.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            dao.open();
            incidencias = daoIncidenciaSQL.readAll(dao);
            dao.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Controlador getInstance() {
        return instance;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    //Metodo para iniciar sesion verifica que exista un usuario con esas credenciales
    public Usuario inicioSesion(String email, String pass) {
        for (Usuario u : usuarios) {
            if (u.login(email, pass)) return u;
        }
        return null;
    }

    //Metodo que busca todas las incidencias creadas por un cliente usando su id
    public ArrayList<Incidencia> buscaIncidenciasCliente(Cliente clienteLog) {
        ArrayList<Incidencia> incidenciasEncontradas = new ArrayList<>();
        if (this.incidencias != null) {
            for (Incidencia i : incidencias) {
                if (i.getIdCliente().equals(clienteLog.getId())) incidenciasEncontradas.add(i);
            }
        }
        return incidenciasEncontradas;
    }

    //Metodo que busca una incidencia por su ID y comprueba que el cliente que la esté buscando sea dueño de la incidencia
    public Incidencia buscaIncidenciaByIdCliente(String id, Cliente clienteLog) {
        if (this.incidencias != null) {
            for (Incidencia i : incidencias) {
                if (i.getId().equals(id) && i.getIdCliente().equals(clienteLog.getId())) return i;
            }
        }
        return null;
    }

    //Metodo que crea una incidencia por un cliente
    public boolean creaIncidenciaCliente(Cliente clienteLog, String descripcion) {
        Cliente cliente = (Cliente) buscaUsuarioById(clienteLog.getId());
        if (cliente != null) {
            Incidencia incidenciaNueva = new Incidencia(generaIdIncidencia(), descripcion, cliente.getId());
            try {
                dao.open();
                boolean creada = daoIncidenciaSQL.create(incidenciaNueva, dao);
                dao.close();
                if (creada) return incidencias.add(incidenciaNueva);
                else return false;
            } catch (Exception e) {
                try {
                    dao.close();
                } catch (SQLException ex) {
                    return false;
                }
            }
        }
        return false;
    }

    //Metodo que busca un usuario por su ID
    public Usuario buscaUsuarioById(String idIntro) {
        for (Usuario u : usuarios) {
            if (u.getId().equals(idIntro)) return u;
        }
        return null;
    }

    //Metodo para generar un ID para las incidencias
    private String generaIdIncidencia() {
        String idIncidencia;
        do {
            idIncidencia = "i";
            int numId = (int) (Math.random() * 100000);
            idIncidencia += numId;

        } while (buscaIncidencia(idIncidencia) != null);
        return idIncidencia;
    }

    //Metodo que busca una incidencia por su ID
    private Incidencia buscaIncidencia(String idIncidencia) {
        if (this.incidencias != null) {
            for (Incidencia i : incidencias) {
                if (i.getId().equals(idIncidencia)) return i;
            }
        }
        return null;
    }

    //Metodo para generar un ID para los clientes
    public String generaIdCliente() {
        String idCliente;
        do {
            idCliente = "c";
            int id = (int) (Math.random() * 100000);
            idCliente += id;
        } while (buscaUsuarioById(idCliente) != null);
        return idCliente;
    }

    //Metodo para generar un ID para los admin
    public String generaIdAdmin() {
        String idAdmin;
        do {
            idAdmin = "a";
            int id = (int) (Math.random() * 100000);
            idAdmin += id;
        } while (buscaUsuarioById(idAdmin) != null);
        return idAdmin;
    }

    //Metodo para generar un ID para los tecnicos
    public String generaIdTecnico() {
        String idTecnico;
        do {
            idTecnico = "t";
            int id = (int) (Math.random() * 100000);
            idTecnico += id;
        } while (buscaUsuarioById(idTecnico) != null);
        return idTecnico;
    }

    //Metodo que busca las incidencias asignadas a un id de un tecnico
    public ArrayList<Incidencia> buscaIncidenciasAsignadas(Tecnico tecnicoLog) {
        ArrayList<Incidencia> incidenciasAsignadas = new ArrayList<>();
        if (tecnicoLog == null) return incidenciasAsignadas;
        if (this.incidencias != null) {
            for (Incidencia i : incidencias) {
                if (i != null) {
                    if (i.getIdTecnicoAsignado() != null) {
                        if (i.getIdTecnicoAsignado().equals(tecnicoLog.getId())) incidenciasAsignadas.add(i);
                    }
                }
            }
        }
        return incidenciasAsignadas;
    }

    //Metodo que busca una incidencia por su ID y comprueba que el tecnico que la esté buscando sea el responsable de la incidencia
    public Incidencia buscaIncidenciaByIdTecnico(Tecnico tecnicoLog, String id) {
        if (this.incidencias != null) {
            for (Incidencia i : incidencias) {
                if (i.getId().equals(id) && i.getIdTecnicoAsignado().equals(tecnicoLog.getId())) return i;
            }
        }
        return null;
    }

    //Metodo soluciona una incidencia por un tecnico
    public boolean solucionaIncidencia(String solucion, Incidencia incidenciaBuscada) {
        Incidencia incidenciaResolver = buscaIncidencia(incidenciaBuscada.getId());
        if (incidenciaResolver == null) return false;
        incidenciaResolver.setSolucion(solucion);
        incidenciaResolver.setSolucionada(true);
        incidenciaResolver.setFechaSolucion(LocalDate.now());
        try {
            dao.open();
            boolean incidenciaSolucionada = daoIncidenciaSQL.update(incidenciaResolver, dao);
            dao.close();
            return incidenciaSolucionada;
        } catch (Exception e) {
            try {
                dao.close();
            } catch (SQLException ex) {
                return false;
            }
        }
        return false;
    }

    //Metodo cambia el estado de una incidencia
    public boolean setEstado(int op, Incidencia incidenciaBuscada) {
        Incidencia incidenciaCambiarEstado = buscaIncidencia(incidenciaBuscada.getId());
        if (incidenciaCambiarEstado != null) {
            switch (op) {
                case 1 -> incidenciaCambiarEstado.setEstado("Pendiente");
                case 2 -> incidenciaCambiarEstado.setEstado("En curso");
                case 3 -> incidenciaCambiarEstado.setEstado("Resuelta");
            }
            try {
                dao.open();
                boolean incidenciaSolucionada = daoIncidenciaSQL.update(incidenciaCambiarEstado, dao);
                dao.close();
                return incidenciaSolucionada;
            } catch (Exception e) {
                try {
                    dao.close();
                } catch (SQLException ex) {
                    return false;
                }
            }
            return false;
        }
        return false;
    }

    //Metodo que devuelve todas las incidencias
    public ArrayList<Incidencia> getIncidencias() {
        return incidencias;
    }

    //Metodo que busca una incidencia por su ID y comprueba que el admin que la esté buscando sea admin
    public Incidencia buscaIncidenciaByIdAdmin(String idIncidencia, Admin admin) {
        if (this.incidencias != null) {
            if (admin.getTipoUsuario().equalsIgnoreCase("admin")) {
                for (Incidencia i : incidencias) {
                    if (i.getId().equals(idIncidencia)) return i;
                }
            }
        }
        return null;
    }

    public boolean asignaIncidencia(Incidencia incidenciaBuscada, Tecnico tecnicoBuscado) {
        if (incidenciaBuscada == null || tecnicoBuscado == null) {
            return false;
        }

        try {
            dao.open();
            boolean exito = false;

            for (Incidencia i : incidencias) {
                if (i.getId().equals(incidenciaBuscada.getId())) {
                    i.setIdTecnicoAsignado(tecnicoBuscado.getId());
                    exito = daoIncidenciaSQL.update(i, dao);
                    break;
                }
            }

            dao.close();
            return exito;

        } catch (SQLException e) {
            System.err.println("Error en BD: " + e.getMessage());
            try {
                dao.close();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar conexión: " + ex.getMessage());
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }


    //Metodo para crear un nuevo cliente
    public boolean creaNuevoCliente(String nombre, String apellidos, String email, String pass, String telefono, String direccion, String nombreEmpresa) {
        Cliente nuevoCliente = new Cliente(generaIdCliente(), nombre, apellidos, email, pass, direccion, telefono, nombreEmpresa);
        try {
            dao.open();
            boolean creado = daoUsuarioSQL.create(nuevoCliente, dao);
            dao.close();
            if (creado) return usuarios.add(nuevoCliente);
            else return false;
        } catch (Exception e) {
            try {
                dao.close();
            } catch (SQLException ex) {
                return false;
            }
        }
        return false;
    }

    //Metodo que busca un usuario por su email
    public Usuario buscaUsuarioByEmail(String email) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(email)) return u;
        }
        return null;
    }

    //Metodo para crear un nuevo tecnico
    public boolean creaNuevoTecnico(String nombre, String apellidos, String email, String pass, String telefono, String nivelExp) {
        Tecnico nuevoTecnico = new Tecnico(generaIdTecnico(), nombre, apellidos, email, pass, telefono, nivelExp);
        try {
            dao.open();
            boolean creado = daoUsuarioSQL.create(nuevoTecnico, dao);
            dao.close();
            if (creado) return usuarios.add(nuevoTecnico);
            else return false;
        } catch (Exception e) {
            try {
                dao.close();
            } catch (SQLException ex) {
                return false;
            }
        }
        return false;
    }

    //Metodo para crear un nuevo admin
    public boolean creaNuevoAdmin(String nombre, String apellidos, String email, String pass, String telefono) {
        Admin nuevoAdmin = new Admin(generaIdAdmin(), nombre, apellidos, email, pass, telefono);
        try {
            dao.open();
            boolean creado = daoUsuarioSQL.create(nuevoAdmin, dao);
            dao.close();
            if (creado) return usuarios.add(nuevoAdmin);
            else return false;
        } catch (Exception e) {
            try {
                dao.close();
            } catch (SQLException ex) {
                return false;
            }
        }
        return false;
    }

    //Metodo que busca un tecnico por su email
    public Tecnico buscaTecnicoByEmail(String emailTecnico) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(emailTecnico)) return (Tecnico) u;
        }
        return null;
    }

    //Metodo para borrar un usuario
    public boolean bajaUsuario(String idUsuarioBuscado) {
        Usuario u = buscaUsuarioById(idUsuarioBuscado);
        if (u != null) {
            try {
                dao.open();
                boolean borrado = daoUsuarioSQL.delete(idUsuarioBuscado, dao);
                dao.close();
                if (borrado) return usuarios.remove(u);
                else return false;
            } catch (Exception e) {
                try {
                    dao.close();
                } catch (SQLException ex) {
                    return false;
                }
            }
        }
        return false;
    }

    //Metodo para actualizar un usuario
    public boolean actualizaUsuario(Usuario usuario) {
        try {
            dao.open();
            boolean usuarioActualizado = daoUsuarioSQL.update(usuario, dao);
            dao.close();
            if (usuarioActualizado) return true;
        } catch (Exception e) {
            try {
                dao.close();
            } catch (Exception ex) {
                return false;
            }
        }
        return false;
    }

    //Metodo para actualizar una incidencia
    public boolean actualizaIncidencia(Incidencia incidencia) {
        try {
            dao.open();
            boolean estadoActualizado = daoIncidenciaSQL.update(incidencia, dao);
            dao.close();
            if (estadoActualizado) return true;
        } catch (Exception e) {
            try {
                dao.close();
            } catch (SQLException ex) {
                return false;
            }
        }
        return false;
    }
}
