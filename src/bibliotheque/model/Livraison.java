package src.bibliotheque.model;

import java.time.LocalDate;

public class Livraison {

    private int id;
    private LocalDate dateLivraison;
    private Vehicule vehicule;
    private Annexe annexeOrigine;
    private Annexe annexeDestination;

    public Livraison() {}

    public Livraison(
        int id,
        LocalDate dateLivraison,
        Vehicule vehicule,
        Annexe annexeOrigine,
        Annexe annexeDestination
    ) {
        this.id = id;
        this.dateLivraison = dateLivraison;
        this.vehicule = vehicule;
        this.annexeOrigine = annexeOrigine;
        this.annexeDestination = annexeDestination;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDateLivraison() {
        return this.dateLivraison;
    }

    public void setDateLivraison(LocalDate dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public Vehicule getVehicule() {
        return this.vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    public Annexe getAnnexeOrigine() {
        return this.annexeOrigine;
    }

    public void setAnnexeOrigine(Annexe annexeOrigine) {
        this.annexeOrigine = annexeOrigine;
    }

    public Annexe getAnnexeDestination() {
        return this.annexeDestination;
    }

    public void setAnnexeDestination(Annexe annexeDestination) {
        this.annexeDestination = annexeDestination;
    }

    public static void effectuer() {
        Annexe annexeOrigine = new Annexe();
        Annexe annexeDestination = new Annexe();
        annexeOrigine.setNom("Origine");
        annexeDestination.setNom("Destination");

        Livraison livraison = new Livraison();
        livraison.setAnnexeOrigine(annexeOrigine);
        livraison.setAnnexeDestination(annexeDestination);
        livraison.setDateLivraison(LocalDate.now());

        System.out.println(
            "Livraison effectuée entre " +
                livraison.getAnnexeOrigine() +
                " et " +
                livraison.getAnnexeDestination() +
                ". Date: " +
                livraison.getDateLivraison()
        );
    }
}
