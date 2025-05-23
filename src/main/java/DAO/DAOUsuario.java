package DAO;

import models.Usuario;

import java.util.ArrayList;

public interface DAOUsuario {

    boolean create(Usuario usuario, DAOManager dao);

    boolean update(Usuario usuario, DAOManager dao);

    boolean delete(String id, DAOManager dao);

    ArrayList<Usuario> readAll(DAOManager dao);

}