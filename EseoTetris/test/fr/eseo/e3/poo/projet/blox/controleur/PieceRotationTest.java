package fr.eseo.e3.poo.projet.blox.controleur;

import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.pieces.UsineDePiece;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class PieceRotationTest {

    public PieceRotationTest() {
        displayPuits();
    }

    private void displayPuits() {
        JFrame frame = new JFrame("Test Rotation Pièce");
        Puits puits = new Puits(10, 20);
        
        // On génère des pièces pour en avoir une "actuelle"
        // On s'assure d'avoir un ITetromino pour bien voir la rotation
        UsineDePiece.setMode(UsineDePiece.CYCLIC); 
        // Si CYCLIC 0 -> O, 1 -> I. On veut le I.
        puits.setPieceSuivante(UsineDePiece.genererTetromino()); // O
        puits.setPieceSuivante(UsineDePiece.genererTetromino()); // I devient actuelle
        
        // Position initiale visible
        if (puits.getPieceActuelle() != null) {
            puits.getPieceActuelle().setPosition(5, 5);
        }

        // VuePuits enregistre automatiquement PieceRotation (et PieceDeplacement)
        VuePuits vuePuits = new VuePuits(puits, 30);
        
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
                new PieceRotationTest();
            }
        });
    }
}
