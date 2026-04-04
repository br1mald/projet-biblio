package src.bibliotheque.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Emprunt {

    private int id;
    private Membre membre;
    private Exemplaire exemplaire;
    private LocalDate dateEmprunt;
    private LocalDate dateRetourPrevue;
    private LocalDate dateRetourEffective;

    public Emprunt() {
        this.id = 0;
        this.membre = null;
        this.exemplaire = null;
        this.dateEmprunt = LocalDate.now();
        this.dateRetourPrevue = this.dateEmprunt.plusDays(14);
        this.dateRetourEffective = null;
    }

    public Emprunt(
        int id,
        Membre membre,
        Exemplaire exemplaire,
        int dureeJours
    ) {
        if (!exemplaire.isDisponible()) {
            throw new IllegalStateException("Exemplaire non disponible !");
        }

        this.id = id;
        this.membre = membre;
        this.exemplaire = exemplaire;
        this.dateEmprunt = LocalDate.now();
        this.dateRetourPrevue = dateEmprunt.plusDays(dureeJours);

        exemplaire.marquerEmprunte();
    }

    public boolean estEnRetard() {
        if (dateRetourEffective == null) {
            return LocalDate.now().isAfter(dateRetourPrevue);
        }
        return dateRetourEffective.isAfter(dateRetourPrevue);
    }

    public int calculerRetardJours() {
        if (!estEnRetard()) return 0;

        LocalDate dateReference = (dateRetourEffective != null)
            ? dateRetourEffective
            : LocalDate.now();

        return (int) ChronoUnit.DAYS.between(dateRetourPrevue, dateReference);
    }

    public void effectuerRetour() {
        if (dateRetourEffective != null) {
            throw new IllegalStateException("Retour déjà effectué !");
        }
        this.dateRetourEffective = LocalDate.now();
        exemplaire.marquerRetourne();
    }

    public int getId() {
        return id;
    }

    public Membre getMembre() {
        return membre;
    }

    public Exemplaire getExemplaire() {
        return exemplaire;
    }

    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }

    public LocalDate getDateRetourPrevue() {
        return dateRetourPrevue;
    }

    public LocalDate getDateRetourEffective() {
        return dateRetourEffective;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }

    public void setExemplaire(Exemplaire exemplaire) {
        this.exemplaire = exemplaire;
    }

    public void setDateEmprunt(LocalDate dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public void setDateRetourPrevue(LocalDate dateRetourPrevue) {
        this.dateRetourPrevue = dateRetourPrevue;
    }

    public void setDateRetourEffective(LocalDate dateRetourEffective) {
        this.dateRetourEffective = dateRetourEffective;
    }

    @Override
    public String toString() {
        return (
            "Emprunt{id=" +
            id +
            ", membre=" +
            (membre != null ? membre.getNom() : "null") +
            ", dateEmprunt=" +
            dateEmprunt +
            ", dateRetourPrevue=" +
            dateRetourPrevue +
            ", dateRetourEffective=" +
            (dateRetourEffective != null ? dateRetourEffective : "non rendu") +
            ", enRetard=" +
            estEnRetard() +
            "}"
        );
    }
}
