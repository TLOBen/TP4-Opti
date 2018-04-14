/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uqac.util;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Benjamin
 */
public class Solution {
    public int makespan;
    public ArrayList<Integer> ordonnancement;
    
    public Solution() {
        this.ordonnancement = new ArrayList();
    }
    
    public void init(int jobs) {
        for (int i=0; i < jobs; i++) {
            this.ordonnancement.add(i);
        }
        
        Collections.shuffle(this.ordonnancement);
    }
    
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
