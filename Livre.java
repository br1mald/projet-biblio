public class Livre {

    private String isbn;
    private String titre;
    private String auteur;
    private String genre;
    private int anneePublication;
    private int exemplaires;

    public Livre() {}

    public Livre(
        String isbn,
        String titre,
        String auteur,
        String genre,
        int anneePublication,
        int exemplaires
    ) {
        this.isbn = isbn;
        this.titre = titre;
        this.auteur = auteur;
        this.genre = genre;
        this.anneePublication = anneePublication;
        this.exemplaires = exemplaires;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getTitre() {
        return this.titre;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getAuteur() {
        return this.auteur;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return this.genre;
    }

    public void setAnneePublication(int anneePublication) {
        this.anneePublication = anneePublication;
    }

    public int getAnneePublication() {
        return this.anneePublication;
    }

    public void setExemplaires(int exemplaires) {
        this.exemplaires = exemplaires;
    }

    public int getExemplaires() {
        return this.exemplaires;
    }
}
