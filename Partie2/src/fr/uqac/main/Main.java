package fr.uqac.main;

import fr.uqac.genetique.Genetique;
import fr.uqac.local.Local;
import fr.uqac.tabou.Tabou;
import fr.uqac.util.Info;
import fr.uqac.util.Params;
import fr.uqac.util.ParamsReader;
import fr.uqac.util.TxtFileReader;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Classe principale
 * 
 * @author Julien CUSSET, Benjamin DAGOURET
 */
public class Main {
    public static int method = 1;
    public static String fileName = "";
    public static Params params;
    
    /**
     * Ecrit les premières informations dans le fichier résultat (date, méthode et fichier)
     * Si un fichier résultat.txt existe, il sera supprimé
     * 
     * @param resultat Le résultat sous la forme d'une chaîne de caractères
     * @throws java.io.IOException
     */
    public static void printFile(String resultat) throws IOException {
        File resultFile = new File("result.txt");
        if (resultFile.exists()) {
            resultFile.delete();
        }
        resultFile.createNewFile();
        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        FileWriter fw = new FileWriter("result.txt", true);
        PrintWriter pw = new PrintWriter(fw);
        
        pw.printf("%s%n", "======================================================================================");
        pw.printf("%s", "Méthode: ");
        
        switch(method) {
            case 1:
                pw.printf("%s%n", "Recherche Locale");
                break;
            case 2:
                pw.printf("%s%n", "Recherche Tabou");
                break;
            case 3:
                pw.printf("%s%n", "Algorithme Génétique");
                break;
        }
        
        pw.printf("%s%s%n", "Fichier: ", fileName);
        pw.printf("%s%s%n", "Date: ", dateFormat.format(date));
        pw.printf("%s%n%n", "======================================================================================");
        pw.printf(resultat);
        pw.close();
    }
    
    /**
     * Recherche locale
     * 
     * @param info
     * @return Le résultat
     */
    public static String local(Info info) {
        Local loc = new Local(info, params.iterationLocal);
        return loc.rechercheLocale();        
    }
    
    /**
     * Recherche tabou
     * 
     * @param info
     * @return Le résultat
     */
    public static String tabou(Info info) {
        Tabou tab = new Tabou(info, params.iterationTabou, 20);
        return tab.rechercheTabou();
    }
    
    /**
     * Algorithme génétique
     * 
     * @param info
     * @return Le résultat
     */
    public static String genetic(Info info) {
        Genetique gen = new Genetique(info, params.iterationGenetic, params.population, params.mutation);
        return gen.algorithmeGenetique();
    }
    
    /**
     * Execution de la méthode sélectionnée
     */
    public static void compute() {
        try {
            TxtFileReader tfr = new TxtFileReader();
            Info info = tfr.readFile(fileName);
            
            String resultat = "";
            switch(method) {
                case 1:
                    resultat = local(info);
                    break;
                case 2:
                    resultat = tabou(info);
                    break;
                case 3:
                    resultat = genetic(info);
                    break;
            }
            
            printFile(resultat);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Choix du fichier
     * 
     * @param parent La fenetre principale
     */
    public static void chooseFile(JFrame parent) {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        chooser.setFileFilter(filter);
        
        int returnVal = chooser.showOpenDialog(parent);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
           fileName = chooser.getSelectedFile().getAbsolutePath();
        }
    }
    
    /**
     * Fenetre principale
     */
    public static void createFrame() {
        JFrame frame = new JFrame("TP4 - Exercice 2");
        JPanel panelCenter = new JPanel();
        panelCenter.setLayout(new GridLayout(3, 1));
        JPanel panelSouth = new JPanel();
        panelSouth.setLayout(new GridLayout(1, 1));
        
        JRadioButton radioButton1 = new JRadioButton("Recherche locale");
        radioButton1.setSelected(true);
        JRadioButton radioButton2 = new JRadioButton("Recherche tabou");
        JRadioButton radioButton3 = new JRadioButton("Algorithme génétique");
        
        ButtonGroup group = new ButtonGroup();
        group.add(radioButton1);
        group.add(radioButton2);
        group.add(radioButton3);
        
        panelCenter.add(radioButton1);
        panelCenter.add(radioButton2);
        panelCenter.add(radioButton3);
        
        radioButton1.addActionListener((ActionEvent e) -> {
            method = 1;
        });
        radioButton2.addActionListener((ActionEvent e) -> {
            method = 2;
        });
        radioButton3.addActionListener((ActionEvent e) -> {
            method = 3;
        });
        
        JButton confirm = new JButton("OK");
        confirm.addActionListener((ActionEvent e) -> {
            chooseFile(frame);
            
            if (!fileName.equals("")) {
                compute();
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
        panelSouth.add(confirm);

        frame.add(panelCenter, BorderLayout.CENTER);
        frame.add(panelSouth, BorderLayout.SOUTH);
        frame.setVisible(true);
        frame.setSize(200, 200);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * Main
     * 
     * @param args Arguments données en ligne de commande
     */
    public static void main(String[] args) {
        ParamsReader pr = new ParamsReader();
        params = pr.readFile();
        
        createFrame();
    }
}