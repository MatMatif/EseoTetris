package fr.eseo.e3.poo.projet.blox;

import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.pieces.UsineDePiece;
import fr.eseo.e3.poo.projet.blox.modele.BloxException;
import fr.eseo.e3.poo.projet.blox.vue.PanneauInformation;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FallingBloxVersion1 {

    public static void main(String[] args) {
        final int nbElements;
        final int nbLignes;

        if (args.length >= 2) {
            nbElements = Integer.parseInt(args[0]);
            nbLignes = Integer.parseInt(args[1]);
        } else if (args.length == 1) {
            nbElements = Integer.parseInt(args[0]);
            nbLignes = 0; // Calculé par le constructeur de Tas si nbLignes n'est pas fourni? 
            // En fait mon constructeur Tas(puits, nbElements) calcule nbLignes.
        } else {
            nbElements = 0;
            nbLignes = 0;
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // 1. Création du modèle
                Puits puits;
                if (args.length >= 2) {
                    puits = new Puits(10, 20, nbElements, nbLignes);
                } else if (args.length == 1) {
                    // Puits ne semble pas avoir de constructeur (largeur, profondeur, nbElements)
                    // mais on peut le faire manuellement ou utiliser le constructeur Tas
                    puits = new Puits(10, 20);
                    if (nbElements > 0) {
                        puits.setTas(new fr.eseo.e3.poo.projet.blox.modele.Tas(puits, nbElements));
                    }
                } else {
                    puits = new Puits(10, 20);
                }

                // 2. Création des vues
                VuePuits vuePuits = new VuePuits(puits, 25);
                PanneauInformation panneauInfo = new PanneauInformation(puits);

                // 3. Création de la fenêtre
                JFrame frame = new JFrame("Falling Blox");
                frame.setLayout(new BorderLayout());
                frame.add(vuePuits, BorderLayout.CENTER);
                frame.add(panneauInfo, BorderLayout.EAST);

                frame.setResizable(false);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);

                // 4. Initialisation du jeu
                puits.setPieceSuivante(UsineDePiece.genererTetromino());
                puits.setPieceSuivante(UsineDePiece.genererTetromino());

                // 5. Timer pour la chute
                Timer timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (puits.getPieceActuelle() != null) {
                            try {
                                puits.getPieceActuelle().deplacerDe(0, 1);
                            } catch (IllegalArgumentException | BloxException ex) {
                                // Gérer collision / fin de chute
                            }
                            vuePuits.repaint();
                        }
                    }
                });
                timer.start();
            }
        });
    }
}
