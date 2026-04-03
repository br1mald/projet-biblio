import java.time.LocalDate;
import java.util.ArrayList;

public class Membre {
    private int id;
    private String nom;
    private String prenom;
    private String adresse;
    private LocalDate dateInscription;
    private ArrayList<Emprunt> emprunts;

public Membre() {}

public Membre(int id, String nom, String prenom, String adresse, LocalDate dateInscription, ArrayList<Emprunt> emprunts
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

public int setId(int id) {
    return this.id;
}
public String setNom(String nom) {
    return this.nom;
}
public String setPrenom(String prenom) {
    return this.prenom;
}
public String setAdresse(String adresse) {
    return this.adresse;
}
public LocalDate setDateInscription(LocalDate dateInscription) {
    return this.dateInscription;
}
public ArrayList<Emprunt> setEmprunts(ArrayList<Emprunt> emprunts) {
    return this.emprunts;
}

public Emprunt emprunter(Exemplaire ex){
    // Pour l'instant, on crée un objet vide pour ne pas bloquer la compilation
    // en attente de la classe Emprunt
    Emprunt e = new Emprunt();
    this.emprunts.add(e);
    return e;
}

public void retourner(Emprunt emprunt){
    if (emprunt != null) {
        this.emprunts.remove(emprunt);
    }
}

public ArrayList<Emprunt> getEmpruntsEnCours(){
    return this.emprunts;
}
}