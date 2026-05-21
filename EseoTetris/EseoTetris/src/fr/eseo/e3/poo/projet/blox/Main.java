package fr.eseo.e3.poo.projet.blox;

import fr.eseo.e3.poo.projet.blox.modele.BloxException;
import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.pieces.UsineDePiece;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // 1. Création du modèle
                Puits puits = new Puits(10, 20);
                
                // 2. Préparation de l'usine et ajout d'une pièce
                UsineDePiece.setMode(UsineDePiece.ALEATOIRE_PIECE);
                puits.setPieceSuivante(UsineDePiece.genererTetromino());
                puits.setPieceSuivante(UsineDePiece.genererTetromino()); // Pousse la 1ere en pieceActuelle

                // DEBUG: On force la position à y=5 pour la voir immédiatement
                if (puits.getPieceActuelle() != null) {
                    puits.getPieceActuelle().setPosition(puits.getLargeur() / 2, 5);
                }

                // 3. Création de la vue
                VuePuits vuePuits = new VuePuits(puits, 30);

                // 4. Création de la fenêtre
                JFrame frame = new JFrame("EseoTetris - Falling Blox");
                frame.add(vuePuits);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);

                // 5. Petit Timer pour tester le déplacement automatique (Section 3.4)
                Timer timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (puits.getPieceActuelle() != null) {
                            try {
                                puits.getPieceActuelle().deplacerDe(0, 1);
                            } catch (IllegalArgumentException | BloxException ex) {
                                // Fin de descente pour ce test
                            }
                        }
                    }
                });
                timer.start();
            }
        });
    }
}
