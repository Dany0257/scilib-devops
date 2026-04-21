package fr.uga.im2ag;

import fr.uga.im2ag.scilib.core.NdarrayFactory;
import fr.uga.im2ag.scilib.core.NdarrayInterface;
import fr.uga.im2ag.scilib.core.Ndarray;
import fr.uga.im2ag.scilib.core.UFuncs;
import fr.uga.im2ag.scilib.core.Broadcasting;

/**
 * Application de Démonstration complète de la bibliothèque Scilib-DevOps
 */
public class App {
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("   DÉMONSTRATION COMPLÈTE - SCI-LIB DEVOPS");
        System.out.println("=================================================\n");

        // --- 1. CRÉATION ---
        System.out.println("1. MÉTHODES DE CRÉATION :");

        System.out.println("- zeros(2, 3) :");
        NdarrayInterface z = NdarrayFactory.zeros(2, 3);
        System.out.println(z);

        System.out.println("\n- ones(3, 3) :");
        NdarrayInterface o = NdarrayFactory.ones(3, 3);
        System.out.println(o);

        System.out.println("\n- arange(0, 10, 1) :");
        NdarrayInterface r = NdarrayFactory.arange(0, 10, 1);
        System.out.println(r);

        System.out.println("\n- array() à partir d'un tableau Java :");
        NdarrayInterface a = NdarrayFactory.array(new double[] { 1.5, 2.5, 3.5 });
        System.out.println(a);

        // --- 2. ATTRIBUTS ---
        System.out.println("\n2. ATTRIBUTS (Propriétés du tableau) :");
        NdarrayInterface m = NdarrayFactory.arange(1, 7, 1).reshape(2, 3);
        System.out.println("Matrice m :\n" + m);
        System.out.println("Nombre de dimensions (ndim) : " + m.getNdim());
        System.out.println("Forme (shape) : " + m.getShape());
        System.out.println("Taille totale (size) : " + m.getSize());

        // --- 3. ACCÈS ET MODIFICATION ---
        System.out.println("\n3. ACCÈS ET MODIFICATION :");
        System.out.println("Valeur à (0, 0) : " + m.get(0, 0));
        m.set(99.0, 0, 0);
        System.out.println("Après m.set(99.0, 0, 0) :\n" + m);

        // --- 4. OPÉRATIONS ARITHMÉTIQUES ---
        System.out.println("\n4. OPÉRATIONS ARITHMÉTIQUES :");

        System.out.println("- Addition de deux tableaux (m + m) :");
        NdarrayInterface resAdd = m.add(m);
        System.out.println(resAdd);

        System.out.println("\n- Addition d'un scalaire (m + 10.0) :");
        NdarrayInterface resScalar = m.add(10.0);
        System.out.println(resScalar);

        System.out.println("\n- Addition en place (+= 1.0) sur la matrice :");
        m.addInPlace(1.0);
        System.out.println(m);

        System.out.println("\n- Addition en place (+ m) sur elle-même :");
        m.addInPlace(m);
        System.out.println(m);

        // --- 5. TRANSFORMATION ---
        System.out.println("\n5. TRANSFORMATION (Reshape) :");
        System.out.println("Mutation d'une matrice 2x3 en 3x2 :");
        NdarrayInterface reshaped = m.reshape(3, 2);
        System.out.println(reshaped);

        // --- 6. FONCTIONS UNIVERSELLES (UFuncs) ---
        System.out.println("\n6. FONCTIONS UNIVERSELLES (UFuncs) :");
        Ndarray v = (Ndarray) NdarrayFactory.array(new double[] { 1.0, 4.0, 9.0 });
        System.out.println("Vecteur v : " + v);
        System.out.println("Racine carrée (sqrt) : " + UFuncs.sqrt(v));
        System.out.println("Exponentielle (exp)  : " + UFuncs.exp(v));

        // --- 7. BROADCASTING (NumPy-Style) ---
        System.out.println("\n7. BROADCASTING :");
        Ndarray matrix = (Ndarray) NdarrayFactory.ones(2, 3);
        Ndarray row = (Ndarray) NdarrayFactory.array(new double[] { 10, 20, 30 });
        System.out.println("Matrice (2,3) :\n" + matrix);
        System.out.println("Ligne (3,) : " + row);

        System.out.println("Résultat de l'addition avec Broadcasting :");
        Ndarray resBroadcast = Broadcasting.add(matrix, row);
        System.out.println(resBroadcast);

        System.out.println("\n=================================================");
        System.out.println("   FIN DE LA DÉMONSTRATION");
        System.out.println("=================================================");
    }
}
