package pizzeria.model.dao;

import pizzeria.exception.DAOException;
import pizzeria.model.domain.ProdottoDaPreparare;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BevandeDaPreparareDAO implements GenericProcedureDAO<List<ProdottoDaPreparare>> {

    @Override
    public List<ProdottoDaPreparare> execute(Object... params) throws DAOException {
        List<ProdottoDaPreparare> bevande = new ArrayList<>();

        try {
            Connection conn = ConnectionFactory.getConnection();
            try (CallableStatement cs = conn.prepareCall(
                    "{call Pizzeria.bevande_da_preparare()}")) {
                if (cs.execute()) {
                    try (ResultSet rs = cs.getResultSet()) {
                        while (rs.next()) {
                            bevande.add(new ProdottoDaPreparare(
                                    rs.getLong("numero"),
                                    rs.getString("nome_prodotto")
                            ));
                        }
                    }
                }
            }
            return bevande;
        } catch (SQLException e) {
            throw new DAOException("Errore nella consultazione delle bevande: " + e.getMessage(), e);
        }
    }
}
