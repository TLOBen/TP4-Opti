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
     * 
     * @return Le résultat
     */
    public String rechercheLocale() {
        this.solution = this.calcul.solutionInitiale();
        
        for (int i=0; i < this.iterations; i++) {
            chercherMeilleureSolution();
        }        
        
        String resultat = "Résutat recherche locale\n";
        resultat += "    Meilleure solution trouvée\n";
        resultat += "    " + this.solution.ordonnancement + "\n";
        resultat += "    Makespan : " + this.solution.makespan + "\n";
        
        return resultat;
    }
    
    /**
     * Méthode de recherche d'une meilleure solution
     */
    private void chercherMeilleureSolution() {
        int maxRDS = 0, jobID = 0;
        
        for (int i=0; i < this.info.jobs - 1; i++) {
            int job1 = this.solution.ordonnancement.get(i);
            int job2 = this.solution.ordonnancement.get(i+1);
            
            int RDS = 0;
            for (int j=0; j < this.info.machines; j++) {
                RDS += this.info.RDS[j][job1][job2];
            }
            
            if (maxRDS < RDS) {
                maxRDS = RDS;
                jobID = i;
            }
        }
        
        ArrayList<Integer> tempSolution = (ArrayList<Integer>) this.solution.ordonnancement.clone();
        for (int i=0; i < this.info.jobs; i++) {
            Solution nouvelleSolution = new Solution();
            nouvelleSolution.ordonnancement = (ArrayList<Integer>) tempSolution.clone();
            Collections.swap(nouvelleSolution.ordonnancement, i, jobID);
            nouvelleSolution.makespan = this.calcul.calculateMakespan(nouvelleSolution.ordonnancement);
            
            if (nouvelleSolution.makespan <= this.solution.makespan) {
                this.solution = nouvelleSolution;
            }
        }
    }
}