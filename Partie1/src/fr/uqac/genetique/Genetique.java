package fr.uqac.genetique;

import fr.uqac.util.Calcul;
import fr.uqac.util.Info;
import fr.uqac.util.Solution;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * Algorithme génétique
 * 
 * @author Julien CUSSET, Benjamin DAGOURET
 */
public class Genetique {
    private final Info info;
    private final Calcul calcul;
    private final int iterations;
    private final int nombrePopulations;
    private final double probabiliteMutation;
    private final ArrayList<Solution> population;
    
    /**
     * Constrcteur
     * 
     * @param info Les informations de base (processingTime, RDS, ...)
     * @param iterations Le nombre de générations de l'algorithme
     * @param nombrePopulations La taille de la population initiale
     * @param probabiliteMutation La probabilité de mutation
     */
    public Genetique(Info info, int iterations, int nombrePopulations, double probabiliteMutation) {
        this.info = info;
        this.calcul = new Calcul(info);
        this.iterations = iterations;
        this.nombrePopulations = nombrePopulations;
        this.probabiliteMutation = probabiliteMutation;
        this.population = new ArrayList();
    }
    
    /**
     * Squelette de l'algorithme
     */
    public void algorithmeGenetique() {
        initialiserPopulation();
        
        for (int i=0; i < this.iterations; i++) {
            selection();
            croisement();
            mutation();
        }
        
        tri();
        System.out.println("Resultat algorithme génétique");
        System.out.println("    Meilleure solution trouvée");
        System.out.println("    " + this.population.get(0).ordonnancement);
        System.out.println("    Makespan : " + this.population.get(0).makespan);
    }
    
    /**
     * Initialisation de la population aléatoire
     */
    private void initialiserPopulation() {        
        for (int i=0; i < this.nombrePopulations; i++) {
            this.population.add(this.calcul.solutionInitiale());
        }
    }
    
    /**
     * Supprime les plus mauvais éléments de la population
     */
    private void selection() {
        tri();
        int j;
        
        if ((this.population.size()/2) % 2 == 0) {
            j = this.population.size()/2;
        } else {
            j = 1 + this.population.size()/2;
        }
        
        for (int i=0; i < j; i++) {
            this.population.remove(this.population.size() - 1);
        }
    }
    
    /**
     * Croise tous les individus 2 à 2 choisis au hasard
     */
    private void croisement() {
        ArrayList<Integer> croisement = new ArrayList();
        
        for (int i=0; i < this.population.size(); i++) {
            croisement.add(i);
        }
        
        Collections.shuffle(croisement);
        int j = this.population.size()/2;
        
        for (int i=0; i < j; i++) {
            enfants(this.population.get(i*2), this.population.get(1+i*2));
        }
    }
    
    /**
     * Créer deux nouvelles solutions (enfants) à partir de deux solutions (parents)
     * 
     * @param a La solution parent a
     * @param b La solution parent b
     */
    private void enfants(Solution a, Solution b) {
        int pivot = new Random().nextInt(this.info.jobs);
        
        Solution enfant1 = new Solution();
        Solution enfant2 = new Solution();
        
        for (int i=0; i < 20; i++) {
            if (i < pivot) {
                enfant1.ordonnancement.add(a.ordonnancement.get(i));
                enfant2.ordonnancement.add(b.ordonnancement.get(i));
            } else {
                enfant1.ordonnancement.add(b.ordonnancement.get(i));
                enfant2.ordonnancement.add(a.ordonnancement.get(i));
            }
        }
        
        enfant1.validate();
        enfant2.validate();
        
        enfant1.makespan = this.calcul.calculateMakespan(enfant1.ordonnancement);
        enfant2.makespan = this.calcul.calculateMakespan(enfant2.ordonnancement);
        
        this.population.add(enfant1);
        this.population.add(enfant2);
    }
    
    /**
     * Echange le placement de deux valeurs du tableau d'ordonnancement
     */
    private void mutation() {
        Random rng = new Random();
        
        for (int i=0; i < this.population.size(); i++) {
            if (rng.nextDouble() < this.probabiliteMutation) {
                int pivot1 = rng.nextInt(this.info.jobs);
                int pivot2 = rng.nextInt(this.info.jobs);
                Collections.swap(this.population.get(i).ordonnancement, pivot1, pivot2);
                this.population.get(i).makespan = this.calcul.calculateMakespan(this.population.get(i).ordonnancement);
            }
        }
    }
    
    /**
     * Tri la population en fonction du makespan du meilleur au moins bon
     */
    private void tri() {
        Collections.sort(this.population, new Comparator<Solution>(){
            @Override
            public int compare(Solution s1, Solution s2){
                return s1.makespan - s2.makespan;
            }
        });
    }
}