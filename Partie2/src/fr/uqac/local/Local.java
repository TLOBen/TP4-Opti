package fr.uqac.local;

import fr.uqac.util.Calcul;
import fr.uqac.util.Info;
import fr.uqac.util.Solution;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Recherche locale
 * 
 * @author Julien CUSSET, Benjamin DAGOURET
 */
public class Local {
    private final Info info;
    private final Calcul calcul;
    private final Random rand;
    private final int iterations;
    private Solution solution;
    
    /**
     * Constructeur
     * 
     * @param info Les informations de base (processingTime, RDS, ...)
     * @param iterations Le nombre d'itérations de l'algorithme
     */
    public Local(Info info, int iterations) {
        this.info = info;
        this.calcul = new Calcul(info);
        this.rand = new Random();
        this.iterations = iterations;
    }
    
    /**
     * Squelette de l'algorithme
     */
    public void rechercheLocale() {
        this.solution = this.calcul.solutionInitiale();
        
        for (int i=0; i < this.iterations; i++) {
            chercherMeilleureSolution();
        }
        
        System.out.println("Résutat recherche locale");
        System.out.println("    Meilleure solution trouvée");
        System.out.println("    " + this.solution.ordonnancement);
        System.out.println("    Makespan : " + this.solution.makespan);
    }
    
    /**
     * Méthode de recherche d'une meilleure solution
     */
    private void chercherMeilleureSolution() {
        Solution nouvelleSolution = creerSolution();
        
        if (nouvelleSolution.makespan <= this.solution.makespan) {
            this.solution = nouvelleSolution;
        }
    }
    
    /**
     * Créée une nouvelle solution en échangeant un job avec un autre
     * 
     * @return Une nouvelle solution
     */
    private Solution creerSolution() {
        int indice1 = rand.nextInt(this.info.jobs);
        int indice2 = rand.nextInt(this.info.jobs);
        
        while (indice1 == indice2) {
            indice2 = rand.nextInt(this.info.jobs);
        }
        
        Solution nouvelleSolution = new Solution();
        nouvelleSolution.ordonnancement = (ArrayList<Integer>) this.solution.ordonnancement.clone();
        Collections.swap(nouvelleSolution.ordonnancement, indice1, indice2);
        nouvelleSolution.makespan = this.calcul.calculateMakespan(nouvelleSolution.ordonnancement);
        
        return nouvelleSolution;
    }
}