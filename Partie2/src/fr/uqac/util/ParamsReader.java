/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uqac.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Classe permettant de lire les paramètres du programme
 * 
 * @author Julien CUSSET, Benjamin DAGOURET
 */
public class ParamsReader {
    /**
     * Methode de lecture d'un fichier de paramètres
     * 
     * @return Les paramètres du programme
     */
    public Params readFile() {
        String line;
        Scanner scanner;
        Params params = new Params();

        // Ouverture du fichier
        try {
            FileReader fileReader = new FileReader("params.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // On passe la ligne "Recherche locale"
            bufferedReader.readLine();
            
            if((line = bufferedReader.readLine()) != null) {
                scanner = new Scanner(line);
                
                for (int j=0; j < 1 && scanner.hasNextInt(); j++) {
                    params.iterationLocal = scanner.nextInt();
                }
            }
            
            
            
            // On passe la ligne "Recherche tabou"
            bufferedReader.readLine();
            
            if((line = bufferedReader.readLine()) != null) {
                scanner = new Scanner(line);
                
                for (int j=0; j < 1 && scanner.hasNextInt(); j++) {
                    params.iterationTabou = scanner.nextInt();
                }
            }
            
            
            
            // On passe la ligne "Algorithme génétique"
            bufferedReader.readLine();
            
            if((line = bufferedReader.readLine()) != null) {
                scanner = new Scanner(line);
                
                for (int j=0; j < 3 && scanner.hasNext(); j++) {
                    if (j == 0) {
                        params.iterationGenetic = scanner.nextInt();
                    } else if (j == 1) {
                        params.population = scanner.nextInt();
                    } else {
                        params.mutation = scanner.nextFloat();
                    }
                }
            }

            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file params.txt");
            System.exit(0);
        }
        catch(IOException ex) {
            System.out.println("Error reading file params.txt");
            System.exit(0);
        }
        
        return params;
    }
}