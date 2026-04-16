package fr.eseo.e3.poo.projet.blox.vue;

import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PanneauInformation extends JPanel implements PropertyChangeListener {
    private Puits puits;
    private VuePiece vuePiece;

    public PanneauInformation(Puits puits) {
        this.setPuits(puits);
        this.setPreferredSize(new Dimension(8 * VuePuits.TAILLE_PAR_DEFAUT, 8 * VuePuits.TAILLE_PAR_DEFAUT));
    }

    public void setPuits(Puits puits) {
        if (this.puits != null) {
            this.puits.removePropertyChangeListener(this);
        }
        this.puits = puits;
        if (this.puits != null) {
            this.puits.addPropertyChangeListener(this);
            if (this.puits.getPieceSuivante() != null) {
                this.vuePiece = new VuePiece(this.puits.getPieceSuivante(), VuePuits.TAILLE_PAR_DEFAUT);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.vuePiece != null) {
            Graphics2D g2D = (Graphics2D) g.create();
            // Centrage approximatif de la pièce suivante dans le panneau
            g2D.translate(this.getWidth() / 4, this.getHeight() / 2);
            this.vuePiece.afficherPiece(g2D);
            g2D.dispose();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (Puits.MODIFICATION_PIECE_SUIVANTE.equals(evt.getPropertyName())) {
            Piece nouvellePiece = (Piece) evt.getNewValue();
            if (nouvellePiece != null) {
                this.vuePiece = new VuePiece(nouvellePiece, VuePuits.TAILLE_PAR_DEFAUT);
            } else {
                this.vuePiece = null;
            }
            this.repaint();
        }
    }
}
