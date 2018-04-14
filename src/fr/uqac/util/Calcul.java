/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uqac.util;

import java.util.ArrayList;

/**
 *
 * @author Benjamin
 */
public class Calcul {
    private Info info;
    
    public Calcul(Info info) {
        this.info = info;
    }
    
    public Solution solutionInitiale(int jobs) {
        Solution solution = new Solution();
        solution.init(jobs);
        solution.makespan = calculateMakespan(solution.ordonnancement);
        
        return solution;
    }
    
    public int calculateMakespan(ArrayList<Integer> ordonnancement) {
        int[] makespanParMachine = new int[this.info.machines];
        
        for (int i=0; i < this.info.jobs; i++) {
            int job = ordonnancement.get(i);
            
            for (int j=0; j < this.info.machines; j++) {
                if (j != 0) {
                    if (makespanParMachine[j-1] > makespanParMachine[j]) {
                        makespanParMachine[j] = makespanParMachine[j-1];
                    }
                }
                
                makespanParMachine[j] += this.info.processingTime[j][job];
                if(i != this.info.jobs - 1) {
                    makespanParMachine[j] += this.info.RDS[j][job][ordonnancement.get(i+1)];
                }
            }
        }
        
        return makespanParMachine[this.info.machines - 1];
    }
}
