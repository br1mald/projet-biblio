-- =========================================================
-- SCRIPT DE CRÉATION DE LA BASE DE DONNÉES DE LA BIBLIOTHÈQUE
-- =========================================================


CREATE DATABASE IF NOT EXISTS bibliotheque;
USE bibliotheque;

-- Suppression des tables existantes pour repartir à zéro (ordre inversé des dépendances)
DROP TABLE IF EXISTS livraison_exemplaire;
DROP TABLE IF EXISTS livraison;
DROP TABLE IF EXISTS amende;
DROP TABLE IF EXISTS emprunt;
DROP TABLE IF EXISTS exemplaire;
DROP TABLE IF EXISTS caisse;
DROP TABLE IF EXISTS annexe;
DROP TABLE IF EXISTS vehicule;
DROP TABLE IF EXISTS membre;
DROP TABLE IF EXISTS livre;

-- =========================================================
-- 1. CRÉATION DES TABLES INDÉPENDANTES
-- =========================================================

CREATE TABLE livre (
    isbn VARCHAR(50) PRIMARY KEY,
    titre VARCHAR(255) NOT NULL,
    auteur VARCHAR(255) NOT NULL,
    genre VARCHAR(100),
    annee_publication INT
);

CREATE TABLE membre (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    adresse VARCHAR(255),
    date_inscription DATE NOT NULL
);

CREATE TABLE vehicule (
    immatriculation VARCHAR(20) PRIMARY KEY,
    capacite INT NOT NULL,
    disponible BOOLEAN DEFAULT TRUE,
    kilometrage DOUBLE NOT NULL
);

CREATE TABLE annexe (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL
);

CREATE TABLE caisse (
    id INT AUTO_INCREMENT PRIMARY KEY,
    solde DOUBLE DEFAULT 0.0
);

-- =========================================================
-- 2. CRÉATION DES TABLES DÉPENDANTES
-- =========================================================

CREATE TABLE exemplaire (
    id INT AUTO_INCREMENT PRIMARY KEY,
    etat VARCHAR(50) NOT NULL,
    disponible BOOLEAN DEFAULT TRUE,
    livre_isbn VARCHAR(50) NOT NULL,
    annexe_id INT, -- Rendu nullable (SET NULL) si l'annexe ferme ou est supprimée
    FOREIGN KEY (livre_isbn) REFERENCES livre(isbn) ON DELETE CASCADE,
    FOREIGN KEY (annexe_id) REFERENCES annexe(id) ON DELETE SET NULL
);

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

CREATE TABLE amende (
    id INT AUTO_INCREMENT PRIMARY KEY,
    montant DOUBLE NOT NULL,
    payee BOOLEAN DEFAULT FALSE,
    date_creation DATE NOT NULL,
    emprunt_id INT NOT NULL,
    caisse_id INT DEFAULT NULL, -- NULL si l'amende n'a pas encore été encaissée
    FOREIGN KEY (emprunt_id) REFERENCES emprunt(id) ON DELETE CASCADE,
    FOREIGN KEY (caisse_id) REFERENCES caisse(id) ON DELETE SET NULL
);

CREATE TABLE livraison (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date_livraison DATE NOT NULL,
    distance_km DOUBLE NOT NULL,
    vehicule_immatriculation VARCHAR(20) NOT NULL,
    annexe_origine_id INT NOT NULL,
    annexe_destination_id INT NOT NULL,
    FOREIGN KEY (vehicule_immatriculation) REFERENCES vehicule(immatriculation) ON DELETE CASCADE,
    FOREIGN KEY (annexe_origine_id) REFERENCES annexe(id) ON DELETE CASCADE,
    FOREIGN KEY (annexe_destination_id) REFERENCES annexe(id) ON DELETE CASCADE
);

-- Table d'association entre Livraison et Exemplaire
CREATE TABLE livraison_exemplaire (
    livraison_id INT NOT NULL,
    exemplaire_id INT NOT NULL,
    PRIMARY KEY (livraison_id, exemplaire_id),
    FOREIGN KEY (livraison_id) REFERENCES livraison(id) ON DELETE CASCADE,
    FOREIGN KEY (exemplaire_id) REFERENCES exemplaire(id) ON DELETE CASCADE
);

-- =========================================================
-- 3. INSERTION DES DONNÉES INITIALES
-- =========================================================

-- Création de la caisse principale de la bibliothèque
INSERT INTO caisse (id, solde) VALUES (1, 0.0);
