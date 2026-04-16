package fr.eseo.e3.poo.projet.blox.controleur;

import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.pieces.UsineDePiece;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class PieceDeplacementTest {

    public PieceDeplacementTest() {
        displayPuits();
    }

    private void displayPuits() {
        JFrame frame = new JFrame("Test Déplacement Pièce");
        Puits puits = new Puits(10, 20);
        
        // On génère deux pièces pour en avoir une "actuelle"
        puits.setPieceSuivante(UsineDePiece.genererTetromino());
        puits.setPieceSuivante(UsineDePiece.genererTetromino());
        
        // Position initiale visible
        if (puits.getPieceActuelle() != null) {
            puits.getPieceActuelle().setPosition(5, 5);
        }

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
                new PieceDeplacementTest();
            }
        });
    }
}
