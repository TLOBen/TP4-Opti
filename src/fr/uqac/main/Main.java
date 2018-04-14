package fr.uqac.main;

import fr.uqac.genetique.Genetique;
import fr.uqac.util.Info;
import fr.uqac.util.TxtFileReader;

/**
 *
 * @author Benjamin
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TxtFileReader tfr = new TxtFileReader();
        Info info = tfr.readFile("C:/Users/Benjamin/Documents/Divers/Documents/Ecoles/UQAC/8INF808 - Informatique appliquée et optimisation/Devoirs/Devoir 4/I_20_5_S_1-49_1.txt");
        
        Genetique gen = new Genetique(info, 5000, 1000, 0.1);
        gen.algorithmeGenetique();
    }
}
