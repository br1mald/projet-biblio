-- =========================================================
-- SCRIPT DE CRÉATION DE LA BASE DE DONNÉES DE LA BIBLIOTHÈQUE
-- =========================================================

-- 1. Création de la table Livre (Indépendante)
CREATE TABLE livre (
    isbn VARCHAR(50) PRIMARY KEY,
    titre VARCHAR(255) NOT NULL,
    auteur VARCHAR(255) NOT NULL,
    genre VARCHAR(100),
    annee_publication INT
);

-- 2. Création de la table Membre (Indépendante)
CREATE TABLE membre (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    adresse VARCHAR(255),
    date_inscription DATE NOT NULL
);

-- 3. Création de la table Vehicule (Indépendante)
CREATE TABLE vehicule (
    immatriculation VARCHAR(20) PRIMARY KEY,
    capacite INT NOT NULL,
    disponible BOOLEAN DEFAULT TRUE,
    kilometrage DOUBLE NOT NULL
);

-- 4. Création de la table Annexe (Indépendante)
CREATE TABLE annexe (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL
);

-- 5. Création de la table Caisse (Indépendante)
CREATE TABLE caisse (
    id INT AUTO_INCREMENT PRIMARY KEY,
    solde DOUBLE DEFAULT 0.0
);

-- 6. Création de la table Exemplaire (Dépend de Livre)
CREATE TABLE exemplaire (
    id INT AUTO_INCREMENT PRIMARY KEY,
    etat VARCHAR(50) NOT NULL, -- ex: 'NEUF', 'ABIME'
    disponible BOOLEAN DEFAULT TRUE,
    livre_isbn VARCHAR(50) NOT NULL,
    FOREIGN KEY (livre_isbn) REFERENCES livre(isbn) ON DELETE CASCADE
);

-- 7. Création de la table Emprunt (Dépend de Membre et Exemplaire)
CREATE TABLE emprunt (
    id INT AUTO_INCREMENT PRIMARY KEY,
    membre_id INT NOT NULL,
    exemplaire_id INT NOT NULL,
    date_emprunt DATE NOT NULL,
    date_retour_prevue DATE NOT NULL,
    date_retour_effective DATE DEFAULT NULL,
    FOREIGN KEY (membre_id) REFERENCES membre(id) ON DELETE CASCADE,
    FOREIGN KEY (exemplaire_id) REFERENCES exemplaire(id) ON DELETE CASCADE
);

-- 8. Création de la table Amende (Dépend de Emprunt et potentiellement de Caisse)
CREATE TABLE amende (
    id INT AUTO_INCREMENT PRIMARY KEY,
    montant DOUBLE NOT NULL,
    payee BOOLEAN DEFAULT FALSE,
    date_creation DATE NOT NULL,
    emprunt_id INT NOT NULL,
    caisse_id INT DEFAULT NULL, -- NULL si l'amende n'a pas encore été rattachée/encaissée par une caisse
    FOREIGN KEY (emprunt_id) REFERENCES emprunt(id) ON DELETE CASCADE,
    FOREIGN KEY (caisse_id) REFERENCES caisse(id) ON DELETE SET NULL
);

-- 9. Création de la table Livraison (Dépend de Vehicule et Annexe)
CREATE TABLE livraison (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date_livraison DATE NOT NULL,
    vehicule_immatriculation VARCHAR(20) NOT NULL,
    annexe_origine_id INT NOT NULL,
    annexe_destination_id INT NOT NULL,
    FOREIGN KEY (vehicule_immatriculation) REFERENCES vehicule(immatriculation) ON DELETE CASCADE,
    FOREIGN KEY (annexe_origine_id) REFERENCES annexe(id) ON DELETE CASCADE,
    FOREIGN KEY (annexe_destination_id) REFERENCES annexe(id) ON DELETE CASCADE
);
