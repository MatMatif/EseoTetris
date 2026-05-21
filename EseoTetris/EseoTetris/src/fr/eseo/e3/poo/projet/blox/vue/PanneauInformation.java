package fr.eseo.e3.poo.projet.blox.vue;

import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PanneauInformation extends JPanel implements PropertyChangeListener {
    private Puits puits;
    private VuePiece vuePiece;
    private JLabel scoreLabel;

    public PanneauInformation(Puits puits) {
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(70, 70));
        
        scoreLabel = new JLabel("Score: 0", JLabel.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        this.add(scoreLabel, BorderLayout.NORTH);
        
        this.setPuits(puits);
    }

    public void setPuits(Puits puits) {
        if (this.puits != null) {
            this.puits.removePropertyChangeListener(this);
        }
        this.puits = puits;
        if (this.puits != null) {
            this.puits.addPropertyChangeListener(this);
            updateScore(this.puits.getScore());
            if (this.puits.getPieceSuivante() != null) {
                this.vuePiece = new VuePiece(this.puits.getPieceSuivante(), 10);
            }
        }
    }
    
    private void updateScore(int score) {
        this.scoreLabel.setText("Score: " + score);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.vuePiece != null) {
            Graphics2D g2D = (Graphics2D) g.create();
            // Centrer la pièce dans l'espace disponible
            g2D.translate(this.getWidth() / 2 - (10 * 2), this.getHeight() / 2 - 10);
            this.vuePiece.afficherPiece(g2D);
            g2D.dispose();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (Puits.MODIFICATION_PIECE_SUIVANTE.equals(evt.getPropertyName())) {
            Piece nouvellePiece = (Piece) evt.getNewValue();
            if (nouvellePiece != null) {
                this.vuePiece = new VuePiece(nouvellePiece, 10);
            } else {
                this.vuePiece = null;
            }
            this.repaint();
        } else if (Puits.MODIFICATION_SCORE.equals(evt.getPropertyName())) {
            updateScore((int)evt.getNewValue());
        }
    }
}
