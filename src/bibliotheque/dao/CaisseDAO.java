package src.bibliotheque.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import src.bibliotheque.model.Amende;
import src.bibliotheque.model.Caisse;

public class CaisseDAO {

    // CREATE
    public void ajouter(Caisse caisse) throws SQLException {
        String sql = "INSERT INTO caisse (solde) VALUES (?)";
        try (
            Connection conn = ConnexionBD.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setDouble(1, caisse.getSolde());
            ps.executeUpdate();
        }
    }

    // READ
    public Caisse trouverParId(int id) throws SQLException {
        String sql = "SELECT * FROM caisse WHERE id = ?";
        try (
            Connection conn = ConnexionBD.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Caisse c = new Caisse(
                        rs.getInt("id"),
                        rs.getDouble("solde")
                    );
                    return c;
                }
            }
        }
        return null;
    }

    public List<Caisse> listerToutes() throws SQLException {
        List<Caisse> caisses = new ArrayList<>();
        String sql = "SELECT * FROM caisse";
        try (
            Connection conn = ConnexionBD.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)
        ) {
            while (rs.next()) {
                caisses.add(new Caisse(rs.getInt("id")));
            }
        }
        return caisses;
    }

    // Delete
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM caisse WHERE id = ?";
        try (
            Connection conn = ConnexionBD.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public void encaisserAmende(Caisse caisse, Amende amende)
        throws SQLException {
        if (amende.estPayee()) {
            throw new IllegalStateException("Amende déjà payée !");
        }

        Connection conn = null;
        try {
            conn = ConnexionBD.getConnection();
            conn.setAutoCommit(false);

            // 1. Update solde caisse
            String sql1 = "UPDATE caisse SET solde = solde + ? WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql1)) {
                ps.setDouble(1, amende.getMontant());
                ps.setInt(2, caisse.getId());
                ps.executeUpdate();
            }

            // 2. Lier amende à la caisse
            String sql2 = "UPDATE amende SET caisse_id = ? WHERE id = ?";
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
