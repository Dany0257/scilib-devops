# SciLib-DevOps

[![CI](https://github.com/Dany0257/scilib-devops/actions/workflows/ci.yml/badge.svg)](https://github.com/Dany0257/scilib-devops/actions/workflows/ci.yml)
![Coverage](https://img.shields.io/badge/coverage-Jacoco-blue)
[![Quality Gate Status](https://im2ag-sonar.univ-grenoble-alpes.fr/api/project_badges/measure?project=M1-DEVOPS&metric=alert_status&token=sqb_2baf99df0e09b55a97cbe15c808200e6804f4e11)](https://im2ag-sonar.univ-grenoble-alpes.fr/dashboard?id=M1-DEVOPS)

Bibliothèque de calcul scientifique en Java, inspirée de NumPy (M1 INFO DevOps - 2026).

## Fonctionnalités

La bibliothèque `scilib-devops` permet de manipuler des tableaux multidimensionnels (`Ndarray`) avec une interface proche de celle de NumPy :

- **Support Ndarray** : Manipulation de tableaux en 1D (vecteurs) et 2D (matrices) de type `double`.
- **Attributs de base** : Accès à la forme (`shape`), au nombre de dimensions (`ndim`) et au nombre total d'éléments (`size`).
- **Création de tableaux** :
  - `NdarrayFactory.zeros(dims...)` : Crée un tableau rempli de zéros.
  - `NdarrayFactory.ones(dims...)` : Crée un tableau rempli de uns.
  - `NdarrayFactory.arange(start, stop, step)` : Crée une séquence de nombres.
  - `NdarrayFactory.array(double[])` : Crée un tableau à partir de données existantes.
- **Opérations Arithmétiques** :
  - Addition élément-par-élément (`add`).
  - Addition en place (`addInPlace` ou `+=`).
  - Support de l'addition de scalaires.
- **Transformation** :
  - `reshape(newDims...)` pour changer la structure du tableau sans modifier les données.
- **Fonctions Universelles (UFuncs)** : Support de fonctions mathématiques (`sqrt`, `exp`, `log`, `sin`, `cos`, etc.) en versions standard et en-place.
- **Broadcasting** : Support complet des règles de diffusion NumPy pour les opérations binaires entre tableaux de dimensions différentes.
- **Affichage** : Formatage textuel optimisé pour la lecture dans la console (style NumPy).

## Documentation

La Javadoc du projet est générée automatiquement et hébergée sur GitHub Pages :
**[Lien vers la Javadoc](https://dany0257.github.io/scilib-devops/)**

## Guide de démarrage rapide

Pour cloner et tester le projet immédiatement :

```bash
# 1. Cloner le dépôt
git clone https://github.com/Dany0257/scilib-devops.git
cd scilib-devops

# 2. Compiler et lancer les tests
mvn clean verify

# 3. Lancer l'application de démonstration
mvn exec:java -Dexec.mainClass="fr.uga.im2ag.App"
```

## Utilisation

```java
import fr.uga.im2ag.scilib.core.NdarrayInterface;
import fr.uga.im2ag.scilib.core.NdarrayFactory;
import fr.uga.im2ag.scilib.core.UFuncs;
import fr.uga.im2ag.scilib.core.Broadcasting;

// Création d'une matrice 2x3
NdarrayInterface a = NdarrayFactory.arange(0, 6, 1).reshape(2, 3);

// Fonctions universelles (ex: racine carrée élément par élément)
NdarrayInterface s = UFuncs.sqrt(a);

// Broadcasting (Addition d'une ligne à toute la matrice)
NdarrayInterface v = NdarrayFactory.array(new double[]{10, 20, 30});
NdarrayInterface res = Broadcasting.add(a, v);

System.out.println(res);
```

## Outils utilisés

Pour assurer la qualité du code et le respect des principes DevOps, nous utilisons :
- **Maven** : Pour la gestion des dépendances et l'automatisation du cycle de vie du projet (compilation, tests, package).
- **JUnit 5** : Pour les tests unitaires (91 tests couvrant l'intégralité de la bibliothèque).
- **JaCoCo** : Intégré à Maven pour mesurer la couverture de code par les tests.
- **GitHub Actions** : Orchestration de l'intégration continue (CI) à chaque push ou Pull Request.
- **SonarQube** : Analyse statique du code (Quality Gate) effectuée automatiquement dans le pipeline CI.

## Workflow Git

Nous avons adopté un workflow collaboratif basé sur des **feature branches** :
- La branche `main` contient la version stable.
- La branche `develop` sert de base pour l'intégration des nouvelles fonctionnalités.
- Toute nouvelle modification passe par une branche dédiée (ex: `feature/ndarray-core`) et fait l'objet d'une **Pull Request** avant d'être fusionnée dans `develop`.
- Nous avons mis en place des **protections de branches** sur `main` et `develop` pour interdire les push directs et forcer le passage par des Pull Requests.

## Livraison Continue (Docker)

Conformément aux attentes DevOps, nous générons une image Docker "prête à l'emploi" qui contient et exécute automatiquement notre application de démonstration.
L'image est construite via un `Dockerfile` multi-stage (distribué via **Eclipse Temurin 17**) pour garantir un poids minimal, puis elle est poussée automatiquement par notre CI vers le registre **GitHub Container Registry (GHCR)**.

**Lien vers le registre** : [ghcr.io/Dany0257/scilib-devops](https://github.com/Dany0257/scilib-devops/pkgs/container/scilib-devops)

Pour tester la bibliothèque immédiatement sans rien installer :
```bash
docker run --rm ghcr.io/dany0257/scilib-devops:latest
```

## Infrastructure-as-Code (Terraform & Ansible)

Pour l'étape avancée du projet, nous avons automatisé le déploiement de la bibliothèque sur le Cloud (**Google Cloud Platform**) via une approche IaC :

1.  **Terraform** : Provisionne automatiquement une machine virtuelle `e2-micro` sur GCP avec les règles de pare-feu SSH appropriées.
2.  **Ansible** : Configure la machine distante (installation de Docker et des dépendances Python) et déploie le conteneur de la bibliothèque pour un test d'intégration grandeur nature.

Les fichiers de configuration se trouvent dans le dossier `/infra`.

## Architecture Technique

Le cœur de la bibliothèque repose sur une structure de données **Row-Major** :
- Les données multi-dimensionnelles sont stockées dans un unique tableau de `double[]` à plat pour optimiser les performances mémoire.
- La classe `Shape` gère la correspondance entre les indices multi-dimensionnels et l'index 1D physique (sécurisé par clonage défensif).
- L'architecture est modulaire : les opérations avancées (**Broadcasting**, **UFuncs**) sont séparées du cœur (`Ndarray`) pour faciliter la maintenance et l'évolution.

## Feedback

Le projet nous a permis de mettre en pratique les concepts de DevOps dans un environnement collaboratif réel. L'automatisation des tests via GitHub Actions nous a fait gagner un temps précieux en garantissant que les nouvelles modifications ne cassaient pas l'existant. La configuration de SonarQube et JaCoCo nous a poussés à maintenir un niveau d'excellence technique constant tout au long du développement.

---
*Projet réalisé dans le cadre du module DevOps - Master 1 Informatique - UFR IM2AG.*