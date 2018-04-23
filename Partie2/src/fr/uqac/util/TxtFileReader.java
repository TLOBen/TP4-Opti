package fr.uqac.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.SecurityException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Classe permettant de lire les fichiers de tests
 * 
 * @author Julien CUSSET, Benjamin DAGOURET
 */
public class TxtFileReader {
    /**
     * Methode de lecture d'un fichier de test
     * 
     * @param filePath Le chemin vers le fichier test
     * @return Les informations des n premières instances contenus dans ce fichier
     */
    public Info readFile(String filePath) {
        String line;
        Scanner scanner;

        // Ouverture du fichier
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            int jobs=0, machines=0;

            // Ligne avec le nombre de jobs, machines, ...
            if((line = bufferedReader.readLine()) != null) {
                scanner = new Scanner(line);

                
                for (int j=0; j<2 && scanner.hasNextInt(); j++) {
                    if (j==0) {
                        jobs = scanner.nextInt();
                    } else {
                        machines = scanner.nextInt();
                    }
                }
            }
            
            Info info = new Info(jobs, machines);
            
            for (int i=0; i<jobs; i++) {
                if((line = bufferedReader.readLine()) != null) {
                    scanner = new Scanner(line);

                    for (int j=0; j<2*machines && scanner.hasNextInt(); j++) {
                        if (j%2==0) {
                            scanner.nextInt();
                        } else {
                            info.processingTime[j/2][i] = scanner.nextInt();
                        }
                    }
                }
            }
            
            // On passe la ligne SSD
            bufferedReader.readLine();

            for (int i=0; i < machines; i++) {
                // On passe la ligne M0, M1, ...
                bufferedReader.readLine();
                
                for (int j=0; j < jobs; j++) {
                    if((line = bufferedReader.readLine()) != null) {
                        scanner = new Scanner(line);

                        for (int k=0; k < jobs && scanner.hasNextInt(); k++) {
                            info.RDS[i][j][k] = scanner.nextInt();
                        }
                    }
                }
            }

            bufferedReader.close();
        
            return info;
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filePath + "'");
            System.exit(0);
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + filePath + "'");
            System.exit(0);
        }
        
        return null;
    }
    
    /*
    * write file in a ILOG CPLEX .dat format 
    */
    public static void writeFile(Info data, String folderPath){
        String path = folderPath + data.jobs + "_" + data.machines + ".dat";
        PrintWriter pw = null;
        try{
            pw = new PrintWriter(path);
        }catch(FileNotFoundException e){
            System.out.println("file not found " + path);
            System.exit(0);
        }catch(SecurityException e){
            System.out.println("security exception at " + path);
            System.exit(0);
        }
        
        pw.println("nbJobs = " + data.jobs);
        pw.println("nbMachines = " + data.machines);
        
        pw.println("p = [");
        for(int i = 0; i < data.machines; i++){
            pw.print("[\t" + data.processingTime[i][0]);
            for(int j = 1; j < data.jobs; j++){
                pw.print(",\t" + data.processingTime[i][j]);
            }
            pw.println("],");
        }
        pw.println("]");
        
        pw.println("s = [");
        for(int i = 0; i < data.machines; i++){
            pw.println("["); 
            for(int j = 0; j < data.jobs; j++){
                pw.print("[\t" + data.RDS[i][j][0]);
                for(int k = 1; k < data.jobs; k++){
                    pw.print(",\t" + data.RDS[i][j][k]);
                }
                pw.println("],");
            }
            pw.println("],");
        }
        pw.print("]");
        
        
        
        pw.close();
    }
}