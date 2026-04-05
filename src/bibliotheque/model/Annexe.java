package src.bibliotheque.model;

import java.util.ArrayList;
import java.util.List;

public class Annexe {

    private int id;
    private String nom;
    private List<Exemplaire> exemplaires;

    public Annexe() {
        this.exemplaires = new ArrayList<>();
    }

    public Annexe(int id, String nom, List<Exemplaire> exemplaires) {
        this.id = id;
        this.nom = nom;
        this.exemplaires = exemplaires;
    }

    public Annexe(int id, String nom) {
        this.id = id;
        this.nom = nom;
        this.exemplaires = new ArrayList<>();
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

    @Override
    public String toString() {
        int nbExemplaire = (this.exemplaires != null)
            ? this.exemplaires.size()
            : 0;
        return (
            "Annexe [id=" +
            id +
            ", Nom='" +
            nom +
            '\'' +
            ", Nombre d'exemplaires=" +
            nbExemplaire +
            "]"
        );
    }
}
