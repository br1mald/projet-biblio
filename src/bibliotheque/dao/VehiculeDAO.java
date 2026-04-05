package src.bibliotheque.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import src.bibliotheque.model.Vehicule;

public class VehiculeDAO {

    // CREATE
    public void ajouter(Vehicule vehicule) throws SQLException {
        String sql = "INSERT INTO vehicule (immatriculation, capacite, disponible, kilometrage) VALUES (?, ?, ?, ?)";
        try (
            Connection conn = ConnexionBD.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, vehicule.getImmatriculation());
            ps.setInt(2, vehicule.getCapacite());
            ps.setBoolean(3, vehicule.isDisponible());
            ps.setDouble(4, vehicule.getKilometrage());
            ps.executeUpdate();
        }
    }

    // READ
    public List<Vehicule> listerTous() throws SQLException {
        List<Vehicule> vehicules = new ArrayList<>();
        String sql = "SELECT * FROM vehicule";
        try (
            Connection conn = ConnexionBD.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)
        ) {
            while (rs.next()) {
                Vehicule v = new Vehicule(
                    rs.getString("immatriculation"),
                    rs.getInt("capacite"),
                    rs.getDouble("kilometrage")
                );
                v.setDisponible(rs.getBoolean("disponible"));
                vehicules.add(v);
            }
        }
        return vehicules;
    }

    // UPDATE
    public void modifier(Vehicule vehicule) throws SQLException {
        String sql = "UPDATE vehicule SET capacite = ?, disponible = ?, kilometrage = ? WHERE immatriculation = ?";
        try (
            Connection conn = ConnexionBD.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, vehicule.getCapacite());
            ps.setBoolean(2, vehicule.isDisponible());
            ps.setDouble(3, vehicule.getKilometrage());
            ps.setString(4, vehicule.getImmatriculation());
            ps.executeUpdate();
        }
    }

    // DELETE
    public void supprimer(String immatriculation) throws SQLException {
        String sql = "DELETE FROM vehicule WHERE immatriculation = ?";
        try (
            Connection conn = ConnexionBD.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, immatriculation);
            ps.executeUpdate();
        }
    }
}
