package fr.eseo.e3.poo.projet.blox.controleur;

import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.pieces.UsineDePiece;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;

public class PieceDeplacementTest {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PieceDeplacementTest::run);
    }

    private static void run() {
        Puits puits = new Puits(12, 22);
        VuePuits vuePuits = new VuePuits(puits, 25);

        UsineDePiece.setMode(UsineDePiece.CYCLIC);
        puits.setPieceSuivante(UsineDePiece.genererTetromino());
        puits.setPieceSuivante(UsineDePiece.genererTetromino());

        JFrame frame = new JFrame("Piece Deplacement Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vuePuits, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
