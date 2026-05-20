package fr.eseo.e3.poo.projet.blox.controleur;

import fr.eseo.e3.poo.projet.blox.modele.BloxException;
import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PieceClavier extends KeyAdapter {

    private final VuePuits vuePuits;
    private Puits puits;

    /**
     * @param vuePuits la vue du puits associée
     */
    public PieceClavier(VuePuits vuePuits) {
        this.vuePuits = vuePuits;
        this.puits = vuePuits.getPuits();
    }

    /**
     * @param puits le nouveau puits à contrôler
     */
    public void setPuits(Puits puits) {
        this.puits = puits;
    }

    /**
     * @param e l'événement clavier
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (this.puits == null || this.puits.getPieceActuelle() == null) {
            return;
        }

        try {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    this.puits.getPieceActuelle().deplacerDe(-1, 0);
                    break;
                case KeyEvent.VK_RIGHT:
                    this.puits.getPieceActuelle().deplacerDe(1, 0);
                    break;
                case KeyEvent.VK_DOWN:
                    this.puits.getPieceActuelle().deplacerDe(0, 1);
                    break;
                case KeyEvent.VK_UP:
                    this.puits.getPieceActuelle().tourner(true);
                    break;
                default:
                    return;
            }
            this.vuePuits.repaint();
        } catch (BloxException | IllegalArgumentException ex) {
            throw new RuntimeException(ex);
        }
    }
}
