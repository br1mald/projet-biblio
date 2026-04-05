package src.bibliotheque.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import src.bibliotheque.model.Exemplaire;
import src.bibliotheque.model.Livre;

public class ExemplaireDAO {

    // CREATE
    public void ajouter(Exemplaire exemplaire) throws SQLException {
        String sql =
            "INSERT INTO exemplaire (etat, disponible, livre_isbn, annexe_id) VALUES (?, ?, ?, ?)";
        try (
            Connection conn = ConnexionBD.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, exemplaire.getEtat().name());
            ps.setBoolean(2, exemplaire.isDisponible());
            ps.setString(3, exemplaire.getLivre().getIsbn());
            if (exemplaire.getAnnexe() != null) {
                // Pour éviter une NullPointerException
                ps.setInt(4, exemplaire.getAnnexe().getId());
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }
            ps.executeUpdate();
        }
    }

    // READ
    public List<Exemplaire> listerTous() throws SQLException {
        List<Exemplaire> exemplaires = new ArrayList<>();
        String sql = "SELECT * FROM exemplaire";
        try (
            Connection conn = ConnexionBD.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)
        ) {
            while (rs.next()) {
                Exemplaire ex = new Exemplaire();
                ex.setId(rs.getInt("id"));
                ex.setEtat(
                    Exemplaire.EtatExemplaire.valueOf(rs.getString("etat"))
                );
                ex.setDisponible(rs.getBoolean("disponible"));

                int annexeId = rs.getInt("annexe_id");
                String livreIsbn = rs.getString("livre_isbn");
                ex.setAnnexe(new AnnexeDAO().trouverParId(annexeId));

                List<Livre> livres = new LivreDAO().listerTous(); // Il faudrait ajouter une fonction pour trouver par isbn
                livres.forEach(livre -> {
                    if (livre.getIsbn().equals(livreIsbn)) ex.setLivre(livre);
                });

                exemplaires.add(ex);
            }
        }
        return exemplaires;
    }

    // UPDATE
    public void modifier(Exemplaire exemplaire) throws SQLException {
        String sql =
            "UPDATE exemplaire SET etat = ?, disponible = ?, livre_isbn = ?, annexe_id = ? WHERE id = ?";
        try (
            Connection conn = ConnexionBD.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, exemplaire.getEtat().name());
            ps.setBoolean(2, exemplaire.isDisponible());

            if (exemplaire.getLivre() != null) {
                ps.setString(3, exemplaire.getLivre().getIsbn());
            } else {
                ps.setNull(3, java.sql.Types.VARCHAR);
            }

            if (exemplaire.getAnnexe() != null) {
                ps.setInt(4, exemplaire.getAnnexe().getId());
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }

            ps.setInt(5, exemplaire.getId());
            ps.executeUpdate();
        }
    }

    // DELETE
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM exemplaire WHERE id = ?";
        try (
            Connection conn = ConnexionBD.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
