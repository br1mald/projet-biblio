package src.bibliotheque.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Membre {

    private int id;
    private String nom;
    private String prenom;
    private String adresse;
    private LocalDate dateInscription;
    private ArrayList<Emprunt> emprunts;

    public Membre() {
        this.emprunts = new ArrayList<>();
    }

    public Membre(
        int id,
        String nom,
        String prenom,
        String adresse,
        LocalDate dateInscription,
        ArrayList<Emprunt> emprunts
    ) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.dateInscription = dateInscription;
        this.emprunts = emprunts;
    }

    public int getId() {
        return this.id;
    }

    public String getNom() {
        return this.nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public LocalDate getDateInscription() {
        return this.dateInscription;
    }

    public ArrayList<Emprunt> getEmprunts() {
        return this.emprunts;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setDateInscription(LocalDate dateInscription) {
        this.dateInscription = dateInscription;
    }

    public void setEmprunts(ArrayList<Emprunt> emprunts) {
        this.emprunts = emprunts;
    }

    public Emprunt emprunter(Exemplaire ex, int idEmprunt, int dureeJours) {
        Emprunt e = new Emprunt(idEmprunt, this, ex, dureeJours);
        this.emprunts.add(e);
        return e;
    }

    public void retourner(Emprunt emprunt) {
        if (emprunt != null) {
            this.emprunts.remove(emprunt);
        }
    }

    public ArrayList<Emprunt> getEmpruntsEnCours() {
        return this.emprunts;
    }
}
