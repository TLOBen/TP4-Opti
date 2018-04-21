package fr.uqac.util;

/**
 * Classe contenant les infos de test
 * 
 * @author Julien CUSSET, Benjamin DAGOURET
 */
public class Info {
    public int jobs;
    public int machines;
    public int[][][] RDS;
    public int[][] processingTime;
    
    /**
     * Constructeur
     * 
     * @param jobs Le nombre de jobs
     * @param machines Le nombre de machines
     */
    public Info(int jobs, int machines) {
        this.jobs = jobs;
        this.machines = machines;
        this.RDS = new int[machines][jobs][jobs];
        this.processingTime = new int[machines][jobs];
    }
}