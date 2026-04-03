public class Vehicule {

    // Attributs 
    private String immatriculation;
    private int capacite;
    private boolean disponible;
    private double kilometrage;

    // Constructeurs 

    // Constructeur sans paramètre
    public Vehicule() {
        this.disponible = true;
        this.kilometrage = 0;
    }

    // Constructeur avec paramètres
    public Vehicule(String immatriculation, int capacite, double kilometrage) {
        this.immatriculation = immatriculation;
        this.capacite = capacite;
        this.kilometrage = kilometrage;
        this.disponible = true; // un véhicule tout juste créé est disponible
    }

    // Méthodes métier

    // Retourne true si le véhicule est disponible, false sinon
    public boolean estDisponible() {
        return this.disponible;
    }

    // Getters et Setters 

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        if (capacite <= 0) {
            System.out.println("La capacité doit être supérieure à 0.");
            return;
        }
        this.capacite = capacite;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public double getKilometrage() {
        return kilometrage;
    }

    public void setKilometrage(double kilometrage) {
        if (kilometrage < 0) {
            System.out.println("Le kilométrage ne peut pas être négatif.");
            return;
        }
        this.kilometrage = kilometrage;
    }

    // Affichage d'un véhicule
    public String toString() {
        String dispo = disponible ? "Oui" : "Non";
        return "Vehicule [immatriculation=" + immatriculation + ", capacite=" + capacite
                + ", disponible=" + dispo + ", kilometrage=" + kilometrage + " km]";
    }
}
