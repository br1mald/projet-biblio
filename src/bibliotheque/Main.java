package src.bibliotheque;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import src.bibliotheque.dao.*;
import src.bibliotheque.model.*;
import src.bibliotheque.model.Exemplaire.EtatExemplaire;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        LivreDAO livreDAO = new LivreDAO();
        ExemplaireDAO exemplaireDAO = new ExemplaireDAO();
        AnnexeDAO annexeDAO = new AnnexeDAO();
        CaisseDAO caisseDAO = new CaisseDAO();
        EmpruntDAO empruntDAO = new EmpruntDAO();
        LivraisonDAO livraisonDAO = new LivraisonDAO();
        MembreDAO membreDAO = new MembreDAO();
        VehiculeDAO vehiculeDAO = new VehiculeDAO();

        try {
            System.out.println("--- DÉBUT DE LA SIMULATION ---");
            // Initialisation
            Livre livre = new Livre(
                "978-2070409228",
                "Les Misérables",
                "Victor Hugo",
                "Drame",
                1820 // date incorrecte, modifier plus tard?
            );

            Annexe ann1 = new Annexe(1, "Bibliothèque Centrale");
            Annexe ann2 = new Annexe(2, "Annexe Nord");
            annexeDAO.ajouter(ann1);
            annexeDAO.ajouter(ann2);

            Exemplaire ex1 = new Exemplaire(
                1,
                Exemplaire.EtatExemplaire.NEUF,
                livre,
                ann1
            );
            Exemplaire ex2 = new Exemplaire(
                2,
                Exemplaire.EtatExemplaire.BON,
                livre,
                ann1
            );
            Exemplaire ex3 = new Exemplaire(
                3,
                Exemplaire.EtatExemplaire.BON,
                livre,
                ann2
            );
            exemplaireDAO.ajouter(ex1);
            exemplaireDAO.ajouter(ex2);
            exemplaireDAO.ajouter(ex3);

            Membre membre = new Membre(
                1,
                "Fall",
                "Fanta",
                "Liberté 6",
                LocalDate.of(2025, Month.JANUARY, 30),
                new ArrayList<>()
            );
            membreDAO.ajouter(membre);

            Vehicule vehicule = new Vehicule("AA-123_DK", 50, 20);
            vehiculeDAO.ajouter(vehicule);

            // Test des actions

            // Le membre emprunte un exemplaire au niveau de la Bibliothèque Centrale
            System.out.println(
                "Mme Fall emprunte Les Misérables à la Bibliothèque Centrale."
            );
            Emprunt emprunt = new Emprunt(1, membre, ex2, 10);

            emprunt.setDateRetourPrevue(LocalDate.now().minusDays(5)); // On modifie la date de retour prévue pour simuler un retard
            empruntDAO.ajouter(emprunt);

            // Le membre retourne le livre
            Amende amendeGeneree = empruntDAO.retournerLivre(emprunt.getId());
            if (amendeGeneree != null) {
                // Si l'amende est correctement générée on affiche le montant
                System.out.println(
                    "RETOUR: L'exemplaire a été ramené avec un retard de 5 jours, une amende de " +
                        amendeGeneree.getMontant() +
                        " FCFA est générée."
                );

                // Le membre paie l'amende
                Caisse caisse = new Caisse(1);
                // caisseDAO.ajouter(caisse); il faut une méthode ajouter pour caisseDAO
                caisseDAO.encaisserAmende(caisse, amendeGeneree);
                System.out.println(
                    "CAISSE: L'amende a été payée. Le solde de la caisse est mis à jour."
                );
            }

            // L'exemplaire rendu est en mauvais état
            emprunt.getExemplaire().setEtat(EtatExemplaire.ABIME);
            exemplaireDAO.modifier(emprunt.getExemplaire());
            System.out.println(
                "CONSERVATION: L'exemplaire est abîmé, son état est mis à jour dans le système."
            );

            // On livre un exemplaire du livre à l'Annexe Nord qui n'en a plus qu'un
            List<Exemplaire> exemplairesALivrer = new ArrayList<Exemplaire>();
            exemplairesALivrer.add(ex1);
            Livraison liv1 = new Livraison(
                1,
                LocalDate.now(),
                vehicule,
                ann1,
                ann2,
                exemplairesALivrer,
                2.5
            );
            liv1.effectuer();
            livraisonDAO.ajouter(liv1);
            System.out.println(
                "LIVRAISON: Départ d'un véhicule pour transférer l'exemplaire ID 1 vers l'Annexe Nord"
            );
            // L'exemplaire ne se trouve plus au même endroit donc on le réflète dans la db
            exemplairesALivrer.forEach(exemplaire -> {
                try {
                    exemplaireDAO.modifier(exemplaire);
                } catch (SQLException e) {
                    System.out.println(
                        "Erreur lors de la modification de l'exemplaire " +
                            exemplaire.getId()
                    );
                }
            });

            // On supprime tout ce qui a été créé
            livraisonDAO.supprimer(liv1.getId());
            exemplaireDAO.supprimer(ex1.getId());
            exemplaireDAO.supprimer(ex2.getId());
            exemplaireDAO.supprimer(ex3.getId());

            livreDAO.supprimer(livre.getIsbn());

            vehiculeDAO.supprimer(vehicule.getImmatriculation());

            annexeDAO.supprimer(ann1.getId());
            annexeDAO.supprimer(ann2.getId());

            membreDAO.supprimer(membre.getId());
        } catch (SQLException e) {
            System.out.println("Erreur");
            e.printStackTrace();
        }
    }
}
