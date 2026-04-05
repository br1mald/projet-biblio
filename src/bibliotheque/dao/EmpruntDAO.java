package src.bibliotheque.dao;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import src.bibliotheque.model.*;

public class EmpruntDAO {

    // ============================
    // CREATE
    // ============================
    public void ajouter(Emprunt e) throws SQLException {

        if (!e.getExemplaire().isDisponible()) {
            throw new IllegalStateException("Exemplaire non disponible !");
        }

        String sql = "INSERT INTO emprunt (membre_id, exemplaire_id, date_emprunt, date_retour_prevue, date_retour_effective) VALUES (?, ?, ?, ?, NULL)";

        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, e.getMembre().getId());
            ps.setInt(2, e.getExemplaire().getId());
            ps.setDate(3, Date.valueOf(e.getDateEmprunt()));
            ps.setDate(4, Date.valueOf(e.getDateRetourPrevue()));

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                e.setId(rs.getInt(1));
            }
        }

        setDisponibilite(e.getExemplaire().getId(), false);
    }

    // ============================
    // RETOUR LIVRE
    // ============================
    public Amende retournerLivre(int idEmprunt) throws SQLException {

        String sql = "SELECT * FROM emprunt WHERE id = ?";

        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idEmprunt);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) throw new IllegalArgumentException("Introuvable");

            if (rs.getDate("date_retour_effective") != null) {
                throw new IllegalStateException("Déjà retourné");
            }

            LocalDate prevue = rs.getDate("date_retour_prevue").toLocalDate();
            LocalDate aujourd = LocalDate.now();

            // update retour
            String update = "UPDATE emprunt SET date_retour_effective=? WHERE id=?";
            try (PreparedStatement ps2 = conn.prepareStatement(update)) {
                ps2.setDate(1, Date.valueOf(aujourd));
                ps2.setInt(2, idEmprunt);
                ps2.executeUpdate();
            }

            long retard = ChronoUnit.DAYS.between(prevue, aujourd);

            Amende amende = null;

            if (retard > 0) {
                double montant = retard * 500;
                amende = new AmendeDAO().creerAmende(idEmprunt, montant);
            }

            setDisponibilite(rs.getInt("exemplaire_id"), true);

            return amende;
        }
    }

    private void setDisponibilite(int idEx, boolean dispo) throws SQLException {

        String sql = "UPDATE exemplaire SET disponible=? WHERE id=?";

        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBoolean(1, dispo);
            ps.setInt(2, idEx);
            ps.executeUpdate();
        }
    }
}