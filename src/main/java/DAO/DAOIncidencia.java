package DAO;

import models.Incidencia;

import java.util.ArrayList;

public interface DAOIncidencia {

    boolean create(Incidencia incidencia, DAOManager dao);

    boolean update(Incidencia incidencia, DAOManager dao);

    boolean delete(String id, DAOManager dao);

    ArrayList<Incidencia> readAll(DAOManager dao);

}