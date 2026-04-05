package src.bibliotheque.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import src.bibliotheque.model.EtatExemplaire;
import src.bibliotheque.model.Exemplaire;

public class ExemplaireDAO {

    // CREATE
    public void ajouter(Exemplaire exemplaire) throws SQLException {
        String sql = "INSERT INTO exemplaire (etat, disponible, livre_isbn, annexe_id) VALUES (?, ?, ?, ?)";
        try (
            Connection conn = ConnexionBD.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, exemplaire.getEtat().name());
            ps.setBoolean(2, exemplaire.isDisponible());
            ps.setString(3, exemplaire.getLivre().getIsbn());
            ps.setInt(4, exemplaire.getAnnexe().getId());
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
                ex.setEtat(EtatExemplaire.valueOf(rs.getString("etat")));
                ex.setDisponible(rs.getBoolean("disponible"));
                exemplaires.add(ex);
            }
        }
        return exemplaires;
    }

    // UPDATE
    public void modifier(Exemplaire exemplaire) throws SQLException {
        String sql = "UPDATE exemplaire SET etat = ?, disponible = ?, annexe_id = ? WHERE id = ?";
        try (
            Connection conn = ConnexionBD.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, exemplaire.getEtat().name());
            ps.setBoolean(2, exemplaire.isDisponible());
            ps.setInt(3, exemplaire.getAnnexe().getId());
            ps.setInt(4, exemplaire.getId());
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
