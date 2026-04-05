package src.bibliotheque.dao;

import java.sql.*;
import src.bibliotheque.model.*;

public class CaisseDAO {

    public void encaisserAmende(Caisse caisse, Amende amende) throws SQLException {

        if (!amende.estPayee()) {
            throw new IllegalStateException("Amende non payée !");
        }

        Connection conn = null;

        try {
            conn = ConnexionBD.getConnection();
            conn.setAutoCommit(false);

            // update caisse
            String sql1 = "UPDATE caisse SET solde = solde + ? WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql1)) {
                ps.setDouble(1, amende.getMontant());
                ps.setInt(2, caisse.getId());
                ps.executeUpdate();
            }

            // lier amende
            String sql2 = "UPDATE amende SET caisse_id=? WHERE id=?";
            try (PreparedStatement ps = conn.prepareStatement(sql2)) {
                ps.setInt(1, caisse.getId());
                ps.setInt(2, amende.getId());
                ps.executeUpdate();
            }

            conn.commit();

        } catch (Exception e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) conn.close();
        }
    }
}