# Système de Gestion d'une Bibliothèque Municipale (Projet Java)

## Présentation
Ce projet est une application Java orientée objet (avec persistance en base de données MySQL) conçue pour modéliser et informatiser la gestion d'une bibliothèque municipale et de ses annexes.

Il répond aux exigences suivantes :
- Gestion du catalogue de livres et des exemplaires physiques.
- Gestion des membres et de leurs emprunts.
- Suivi des retards et traitement des amendes via une caisse centrale.
- Logistique des livraisons (transfert d'ouvrages entre annexes via un véhicule).
- Suivi strict des conditions de conservation (état des exemplaires).

## Architecture du Projet
Le projet suit une architecture en couches :
- **`model`** : Contient les classes métiers (`Livre`, `Membre`, `Livraison`, etc.) respectant strictement l'encapsulation.
- **`dao`** : Contient les classes d'accès aux données (Data Access Objects) gérant la persistance via JDBC (`LivreDAO`, `EmpruntDAO`, etc.).
- **`Main.java`** : Contient le scénario principal simulant une utilisation réaliste du système.

## ⚙️ Prérequis
- **Java** (JDK 8 ou supérieur)
- **MySQL** (Serveur local comme MAMP, XAMPP, ou WAMP)
- Le driver JDBC MySQL (`mysql-connector-j.jar`) situé dans le dossier `lib/`.

## 🚀 Installation et Exécution

**1. Configuration de la base de données**
- Ouvrez votre client MySQL (ex: phpMyAdmin).
- Créez une base de données nommée `bibliotheque`.
- Exécutez l'intégralité du script SQL fourni : `script_sql.sql`. *(Ce script créera toutes les tables nécessaires et insérera la caisse initiale).*

**2. Configuration de la connexion (Optionnel)**
- Si vos identifiants MySQL sont différents de `root` / `root`, ou si votre port n'est pas `8889`, modifiez les constantes dans le fichier `src/bibliotheque/dao/ConnexionBD.java`.

**3. Compilation et Lancement (en ligne de commande)**
Placez-vous à la racine du projet et exécutez les commandes suivantes :

```bash
# Compiler le projet dans le dossier bin/
javac -d bin -cp "lib/*" $(find src -name "*.java")

# Lancer le scénario principal
java -cp "bin:lib/*" src.bibliotheque.Main
