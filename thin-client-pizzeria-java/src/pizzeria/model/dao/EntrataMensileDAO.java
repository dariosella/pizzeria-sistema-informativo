package pizzeria.model.dao;

import pizzeria.exception.DAOException;
import pizzeria.model.domain.EntrataMensile;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.YearMonth;

public class EntrataMensileDAO implements GenericProcedureDAO<EntrataMensile> {

    @Override
    public EntrataMensile execute(Object... params) throws DAOException {
        YearMonth periodo = (YearMonth) params[0];

        try {
            Connection conn = ConnectionFactory.getConnection();
            try (CallableStatement cs = conn.prepareCall("{call Pizzeria.entrata_mensile(?)}")) {
                cs.setString(1, periodo.toString()); // converte YearMonth in stringa

                if (cs.execute()) {
                    try (ResultSet rs = cs.getResultSet()) {
                        if (rs.next()) {
                            return new EntrataMensile(
                                    rs.getInt("anno"),
                                    rs.getInt("mese"),
                                    rs.getBigDecimal("entrata_mensile")
                            );
                        }
                    }
                }
                return null;
            }
        } catch (SQLException e) {
            throw new DAOException("Errore nella consultazione dell'entrata mensile: " + e.getMessage(), e);
        }
    }
}
