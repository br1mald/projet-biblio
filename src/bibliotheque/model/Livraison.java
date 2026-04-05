package src.bibliotheque.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Livraison {

    private int id;
    private LocalDate dateLivraison;
    private Vehicule vehicule;
    private Annexe annexeOrigine;
    private Annexe annexeDestination;
    private List<Exemplaire> exemplairesALivrer;
    private Double distanceKm;

    public Livraison() {
        this.exemplairesALivrer = new ArrayList<>();
    }

    public Livraison(
        int id,
        LocalDate dateLivraison,
        Vehicule vehicule,
        Annexe annexeOrigine,
        Annexe annexeDestination,
        List<Exemplaire> exemplairesALivrer,
        Double distanceKm
    ) {
        this.id = id;
        this.dateLivraison = dateLivraison;
        this.vehicule = vehicule;
        this.annexeOrigine = annexeOrigine;
        this.annexeDestination = annexeDestination;
        this.exemplairesALivrer = exemplairesALivrer;
        this.distanceKm = distanceKm;
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

    public Double getDistanceKm() {
        return this.distanceKm;
    }

    public void setDistanceKm(Double distanceKm) {
        this.distanceKm = distanceKm;
    }

    public void effectuer() throws IllegalStateException {
        for (Exemplaire exemplaireALivrer : exemplairesALivrer) {
            if (exemplaireALivrer.getAnnexe() != this.annexeOrigine) {
                // Incohérence
                throw new IllegalStateException(
                    "L'exemplaire " +
                        exemplaireALivrer.getId() +
                        " ne se trouve pas à l'annexe d'origine " +
                        this.annexeOrigine.getNom()
                );
            }
        }

        if (
            this.vehicule.estDisponible() &&
            this.vehicule.getCapacite() >= exemplairesALivrer.size()
        ) {
            for (Exemplaire exemplaireALivrer : exemplairesALivrer) {
                exemplaireALivrer.setAnnexe(annexeDestination);
            }
            vehicule.setKilometrage(
                vehicule.getKilometrage() + this.getDistanceKm()
            );
        } else {
            // Incohérence
            throw new IllegalStateException(
                "Le véhicule n'est pas disponible ou sa capacité est insuffisante"
            );
        }
    }
}
