package fr.eseo.e3.poo.projet.blox.controleur;

import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.pieces.UsineDePiece;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;

/**
 * Test manuel — EXTENSION (section 4.1).
 *
 * Lance une fenêtre avec un VuePuits et une pièce qui tombe automatiquement.
 * À tester à la main :
 *   ← / →   : la pièce se déplace latéralement d'une colonne
 *   ↓       : la pièce descend d'une case (équivalent gravité)
 *   ↑       : rotation sens horaire
 *   Espace  : rotation sens anti-horaire
 *
 * Les contrôleurs souris {@link PieceDeplacement} et {@link PieceRotation}
 * restent fonctionnels en parallèle.
 */
public class PieceClavierTest {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PieceClavierTest::run);
    }

    private static void run() {
        Puits puits = new Puits(10, 20);
        VuePuits vuePuits = new VuePuits(puits, 25);

        UsineDePiece.setMode(UsineDePiece.CYCLIC);
        puits.setPieceSuivante(UsineDePiece.genererTetromino());
        puits.setPieceSuivante(UsineDePiece.genererTetromino());

        JFrame frame = new JFrame("Test Clavier — flèches + Espace");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vuePuits, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Important : donner le focus au VuePuits pour qu'il reçoive les touches
        vuePuits.requestFocusInWindow();

        // On démarre la gravité automatique pour valider que le clavier
        // n'entre pas en conflit avec le timer
        new Gravite(vuePuits);
    }
}
