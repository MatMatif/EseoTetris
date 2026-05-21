package fr.eseo.e3.poo.projet.blox;

import fr.eseo.e3.poo.projet.blox.controleur.Gravite;
import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.pieces.UsineDePiece;
import fr.eseo.e3.poo.projet.blox.vue.PanneauInformation;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;

public class FallingBloxVersion1 {

    public static void main(String[] args) {
        final int nbElements;
        final int nbLignes;

        if (args.length >= 2) {
            nbElements = Integer.parseInt(args[0]);
            nbLignes = Integer.parseInt(args[1]);
        } else if (args.length == 1) {
            nbElements = Integer.parseInt(args[0]);
            nbLignes = 0;
        } else {
            nbElements = 0;
            nbLignes = 0;
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Puits puits = new Puits(10, 20, nbElements, nbLignes);

                VuePuits vuePuits = new VuePuits(puits, 25);
                PanneauInformation panneauInfo = new PanneauInformation(puits);

                JFrame frame = new JFrame("Falling Blox");
                frame.setLayout(new BorderLayout());
                frame.add(vuePuits, BorderLayout.CENTER);
                frame.add(panneauInfo, BorderLayout.EAST);

                frame.setResizable(false);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                puits.setPieceSuivante(UsineDePiece.genererTetromino());
                puits.setPieceSuivante(UsineDePiece.genererTetromino());

                new Gravite(vuePuits);

                frame.setVisible(true);
                vuePuits.requestFocusInWindow();
            }
        });
    }
}
