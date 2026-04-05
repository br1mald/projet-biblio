package src.bibliotheque.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import src.bibliotheque.model.Livraison;

public class LivraisonDAO {

    // CREATE
    public void ajouter(Livraison livraison) throws SQLException {
        String sql =
            "INSERT INTO livraison (date_livraison, vehicule_immatriculation, annexe_origine_id, annexe_destination_id, distance_km) VALUES (?, ?, ?, ?, ?)";

        try (
            Connection conn = ConnexionBD.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
            );
        ) {
            ps.setDate(1, Date.valueOf(livraison.getDateLivraison()));
            ps.setString(2, livraison.getVehicule().getImmatriculation());
            ps.setInt(3, livraison.getAnnexeOrigine().getId());
            ps.setInt(4, livraison.getAnnexeDestination().getId());
            ps.setDouble(5, livraison.getDistanceKm());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                livraison.setId(rs.getInt(1));
            }
        }
    }

    // READ
    public List<Livraison> listerToutes() throws SQLException {
        List<Livraison> livraisons = new ArrayList<>();
        String sql = "SELECT * FROM livraison";

        try (
            Connection conn = ConnexionBD.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)
        ) {
            while (rs.next()) {
                Livraison liv = new Livraison(
                    rs.getInt("id"),
                    rs.getDate("date_livraison").toLocalDate(),
                    rs.getDouble("distance_km")
                );
                String immatriculation = rs.getString(
                    "immatriculation_vehicule"
                );
                int annexeOrigineId = rs.getInt("id_annexe_origine");
                int annexeDestinationId = rs.getInt("id_annexe_destination");
                liv.setAnnexeOrigine(
                    new AnnexeDAO().trouverParId(annexeOrigineId)
                );
                liv.setAnnexeDestination(
                    new AnnexeDAO().trouverParId(annexeDestinationId)
                );
                liv.setVehicule(
                    new VehiculeDAO().trouverParImm(immatriculation)
                );

                livraisons.add(liv);
            }
        }
        return livraisons;
    }

    //UPDATE
    public void modifier(Livraison livraison) throws SQLException {
        String sql =
            "UPDATE livraison SET date_livraison = ?, immatriculation_vehicule = ?, id_annexe_origine = ?, id_annexe_destination = ?, distance_km = ? WHERE id = ?";

        try (
            Connection conn = ConnexionBD.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setDate(1, Date.valueOf(livraison.getDateLivraison()));
            ps.setString(2, livraison.getVehicule().getImmatriculation());
            ps.setInt(3, livraison.getAnnexeOrigine().getId());
            ps.setInt(4, livraison.getAnnexeDestination().getId());
            ps.setDouble(5, livraison.getDistanceKm());
            ps.setInt(6, livraison.getId());

            ps.executeUpdate();
        }
    }

    //DELETE
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM livraison WHERE id = ?";

        try (
            Connection conn = ConnexionBD.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
