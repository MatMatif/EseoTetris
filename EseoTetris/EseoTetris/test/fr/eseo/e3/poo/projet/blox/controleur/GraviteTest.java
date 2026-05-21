package fr.eseo.e3.poo.projet.blox.controleur;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;

import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.pieces.UsineDePiece;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

public class GraviteTest {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GraviteTest::run);
    }

    private static void run() {
        Puits puits = new Puits(10, 20);
        VuePuits vuePuits = new VuePuits(puits, 30);

        UsineDePiece.setMode(UsineDePiece.ALEATOIRE_PIECE);
        puits.setPieceSuivante(UsineDePiece.genererTetromino());
        puits.setPieceSuivante(UsineDePiece.genererTetromino());

        JFrame frame = new JFrame("Gravite Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vuePuits, BorderLayout.CENTER);
        frame.setSize(vuePuits.getPreferredSize());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // La gravité démarre automatiquement à l'instanciation
        new Gravite(vuePuits);
    }
}
