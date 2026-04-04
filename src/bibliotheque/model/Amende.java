package src.bibliotheque.model;

import java.time.LocalDate;

public class Amende {

    private int id;
    private double montant;
    private boolean payee;
    private LocalDate dateCreation;
    private Emprunt emprunt;

    private static final double TARIF_PAR_JOUR = 500;

    public Amende() {}

    public Amende(int id, Emprunt emprunt) {
        int joursRetard = emprunt.calculerRetardJours();
        if (joursRetard <= 0) {
            throw new IllegalArgumentException(
                "Impossible de créer une amende : aucun retard constaté."
            );
        }

        this.id = id;
        this.emprunt = emprunt;
        this.dateCreation = LocalDate.now();
        this.montant = calculerMontant(joursRetard);
        this.payee = false;
    }

    private double calculerMontant(int joursRetard) {
        return joursRetard * TARIF_PAR_JOUR;
    }

    public void payer() {
        if (payee) {
            throw new IllegalStateException("Amende déjà payée !");
        }
        this.payee = true;
    }

    public int getId() {
        return id;
    }

    public double getMontant() {
        return montant;
    }

    public boolean estPayee() {
        return payee;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public Emprunt getEmprunt() {
        return emprunt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public void setPayee(boolean payee) {
        this.payee = payee;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setEmprunt(Emprunt emprunt) {
        this.emprunt = emprunt;
    }

    @Override
    public String toString() {
        return (
            "Amende{id=" +
            id +
            ", montant=" +
            montant +
            " FCFA" +
            ", payee=" +
            payee +
            ", dateCreation=" +
            dateCreation +
            "}"
        );
    }
}
