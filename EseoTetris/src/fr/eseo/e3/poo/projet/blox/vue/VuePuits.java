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

public class VuePuits extends JPanel implements PropertyChangeListener {
    public static final int TAILLE_PAR_DEFAUT = 15;

    private Puits puits;
    private int taille;
    private VuePiece vuePiece;

    public VuePuits(Puits puits) {
        this(puits, TAILLE_PAR_DEFAUT);
    }

    public VuePuits(Puits puits, int taille) {
        this.taille = taille;
        this.setPuits(puits);
        this.setBackground(Color.WHITE);
        this.vuePiece = null;
    }

    public Puits getPuits() {
        return puits;
    }

    public void setPuits(Puits puits) {
        if (this.puits != null) {
            this.puits.removePropertyChangeListener(this);
        }
        this.puits = puits;
        if (this.puits != null) {
            this.puits.addPropertyChangeListener(this);
            this.updatePreferredSize();
        }
    }

    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
        this.updatePreferredSize();
    }

    public VuePiece getVuePiece() {
        return vuePiece;
    }

    private void setVuePiece(VuePiece vuePiece) {
        this.vuePiece = vuePiece;
    }

    private void updatePreferredSize() {
        if (this.puits != null) {
            this.setPreferredSize(new Dimension(puits.getLargeur() * taille, puits.getProfondeur() * taille));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g.create();
        g2D.setColor(Color.LIGHT_GRAY);
        
        if (this.puits != null) {
            for (int x = 0; x <= puits.getLargeur(); x++) {
                g2D.drawLine(x * taille, 0, x * taille, puits.getProfondeur() * taille);
            }
            for (int y = 0; y <= puits.getProfondeur(); y++) {
                g2D.drawLine(0, y * taille, puits.getLargeur() * taille, y * taille);
            }
        }

        if (this.vuePiece != null) {
            this.vuePiece.afficherPiece(g2D);
        }
        g2D.dispose();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (Puits.MODIFICATION_PIECE_ACTUELLE.equals(evt.getPropertyName())) {
            Piece nouvellePiece = (Piece) evt.getNewValue();
            if (nouvellePiece != null) {
                this.setVuePiece(new VuePiece(nouvellePiece, this.taille));
            } else {
                this.setVuePiece(null);
            }
            this.repaint();
        }
    }
}
