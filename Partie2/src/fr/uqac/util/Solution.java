package fr.uqac.util;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Classe de Solution
 * 
 * @author Julien CUSSET, Benjamin DAGOURET
 */
public class Solution {
    public int makespan;
    public ArrayList<Integer> ordonnancement;
    
    /**
     * Constructeur
     */
    public Solution() {
        this.ordonnancement = new ArrayList();
    }
    
    /**
     * Initialise aléatoirement l'ordonnancement de jobs
     * 
     * @param jobs Le nombre de jobs
     */
    public void init(int jobs) {
        for (int i=0; i < jobs; i++) {
            this.ordonnancement.add(i);
        }
        
        Collections.shuffle(this.ordonnancement);
    }
    
    /**
     * Transforme une solution actuelle infaisable en une solution faisable
     */
    public void validate() {
        ArrayList<Integer> jobsUtilise = new ArrayList();
        
        for (int i=0; i < this.ordonnancement.size(); i++) {
            jobsUtilise.add(i);
        }
        
        for (int i=0; i < this.ordonnancement.size(); i++) {
            if (!jobsUtilise.contains(this.ordonnancement.get(i))) {
                this.ordonnancement.set(i, jobsUtilise.get(0));
            }
            
            jobsUtilise.remove(this.ordonnancement.get(i));
        }
    }
}