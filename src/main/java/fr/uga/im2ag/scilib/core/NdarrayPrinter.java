package fr.uga.im2ag.scilib.core;

/**
 * Classe utilitaire dédiée au formatage et à l'affichage dans le terminal.
 */
public final class NdarrayPrinter {

    // Constructeur privé pour bloquer l'instanciation
    private NdarrayPrinter() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Formate dynamiquement un Ndarray selon son nombre de dimensions.
     *
     * @param ndarray Le tableau à analyser et formater
     * @return Le texte prêt à être affiché dans la console
     */
    public static String format(NdarrayInterface ndarray) {
        int ndim = ndarray.getNdim();

        // Routage de l'affichage en fonction du nombre de dimensions
        if (ndim == 1) {
            return format1D(ndarray);
        } else if (ndim == 2) {
            return format2D(ndarray);
        }
        
        // Si on essaie d'afficher de la 3D ou plus,
        // on renvoie une représentation basique de la forme (Shape) au lieu de planter.
        return "Ndarray(shape=" + ndarray.getShape() + ")";
    }

    /**
     * Gère l'affichage d'un vecteur (1 Dimension).
     * Exemple de sortie : [1.0, 2.0, 3.0]
     * 
     * @param ndarray Le tableau 1D à formater.
     * @return La représentation textuelle du vecteur encadrée par des crochets.
     */
    private static String format1D(NdarrayInterface ndarray) {
        int size = ndarray.getSize();
        
        // On utilise StringBuilder plutôt qu'une concaténation classique (String + String)
        // car c'est beaucoup plus performant en termes de gestion de la mémoire.
        StringBuilder sb = new StringBuilder("[");
        
        for (int i = 0; i < size; i++) {
            // Utilisation de la méthode get() publique pour accéder aux données proprement
            sb.append(formatValue(ndarray.get(i)));
            
            // On ajoute une virgule partout sauf après le tout dernier élément
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Gère l'affichage d'une matrice (2 Dimensions).
     * Formate avec des retours à la ligne pour recréer une grille visuelle.
     * 
     * @param ndarray Le tableau 2D (matrice) à formater.
     * @return La représentation textuelle de la matrice sous forme de grille avec retours à * la ligne.
     */
    private static String format2D(NdarrayInterface ndarray) {
        // Extraction du nombre de lignes (index 0) et colonnes (index 1) depuis la Shape
        int rows = ndarray.getShape().getDim(0);
        int cols = ndarray.getShape().getDim(1);
        
        StringBuilder sb = new StringBuilder("[");
        
        // Boucle externe : itération sur chaque ligne de la matrice
        for (int r = 0; r < rows; r++) {
            // Ajoute un espace au début des lignes (sauf la première)
            if (r > 0) {
                sb.append(" ");
            }
            
            sb.append("[");
            // Boucle interne : itération sur chaque colonne de la ligne actuelle
            for (int c = 0; c < cols; c++) {
                sb.append(formatValue(ndarray.get(r, c)));
                if (c < cols - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]");
            
            // Retour à la ligne après chaque fin de ligne matricielle (sauf la dernière)
            if (r < rows - 1) {
                sb.append("\n");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Nettoie l'affichage des nombres flottants pour coller au style NumPy.
     * Exemple : Affiche "5.0" au lieu de "5" pour un entier.
     * 
     * @param value La valeur brute de type double extraite du tableau.
     * @return La valeur formatée sous forme de chaîne de caractères (String), avec ".0"
     * garanti pour les entiers.
     */
    private static String formatValue(double value) {
        // Si le double est en fait un nombre entier (ex: 5.0 == 5)
        if (value == (long) value) {
            // On le force à s'afficher avec le ".0" à la fin
            return String.valueOf((long) value) + ".0";
        }
        // Sinon, on laisse Java afficher les décimales normales (ex: 3.14)
        return String.valueOf(value);
    }
}