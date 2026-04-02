# Gestion des Étudiants – Application Android avec SQLite

Application mobile Android de gestion d’étudiants utilisant une base de données SQLite locale.  
Elle permet d’**ajouter**, **rechercher**, **modifier** et **supprimer** des étudiants, avec une interface moderne (Material Design) et une liste dynamique (RecyclerView).

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![SQLite](https://img.shields.io/badge/SQLite-003B57?style=for-the-badge&logo=sqlite&logoColor=white)
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)

---

## ✨ Fonctionnalités

-  Ajouter un étudiant (nom, prénom)
-  Rechercher un étudiant par son ID
- Supprimer un étudiant (depuis l’accueil ou la liste)
-  Afficher la liste complète des étudiants
-  Modifier un étudiant (dialog pré‑rempli)
-  Interface Material Design (cards, couleurs personnalisées, animations)
-  RecyclerView avec avatar (initiales) et boutons d’action

---

##  Architecture technique

- **Langage** : Java
- **Base de données** : SQLite (locale)
- **Helper** : `MySQLiteHelper` (création / mise à jour de la table)
- **Service** : `EtudiantService` (CRUD complet)
- **Modèle** : `Etudiant` (id, nom, prénom)
- **UI** : `MainActivity` (ajout / recherche / suppression) + `ListeActivity` (affichage liste + modification)
- **Adaptateur** : `EtudiantAdapter` (RecyclerView)
- **Design** : Material Components, `CardView`, `TextInputLayout`

---

## demo

> *Ajoutez ici des captures d’écran de votre application (écran principal, liste, dialog de modification).*

---

