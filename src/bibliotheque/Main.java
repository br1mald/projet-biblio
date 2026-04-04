package src.bibliotheque;

import java.sql.SQLException;
import java.util.List;
import src.bibliotheque.dao.LivreDAO;
import src.bibliotheque.model.Livre;

public class Main {

    public static void main(String[] args) {
        LivreDAO livreDAO = new LivreDAO();

        try {
            // Ajouter un livre (persists to DB)
            Livre livre = new Livre(
                "978-2070409228",
                "Les Misérables",
                "Victor Hugo",
                "Drame",
                1820
            );

            livreDAO.ajouter(livre);

            // Lister tous les livres
            List<Livre> livres = livreDAO.listerTous();
            livres.forEach(nouveauLivre ->
                System.out.println(nouveauLivre.getTitre() + " ajouté")
            );
        } catch (SQLException e) {
            System.out.println("Erreur");
            e.printStackTrace();
        }
    }
}
