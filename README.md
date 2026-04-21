# SciLib-DevOps

[![CI](https://github.com/Dany0257/scilib-devops/actions/workflows/ci.yml/badge.svg)](https://github.com/Dany0257/scilib-devops/actions/workflows/ci.yml)
![Coverage](https://img.shields.io/badge/coverage-Jacoco-blue)
[![Quality Gate Status](https://im2ag-sonar.univ-grenoble-alpes.fr/api/project_badges/measure?project=fr.uga.im2ag%3Ascilib-devops&metric=alert_status)](https://im2ag-sonar.univ-grenoble-alpes.fr/dashboard?id=fr.uga.im2ag%3Ascilib-devops)

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
- **Affichage** : Formatage textuel optimisé pour la lecture dans la console (style NumPy).

## Utilisation

```java
import fr.uga.im2ag.scilib.core.NdarrayInterface;
import fr.uga.im2ag.scilib.core.NdarrayFactory;

// Création d'une matrice 2x3 remplie de zéros
NdarrayInterface a = NdarrayFactory.zeros(2, 3);

// Création d'un vecteur et reshape en matrice
NdarrayInterface b = NdarrayFactory.arange(0, 6, 1).reshape(2, 3);

// Addition
NdarrayInterface result = a.add(b);
System.out.println(result);
```

## Outils utilisés

Pour assurer la qualité du code et le respect des principes DevOps, nous utilisons :
- **Maven** : Pour la gestion des dépendances et l'automatisation du cycle de vie du projet (compilation, tests, package).
- **JUnit 5** : Pour les tests unitaires (55 tests couvrant le cœur de la bibliothèque).
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
L'image est construite via un `Dockerfile` multi-stage pour garantir un poids minimal, puis elle est poussée automatiquement par notre CI vers le registre **GitHub Container Registry (GHCR)**.

🔗 **Lien vers le dépôt d'images** : [ghcr.io/Dany0257/scilib-devops](https://github.com/Dany0257/scilib-devops/pkgs/container/scilib-devops)

Vous pouvez tester l'image locale ou distante en tapant :
```bash
docker run --rm ghcr.io/dany0257/scilib-devops:latest
```

## Feedback

Le projet nous a permis de mettre en pratique les concepts de DevOps dans un environnement collaboratif réel. L'automatisation des tests via GitHub Actions nous a fait gagner un temps précieux en garantissant que les nouvelles modifications ne cassaient pas l'existant. La configuration de JaCoCo nous a poussés à maintenir un haut niveau de qualité et de couverture de code.

---
*Projet réalisé dans le cadre du module DevOps - Master 1 Informatique - UFR IM2AG.*