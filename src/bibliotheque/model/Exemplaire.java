package src.bibliotheque.model;

public class Exemplaire {

    public enum EtatExemplaire {
        NEUF,
        BON,
        ABIME,
        HORS_SERVICE,
    }

    // Attributs
    private int id;
    private EtatExemplaire etat;
    private boolean disponible;
    private Livre livre;
    private Annexe annexe;

    // Constructeurs

    // Constructeur sans paramètre
    public Exemplaire() {
        this.disponible = true;
        this.etat = EtatExemplaire.NEUF;
    }

    // Constructeur avec paramètres
    public Exemplaire(int id, EtatExemplaire etat, Livre livre, Annexe annexe) {
        this.id = id;
        this.etat = etat;
        this.livre = livre;
        this.disponible = true;
        // par défaut, un exemplaire créé est disponible
        this.annexe = annexe;
    }

    // Appelée quand un membre emprunte l'exemplaire
    public void marquerEmprunte() {
        if (!this.disponible) {
            System.out.println("Cet exemplaire est déjà emprunté.");
            return;
        }
        this.disponible = false;
        System.out.println("Exemplaire " + this.id + " marqué comme emprunté.");
    }

    // Appelée quand le membre ramène l'exemplaire
    public void marquerRetourne() {
        if (this.disponible) {
            System.out.println("Cet exemplaire n'était pas emprunté.");
            return;
        }
        this.disponible = true;
        System.out.println("Exemplaire " + this.id + " marqué comme retourné.");
    }

    // Getters et Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EtatExemplaire getEtat() {
        return etat;
    }

    public void setEtat(EtatExemplaire etat) {
        this.etat = etat;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public Annexe getAnnexe() {
        return this.annexe;
    }

    public void setAnnexe(Annexe annexe) {
        this.annexe = annexe;
    }

    // Affichage d'un exemplaire
    public String toString() {
        String titrelivre = (livre != null) ? livre.getTitre() : "Non défini";
        String dispo = disponible ? "Oui" : "Non";
        return (
            "Exemplaire [id=" +
            id +
            ", etat=" +
            etat +
            ", disponible=" +
            dispo +
            ", livre=" +
            titrelivre +
            ", annexe=" +
            annexe.getNom() +
            "]"
        );
    }
}
