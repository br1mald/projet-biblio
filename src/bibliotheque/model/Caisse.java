package src.bibliotheque.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Caisse {

    private int id;
    private double solde;
    private List<Amende> amendesEncaissees;

    public Caisse() {}

    public Caisse(int id) {
        this.id = id;
        this.solde = 0;
        this.amendesEncaissees = new ArrayList<>();
    }

    public void encaisserAmende(Amende amende) {
        if (amende == null) {
            throw new IllegalArgumentException("Amende invalide (null).");
        }

        if (amende.estPayee()) {
            throw new IllegalStateException(
                "Cette amende a déjà été payée et encaissée !"
            );
        }

        amende.payer();
        solde += amende.getMontant();
        amendesEncaissees.add(amende);
    }

    public int getId() {
        return id;
    }

    public double getSolde() {
        return solde;
    }

    public List<Amende> getAmendesEncaissees() {
        return Collections.unmodifiableList(amendesEncaissees);
    }

    public void setId(int id) {
        this.id = id;
    }
   
    public void setSolde(double solde) {
    this.solde = solde;
}

    @Override
    public String toString() {
        return (
            "Caisse{id=" +
            id +
            ", solde=" +
            solde +
            " FCFA" +
            ", amendesEncaissees=" +
            amendesEncaissees.size() +
            "}"
        );
    }
}
