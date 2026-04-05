package src.bibliotheque.model;

import java.util.List;

public class Annexe {

    private int id;
    private String nom;
    private List<Exemplaire> exemplaires;

    public Annexe() {}

    public Annexe(int id, String nom, List<Exemplaire> exemplaires) {
        this.id = id;
        this.nom = nom;
        this.exemplaires = exemplaires;
    }

    public Annexe(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Exemplaire> getExemplaires() {
        return this.exemplaires;
    }

    public void setExemplaires(List<Exemplaire> exemplaires) {
        this.exemplaires = exemplaires;
    }
}
