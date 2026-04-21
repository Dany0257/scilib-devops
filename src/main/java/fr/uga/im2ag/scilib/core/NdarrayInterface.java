package fr.uga.im2ag.scilib.core;

/**
 * Contrat public pour un Ndarray.
 * Définit toutes les opérations qu'un Ndarray doit supporter.
 *
 * Pourquoi une interface ?
 * - Sépare le contrat (ce que ça fait) de l'implémentation (comment ça le
 * fait).
 * - Permet d'ajouter facilement d'autres implémentations (ex: NdarrayInt)
 * sans modifier le code existant.
 */
public interface NdarrayInterface {

    // --- Attributs ---

    /** Retourne la forme (dimensions) du ndarray. */
    Shape getShape();

    /** Retourne le nombre de dimensions. */
    int getNdim();

    /** Retourne le nombre total d'éléments. */
    int getSize();

    // --- Accès aux données ---

    /**
     * Retourne une copie des données internes (tableau 1D à plat).
     * 
     * @return une copie du tableau de doubles.
     */
    double[] getData();

    /**
     * Retourne la valeur à la position donnée.
     *
     * @param indices les indices (ex: get(1, 2) pour ligne 1, colonne 2)
     * @return la valeur à cette position
     */
    double get(int... indices);

    /**
     * Modifie la valeur à la position donnée.
     *
     * @param value   la nouvelle valeur
     * @param indices les indices de la position
     */
    void set(double value, int... indices);

    // --- Opérations arithmétiques ---

    /**
     * Addition élément par élément. Retourne un NOUVEAU ndarray.
     *
     * @param other le ndarray à additionner
     * @return un nouveau ndarray contenant le résultat
     */
    NdarrayInterface add(NdarrayInterface other);

    /**
     * Addition d'un scalaire à chaque élément. Retourne un NOUVEAU ndarray.
     *
     * @param scalar le scalaire à ajouter
     * @return un nouveau ndarray contenant le résultat
     */
    NdarrayInterface add(double scalar);

    /**
     * Addition en place (+=). Modifie cet objet directement.
     *
     * @param other le ndarray à additionner
     * @return this (pour chaîner les appels)
     */
    NdarrayInterface addInPlace(NdarrayInterface other);

    /**
     * Addition en place d'un scalaire (+=). Modifie cet objet directement.
     *
     * @param scalar le scalaire à ajouter
     * @return this (pour chaîner les appels)
     */
    NdarrayInterface addInPlace(double scalar);

    // --- Transformation ---

    /**
     * Change la forme du ndarray. Retourne un NOUVEAU ndarray.
     * Le nombre total d'éléments doit rester identique.
     *
     * @param newShape les nouvelles dimensions
     * @return un nouveau ndarray avec la nouvelle forme
     */
    NdarrayInterface reshape(int... newShape);
}
