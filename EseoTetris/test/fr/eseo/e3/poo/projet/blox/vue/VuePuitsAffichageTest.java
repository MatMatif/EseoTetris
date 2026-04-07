package fr.eseo.e3.poo.projet.blox.vue;

import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.pieces.UsineDePiece;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class VuePuitsAffichageTest {

    public VuePuitsAffichageTest() {
        testConstructeurPuits();
        testConstructeurPuitsTaille();
    }

    private void testConstructeurPuits() {
        JFrame frame = new JFrame("Puits");
        Puits puits = new Puits();
        
        // Création de la vue (enregistrement automatique comme listener)
        VuePuits vuePuits = new VuePuits(puits);
        
        // Ajout des pièces : la mise à jour de la vue est automatique via PropertyChangeListener
        puits.setPieceSuivante(UsineDePiece.genererTetromino());
        puits.setPieceSuivante(UsineDePiece.genererTetromino());
        
        frame.add(vuePuits);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void testConstructeurPuitsTaille() {
        JFrame frame = new JFrame("Puits et taille");
        Puits puits = new Puits();
        VuePuits vuePuits = new VuePuits(puits, 30);
        
        puits.setPieceSuivante(UsineDePiece.genererTetromino());
        puits.setPieceSuivante(UsineDePiece.genererTetromino());
        
        frame.add(vuePuits);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VuePuitsAffichageTest();
            }
        });
    }
}
