# ITSupport — Gestion des incidents informatiques

## 1. Présentation du projet

ITSupport est un projet pédagogique réalisé dans le cadre du TP1 d'Architecture SOA et services web.

L'objectif est de modéliser un système de gestion des incidents informatiques en XML, de définir un schéma XSD pour contrôler la structure des données et d'utiliser Java avec Maven pour valider le document XML et interroger ses informations avec XPath.

## 2. Problématique

Dans une entreprise, les incidents informatiques doivent être enregistrés et suivis afin de faciliter leur prise en charge par les techniciens.

Le projet représente plusieurs types d'incidents, notamment les problèmes de réseau, de matériel et de serveur.

## 3. Modélisation des données

Le document XML contient trois catégories principales :

- **Utilisateurs :** identifiant, nom et service.
- **Techniciens :** identifiant, nom et spécialité.
- **Incidents :** identifiant, titre, description, catégorie, priorité, statut, identifiant de l'utilisateur concerné, identifiant du technicien responsable et date de création.

Les identifiants permettent de relier les incidents aux utilisateurs et aux techniciens.

## 4. Structure du projet

- `pom.xml` : configuration du projet Maven.
- `src/main/java/Main.java` : validation XML/XSD et interrogation XPath.
- `src/main/resources/incidents.xml` : données fictives du système.
- `src/main/resources/incidents.xsd` : schéma de validation.
- `README.md` : documentation du projet.

## 5. Validation XML/XSD

Le schéma XSD définit les éléments obligatoires, l'ordre des champs, les types de données, les priorités autorisées, les statuts possibles et les références aux identifiants.

La classe Java utilise `SchemaFactory` pour charger le schéma, puis `Validator` pour vérifier le document XML.

## 6. Requêtes XPath

Exemples de requêtes utilisées :

- Tous les titres : `/gestionIncidents/incidents/incident/titre`
- Incidents critiques : `/gestionIncidents/incidents/incident[priorite='Critique']/titre`
- Incidents ouverts : `/gestionIncidents/incidents/incident[statut='Ouvert']/titre`
- Incidents d'un technicien : `/gestionIncidents/incidents/incident[technicienId='T002']/titre`
- Incidents de réseau : `/gestionIncidents/incidents/incident[categorie='Reseau']/titre`

Ces requêtes permettent d'extraire des informations ciblées sans parcourir manuellement toutes les données.

## 7. Tests

Le projet doit être testé avec un document XML valide, puis avec des valeurs incorrectes, comme une priorité non autorisée ou une date invalide.

Les résultats obtenus dans la console permettent de vérifier le fonctionnement de la validation et des requêtes XPath.

## 8. Conclusion

Ce projet met en pratique la modélisation XML, la définition d'un schéma XSD, la validation avec les API XML de Java et l'interrogation des données avec XPath.

Il constitue une première étape vers un système plus complet de gestion des incidents informatiques.