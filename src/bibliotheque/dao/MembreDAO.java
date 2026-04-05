package src.bibliotheque.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import src.bibliotheque.model.Emprunt;
import src.bibliotheque.model.Membre;

public class MembreDAO {

    // CREATE
    public void ajouter(Membre membre) throws SQLException {
        String sql =
            "INSERT INTO membre (nom, prenom, adresse, date_inscription) VALUES (?, ?, ?, ?)";

        try (
            Connection conn = ConnexionBD.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
            )
        ) {
            ps.setString(1, membre.getNom());
            ps.setString(2, membre.getPrenom());
            ps.setString(3, membre.getAdresse());

            ps.setDate(4, Date.valueOf(membre.getDateInscription()));

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                membre.setId(rs.getInt(1));
            }
        }
    }

    // READ
    public List<Membre> listerTous() throws SQLException {
        List<Membre> membres = new ArrayList<>();
        String sql = "SELECT * FROM membre";

        try (
            Connection conn = ConnexionBD.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)
        ) {
            while (rs.next()) {
                Membre m = new Membre(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("adresse"),
                    rs.getDate("date_inscription").toLocalDate(),
                    new ArrayList<Emprunt>() // Liste d'emprunts vide au départ
                );
                membres.add(m);
            }
        }
        return membres;
    }

    // UPDATE
    public void modifier(Membre membreModifie) throws SQLException {
        String sql =
            "UPDATE membre SET nom = ?, prenom = ?, adresse = ? WHERE id = ?";

        try (
            Connection conn = ConnexionBD.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, membreModifie.getNom());
            ps.setString(2, membreModifie.getPrenom());
            ps.setString(3, membreModifie.getAdresse());
            ps.setInt(4, membreModifie.getId());

            ps.executeUpdate();
        }
    }

    // DELETE
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM membre WHERE id = ?";
        try (
            Connection conn = ConnexionBD.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
