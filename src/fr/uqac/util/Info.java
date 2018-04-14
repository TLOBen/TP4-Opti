/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uqac.util;

/**
 *
 * @author Benjamin
 */
public class Info {
    public int jobs;
    public int machines;
    public int[][][] RDS;
    public int[][] processingTime;
    
    public Info(int jobs, int machines) {
        this.jobs = jobs;
        this.machines = machines;
        this.RDS = new int[machines][jobs][jobs];
        this.processingTime = new int[machines][jobs];
    }
}
