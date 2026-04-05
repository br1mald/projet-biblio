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

public class LivreDAO {

    // CREATE
    public void ajouter(Livre livre) throws SQLException {
        String sql =
            "INSERT INTO livre (isbn, titre, auteur, genre, annee_publication) VALUES (?, ?, ?, ?, ?)";
        try (
            Connection conn = ConnexionBD.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, livre.getIsbn());
            ps.setString(2, livre.getTitre());
            ps.setString(3, livre.getAuteur());
            ps.setString(4, livre.getGenre());
            ps.setInt(5, livre.getAnneePublication());
            ps.executeUpdate();
        }
    }

    // READ
    public List<Livre> listerTous() throws SQLException {
        List<Livre> livres = new ArrayList<>();
        String sql = "SELECT * FROM livre";
        try (
            Connection conn = ConnexionBD.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)
        ) {
            while (rs.next()) {
                Livre l = new Livre(
                    rs.getString("isbn"),
                    rs.getString("titre"),
                    rs.getString("auteur"),
                    rs.getString("genre"),
                    rs.getInt("annee_publication"),
                    new ArrayList<Exemplaire>()
                );
                livres.add(l);
            }
        }
        return livres;
    }

    public Livre trouverParIsbn(String isbn) throws SQLException {
        String sql = "SELECT * FROM livre WHERE isbn = ?";
        try (
            Connection conn = ConnexionBD.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setString(1, isbn);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Livre livre = new Livre(
                    rs.getString("isbn"),
                    rs.getString("titre"),
                    rs.getString("auteur"),
                    rs.getString("genre"),
                    rs.getInt("annee_publication")
                );
                return livre;
            }
            return null;
        }
    }

    // UPDATE
    public void modifier(Livre livreModifie) throws SQLException {
        String sql =
            "UPDATE livre SET titre = ?, auteur = ?, genre = ?, annee_publication = ? WHERE isbn = ?";

        try (
            Connection conn = ConnexionBD.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, livreModifie.getTitre());
            ps.setString(2, livreModifie.getAuteur());
            ps.setString(3, livreModifie.getGenre());
            ps.setInt(4, livreModifie.getAnneePublication());

            ps.setString(5, livreModifie.getIsbn());

            ps.executeUpdate();
        }
    }

    // DELETE
    public void supprimer(String isbn) throws SQLException {
        String sql = "DELETE FROM livre WHERE isbn = ?";
        try (
            Connection conn = ConnexionBD.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setString(1, isbn);
            ps.executeUpdate();
        }
    }
}
