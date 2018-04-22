package fr.uqac.main;

import fr.uqac.genetique.Genetique;
import fr.uqac.local.Local;
import fr.uqac.util.Info;
import fr.uqac.util.TxtFileReader;

/**
 * Classe principale
 * 
 * @author Julien CUSSET, Benjamin DAGOURET
 */
public class Main {
    /**
     * Méthode principale
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TxtFileReader tfr = new TxtFileReader();
        Info info = tfr.readFile("C:/Users/krato/Documents/Etudes/UQAC/Cours/RO/Devoir4_CUSSET_DAGOURET_8INF808_H2018/instances/I_20_5_S_1-49_1.txt");
        
        //TxtFileReader.writeFile(info, "C:/Users/krato/Documents/");
        
        
        Local loc = new Local(info, 10000);
        loc.rechercheLocale();
        
        Genetique gen = new Genetique(info, 5000, 1000, 0.1);
        gen.algorithmeGenetique();

    }
}