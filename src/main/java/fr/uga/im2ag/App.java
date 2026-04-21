package fr.uga.im2ag;

import fr.uga.im2ag.scilib.core.NdarrayFactory;
import fr.uga.im2ag.scilib.core.NdarrayInterface;
import fr.uga.im2ag.scilib.core.Ndarray;
import fr.uga.im2ag.scilib.core.UFuncs;
import fr.uga.im2ag.scilib.core.Broadcasting;

import java.util.logging.Logger;

/**
 * Application de Démonstration complète de la bibliothèque Scilib-DevOps
 */
public class App {

    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        LOGGER.info("=================================================");
        LOGGER.info("   DÉMONSTRATION COMPLÈTE - SCI-LIB DEVOPS");
        LOGGER.info("=================================================");

        // --- 1. CRÉATION ---
        LOGGER.info("1. MÉTHODES DE CRÉATION :");

        LOGGER.info("- zeros(2, 3) :");
        NdarrayInterface z = NdarrayFactory.zeros(2, 3);
        LOGGER.info(String.valueOf(z));

        LOGGER.info("\n- ones(3, 3) :");
        NdarrayInterface o = NdarrayFactory.ones(3, 3);
        LOGGER.info(String.valueOf(o));

        LOGGER.info("\n- arange(0, 10, 1) :");
        NdarrayInterface r = NdarrayFactory.arange(0, 10, 1);
        LOGGER.info(String.valueOf(r));

        LOGGER.info("\n- array() à partir d'un tableau Java :");
        NdarrayInterface a = NdarrayFactory.array(new double[] { 1.5, 2.5, 3.5 });
        LOGGER.info(String.valueOf(a));

        // --- 2. ATTRIBUTS ---
        LOGGER.info("\n2. ATTRIBUTS (Propriétés du tableau) :");
        NdarrayInterface m = NdarrayFactory.arange(1, 7, 1).reshape(2, 3);
        LOGGER.info("Matrice m :\n" + m);
        LOGGER.info("Nombre de dimensions (ndim) : " + m.getNdim());
        LOGGER.info("Forme (shape) : " + m.getShape());
        LOGGER.info("Taille totale (size) : " + m.getSize());

        // --- 3. ACCÈS ET MODIFICATION ---
        LOGGER.info("\n3. ACCÈS ET MODIFICATION :");
        LOGGER.info("Valeur à (0, 0) : " + m.get(0, 0));
        m.set(99.0, 0, 0);
        LOGGER.info("Après m.set(99.0, 0, 0) :\n" + m);

        // --- 4. OPÉRATIONS ARITHMÉTIQUES ---
        LOGGER.info("\n4. OPÉRATIONS ARITHMÉTIQUES :");

        LOGGER.info("- Addition de deux tableaux (m + m) :");
        NdarrayInterface resAdd = m.add(m);
        LOGGER.info(String.valueOf(resAdd));

        LOGGER.info("\n- Addition d'un scalaire (m + 10.0) :");
        NdarrayInterface resScalar = m.add(10.0);
        LOGGER.info(String.valueOf(resScalar));

        LOGGER.info("\n- Addition en place (+= 1.0) sur la matrice :");
        m.addInPlace(1.0);
        LOGGER.info(String.valueOf(m));

        LOGGER.info("\n- Addition en place (+ m) sur elle-même :");
        m.addInPlace(m);
        LOGGER.info(String.valueOf(m));

        // --- 5. TRANSFORMATION ---
        LOGGER.info("\n5. TRANSFORMATION (Reshape) :");
        LOGGER.info("Mutation d'une matrice 2x3 en 3x2 :");
        NdarrayInterface reshaped = m.reshape(3, 2);
        LOGGER.info(String.valueOf(reshaped));

        // --- 6. FONCTIONS UNIVERSELLES (UFuncs) ---
        LOGGER.info("\n6. FONCTIONS UNIVERSELLES (UFuncs) :");
        Ndarray v = (Ndarray) NdarrayFactory.array(new double[] { 1.0, 4.0, 9.0 });
        LOGGER.info("Vecteur v : " + v);
        LOGGER.info("Racine carrée (sqrt) : " + UFuncs.sqrt(v));
        LOGGER.info("Exponentielle (exp)  : " + UFuncs.exp(v));

        // --- 7. BROADCASTING (NumPy-Style) ---
        LOGGER.info("\n7. BROADCASTING :");
        Ndarray matrix = (Ndarray) NdarrayFactory.ones(2, 3);
        Ndarray row = (Ndarray) NdarrayFactory.array(new double[] { 10, 20, 30 });
        LOGGER.info("Matrice (2,3) :\n" + matrix);
        LOGGER.info("Ligne (3,) : " + row);

        LOGGER.info("Résultat de l'addition avec Broadcasting :");
        Ndarray resBroadcast = Broadcasting.add(matrix, row);
        LOGGER.info(String.valueOf(resBroadcast));

        LOGGER.info("\n=================================================");
        LOGGER.info("   FIN DE LA DÉMONSTRATION");
        LOGGER.info("=================================================");
    }
}
