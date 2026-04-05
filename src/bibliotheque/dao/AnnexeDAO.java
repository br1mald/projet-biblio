package src.bibliotheque.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import src.bibliotheque.model.Annexe;

public class AnnexeDAO {

    // CREATE
    public void ajouter(Annexe annexe) throws SQLException {
        String sql = "INSERT INTO annexe (nom) VALUES (?)";
        try (
            Connection conn = ConnexionBD.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setString(1, annexe.getNom());
            ps.executeUpdate();
        }
    }

    // READ
    public List<Annexe> lister() throws SQLException {
        List<Annexe> annexes = new ArrayList<Annexe>();
        String sql = "SELECT * FROM annexe";
        try (
            Connection conn = ConnexionBD.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
        ) {
            while (rs.next()) {
                Annexe ann = new Annexe(rs.getInt("id"), rs.getString("nom"));
                annexes.add(ann);
            }
        }
        return annexes;
    }

    public Annexe trouverParId(int id) throws SQLException {
        String sql = "SELECT * FROM annexe WHERE id = ?";
        try (
            Connection conn = ConnexionBD.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Annexe(rs.getInt("id"), rs.getString("nom"));
            }
            return null;
        }
    }

    // UPDATE
    public void modifier(int id, String nom) throws SQLException {
        // ici on récupère l'id et le nom directement vu que le nom est le seul champ modifiable
        String sql = "UPDATE annexe SET nom = ? WHERE id = ?";
        try (
            Connection conn = ConnexionBD.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setString(1, nom);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    // DELETE
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM annexe WHERE id = ?";
        try (
            Connection conn = ConnexionBD.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
