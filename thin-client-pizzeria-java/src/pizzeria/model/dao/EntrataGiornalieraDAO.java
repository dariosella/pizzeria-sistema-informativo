package pizzeria.model.dao;

import pizzeria.exception.DAOException;
import pizzeria.model.domain.EntrataGiornaliera;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class EntrataGiornalieraDAO implements GenericProcedureDAO<EntrataGiornaliera> {

    @Override
    public EntrataGiornaliera execute(Object... params) throws DAOException {
        LocalDate data = (LocalDate) params[0];

        try {
            Connection conn = ConnectionFactory.getConnection();
            try (CallableStatement cs = conn.prepareCall("{call Pizzeria.entrata_giornaliera(?)}")) {
                cs.setDate(1, Date.valueOf(data));

                if (cs.execute()) {
                    try (ResultSet rs = cs.getResultSet()) {
                        if (rs.next()) {
                            return new EntrataGiornaliera(
                                    rs.getDate("data").toLocalDate(),
                                    rs.getBigDecimal("entrata_giornaliera")
                            );
                        }
                    }
                }
                return null;
            }
        } catch (SQLException e) {
            throw new DAOException("Errore nella consultazione dell'entrata giornaliera: " + e.getMessage(), e);
        }
    }
}
