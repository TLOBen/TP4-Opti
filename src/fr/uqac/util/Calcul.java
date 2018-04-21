package fr.uqac.util;

import java.util.ArrayList;

/**
 * Classe de calculs
 * 
 * @author Julien CUSSET, Benjamin DAGOURET
 */
public class Calcul {
    private Info info;
    
    /**
     * Constructeur
     * 
     * @param info Les informations de base (processingTime, RDS, ...)
     */
    public Calcul(Info info) {
        this.info = info;
    }
    
    /**
     * Créée aléatoirement une solution
     * 
     * @return Une solution aléatoire
     */
    public Solution solutionInitiale() {
        Solution solution = new Solution();
        solution.init(this.info.jobs);
        solution.makespan = calculateMakespan(solution.ordonnancement);
        
        return solution;
    }
    
    /**
     * Calcule le makespan d'une liste de jobs ordonnencés
     * 
     * @param ordonnancement Un ordonnancement de jobs
     * @return Son makespan
     */
    public int calculateMakespan(ArrayList<Integer> ordonnancement) {
        int[] makespanParMachine = new int[this.info.machines];
        
        for (int i=0; i < this.info.jobs; i++) {
            int job = ordonnancement.get(i);
            
            for (int j=0; j < this.info.machines; j++) {
                if(i != 0) {
                    makespanParMachine[j] += this.info.RDS[j][ordonnancement.get(i-1)][job];
                }
                
                if (j != 0) {
                    if (makespanParMachine[j-1] > makespanParMachine[j]) {
                        makespanParMachine[j] = makespanParMachine[j-1];
                    }
                }
                
                makespanParMachine[j] += this.info.processingTime[j][job];
            }
        }
        
        return makespanParMachine[this.info.machines - 1];
    }
}