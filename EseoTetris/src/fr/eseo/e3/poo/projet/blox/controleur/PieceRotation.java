package fr.eseo.e3.poo.projet.blox.controleur;

import fr.eseo.e3.poo.projet.blox.modele.BloxException;
import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

import javax.swing.SwingUtilities;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PieceRotation extends MouseAdapter {

    private VuePuits vuePuits;
    private Puits puits;

    public PieceRotation(VuePuits vuePuits) {
        this.vuePuits = vuePuits;
        this.puits = vuePuits.getPuits();
    }

    public void setPuits(Puits puits) {
        this.puits = puits;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (this.puits != null && this.puits.getPieceActuelle() != null) {
            try {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    this.puits.getPieceActuelle().tourner(false);
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    this.puits.getPieceActuelle().tourner(true);
                }
                this.vuePuits.repaint();
            } catch (BloxException ex) {
                // Rotation impossible (collision ou sortie)
            }
        }
    }
}
