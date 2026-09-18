package pizzeria.model.dao;

import pizzeria.exception.DAOException;

import java.sql.SQLException;

// P è un tipo generico e rappresenta il risultato restituito dal DAO.
public interface GenericProcedureDAO<P> {
    // Object... params è un array di lunghezza variabile
    // Consente di passare un numero diverso di parametri a ogni DAO.
    // L’ordine deve corrispondere esattamente a quello atteso dal DAO.
    P execute(Object... params) throws DAOException, SQLException;

}
