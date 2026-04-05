package src.bibliotheque.dao;

import java.sql.*;
import java.time.LocalDate;
import src.bibliotheque.model.Amende;
import src.bibliotheque.model.Emprunt;

public class AmendeDAO {

    // ============================
    // CREATE
    // ============================
    public Amende creerAmende(int idEmprunt, double montant) throws SQLException {

        if (montant <= 0) {
            throw new IllegalArgumentException("Montant invalide");
        }

        String sql = "INSERT INTO amende (montant, payee, date_creation, emprunt_id, caisse_id) VALUES (?, FALSE, ?, ?, NULL)";

        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDouble(1, montant);
            ps.setDate(2, Date.valueOf(LocalDate.now()));
            ps.setInt(3, idEmprunt);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                Amende a = new Amende();
                a.setId(rs.getInt(1));
                a.setMontant(montant);
                a.setPayee(false);
                a.setDateCreation(LocalDate.now());

                Emprunt e = new Emprunt();
                e.setId(idEmprunt);
                a.setEmprunt(e);

                return a;
            }
        }

        throw new SQLException("Erreur création amende");
    }

    // ============================
    // UPDATE (payer)
    // ============================
    public void marquerPayee(Amende amende) throws SQLException {

        if (!amende.estPayee()) {
            throw new IllegalStateException("Amende non payée en mémoire !");
        }

        String sql = "UPDATE amende SET payee = TRUE WHERE id = ?";

        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, amende.getId());
            ps.executeUpdate();
        }
    }

    // ============================
    // READ
    // ============================
    public Amende trouverParId(int id) throws SQLException {

        String sql = "SELECT * FROM amende WHERE id = ?";

        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Amende a = new Amende();
                a.setId(rs.getInt("id"));
                a.setMontant(rs.getDouble("montant"));
                a.setPayee(rs.getBoolean("payee"));
                a.setDateCreation(rs.getDate("date_creation").toLocalDate());
                return a;
            }
        }

        return null;
    }
}