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
    private final float probabiliteMutation;
    private final ArrayList<Solution> population;
    private Solution meilleureSolution;
    
    /**
     * Constrcteur
     * 
     * @param info Les informations de base (processingTime, RDS, ...)
     * @param iterations Le nombre de générations de l'algorithme
     * @param nombrePopulations La taille de la population initiale
     * @param probabiliteMutation La probabilité de mutation
     */
    public Genetique(Info info, int iterations, int nombrePopulations, float probabiliteMutation) {
        this.info = info;
        this.calcul = new Calcul(info);
        this.iterations = iterations;
        
        if (nombrePopulations % 2 == 1) {
            this.nombrePopulations = nombrePopulations + 1;
        } else {
            this.nombrePopulations = nombrePopulations;
        }
        
        this.probabiliteMutation = probabiliteMutation;
        this.population = new ArrayList();
    }
    
    /**
     * Squelette de l'algorithme
     * 
     * @return Le résultat
     */
    public String algorithmeGenetique() {
        initialiserPopulation();
        
        for (int i=0; i < this.iterations; i++) {
            selection();
            croisement();
            mutation();
        }
        
        tri();
        
        String resultat = "Résultat algorithme génétique\n";
        resultat += "    Meilleure solution trouvée\n";
        resultat += "    " + this.meilleureSolution.ordonnancement + "\n";
        resultat += "    Makespan : " + this.meilleureSolution.makespan + "\n";
        
        return resultat;
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
        
        int size = this.population.size();
        for (int i=0; i < size/2; i++) {
            this.population.remove(this.population.size() - 1);
        }
        
        Random rand = new Random();
        while (this.population.size() != size) {
            int index = rand.nextInt(this.population.size());
            Solution newSolution = this.population.get(index);
            this.population.add(newSolution);
        }
    }
    
    /**
     * Croise tous les individus 2 à 2 choisis au hasard
     */
    private void croisement() {
        tri();
        
        ArrayList<Integer> croisement = new ArrayList();
        
        for (int i=0; i < this.population.size(); i++) {
            croisement.add(i);
        }
        
        Collections.shuffle(croisement);
        
        for (int i=0; i < this.population.size(); i=i+2) {
            enfants(this.population.get(croisement.get(i)), this.population.get(croisement.get(i+1)));
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
        
        for (int i=0; i < this.info.jobs; i++) {
            if (i < pivot) {
                enfant1.ordonnancement.add(a.ordonnancement.get(i));
                enfant2.ordonnancement.add(b.ordonnancement.get(i));
            } else {
                enfant1.ordonnancement.add(b.ordonnancement.get(i));
                enfant2.ordonnancement.add(a.ordonnancement.get(i));
            }
        }
        
        enfant1.validate();
        enfant1.makespan = this.calcul.calculateMakespan(enfant1.ordonnancement);
        if (this.population.get(this.population.size()-1).makespan >= enfant1.makespan) {
            this.population.set(this.population.size()-1, enfant1);
        }
        
        enfant2.validate();
        enfant2.makespan = this.calcul.calculateMakespan(enfant2.ordonnancement);
        if (this.population.get(this.population.size()-2).makespan >= enfant2.makespan) {
            this.population.set(this.population.size()-2, enfant2);
        }
    }
    
    /**
     * Echange le placement de deux valeurs du tableau d'ordonnancement
     */
    private void mutation() {
        tri();
        
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
        
        this.meilleureSolution = this.population.get(0);
    }
}