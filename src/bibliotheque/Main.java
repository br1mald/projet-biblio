package src.bibliotheque;

import java.sql.SQLException;
import java.util.List;
import src.bibliotheque.dao.LivreDAO;
import src.bibliotheque.model.Livre;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        LivreDAO livreDAO = new LivreDAO();

        try {
            // Ajouter un livre
            Livre livre = new Livre(
                "978-2070409228",
                "Les Misérables",
                "Victor Hugo",
                "Drame",
                1820 // date incorrecte
            );

            livreDAO.ajouter(livre);

            // Lister tous les livres
            List<Livre> livres = livreDAO.listerTous();
            livres.forEach(nouveauLivre ->
                System.out.println(nouveauLivre.getTitre() + " ajouté")
            );

            Livre livreModifie = new Livre(livre);
            livreModifie.setAnneePublication(1862);
            livreDAO.modifier(livreModifie);
            System.out.println("Livre " + livre.getTitre() + " modifié.");
            System.out.println(
                "Nouvelle valeur: " + livreModifie.getAnneePublication()
            );

            Thread.sleep(10000); // 10 secondes pour vérifier la bd avant la suppression

            livreDAO.supprimer(livre.getIsbn());
            System.out.println("Livre " + livre.getTitre() + " supprimé.");
        } catch (SQLException e) {
            System.out.println("Erreur");
            e.printStackTrace();
        }
    }
}
