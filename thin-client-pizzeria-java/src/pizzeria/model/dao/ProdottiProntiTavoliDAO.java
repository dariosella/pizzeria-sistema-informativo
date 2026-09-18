package pizzeria.model.dao;

import pizzeria.exception.DAOException;
import pizzeria.model.domain.ProdottoPronto;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdottiProntiTavoliDAO implements GenericProcedureDAO<List<ProdottoPronto>> {

    @Override
    public List<ProdottoPronto> execute(Object... params) throws DAOException {
        List<ProdottoPronto> prodotti = new ArrayList<>(); // lista di ProdottoPronto (prodotti pronti)

        try {
            Connection conn = ConnectionFactory.getConnection();
            try (CallableStatement cs = conn.prepareCall("{call Pizzeria.prodotti_pronti_tavoli()}")) {
                if (cs.execute()) {
                    try (ResultSet rs = cs.getResultSet()) {
                        while (rs.next()) {
                            prodotti.add(new ProdottoPronto(
                                    rs.getLong("numero"),
                                    rs.getString("nome_prodotto"),
                                    rs.getInt("numero_tavolo")
                            ));
                        }
                    }
                }
            }
            return prodotti;
        } catch (SQLException e) {
            throw new DAOException("Errore nella consultazione dei prodotti pronti: " + e.getMessage(), e);
        }
    }
}
