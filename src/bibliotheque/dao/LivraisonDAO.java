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
            "INSERT INTO livraison (date_livraison, immatriculation_vehicule, id_annexe_origine, id_annexe_destination) VALUES (?, ?, ?, ?)";

        try (
            Connection conn = ConnexionBD.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setDate(1, Date.valueOf(livraison.getDateLivraison()));
            ps.setString(2, livraison.getVehicule().getImmatriculation());
            ps.setInt(3, livraison.getAnnexeOrigine().getId());
            ps.setInt(4, livraison.getAnnexeDestination().getId());

            ps.executeUpdate();
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
                    null,
                    null,
                    null,
                    null,
                    null
                ); // TODO: modifier plus tard pour mettre les bons attributs
                livraisons.add(liv);
            }
        }
        return livraisons;
    }
}
