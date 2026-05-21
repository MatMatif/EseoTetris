package fr.eseo.e3.poo.projet.blox.vue;

import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PanneauInformation extends JPanel implements PropertyChangeListener {
    private Puits puits;
    private VuePiece vuePieceSuivante;
    private VuePiece vuePieceStockee;  // EXTENSION (section 4.1) : Hold
    private JLabel scoreLabel;
    private JLabel lignesLabel; // EXTENSION (section 4.1) : palier de vitesse

    public PanneauInformation(Puits puits) {
        this.setLayout(new BorderLayout());
        // Section 3.8.5 du PDF demande 70 x 70 pour la pièce suivante.
        // On agrandit le panneau pour les EXTENSIONS (section 4.1) :
        // score, pièce stockée, compteur de lignes complétées.
        this.setPreferredSize(new Dimension(140, 260));

        scoreLabel = new JLabel("Score : 0", JLabel.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 14));
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        lignesLabel = new JLabel("Lignes : 0", JLabel.CENTER);
        lignesLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        lignesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel haut = new JPanel();
        haut.setLayout(new BoxLayout(haut, BoxLayout.Y_AXIS));
        haut.setOpaque(false);
        haut.add(scoreLabel);
        haut.add(Box.createVerticalStrut(2));
        haut.add(lignesLabel);
        this.add(haut, BorderLayout.NORTH);

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
            updateLignes(this.puits.getNbLignesCompletes());
            if (this.puits.getPieceSuivante() != null) {
                this.vuePieceSuivante = new VuePiece(this.puits.getPieceSuivante(), 10);
            }
            if (this.puits.getPieceStockee() != null) {
                this.vuePieceStockee = new VuePiece(this.puits.getPieceStockee(), 10);
            }
        }
    }

    private void updateScore(int score) {
        this.scoreLabel.setText("Score : " + score);
        this.scoreLabel.repaint();
        this.revalidate();
    }

    /**
     * EXTENSION (section 4.1) — affiche le compteur de lignes complétées
     * et le palier courant (chaque palier de 10 lignes accélère la gravité).
     */
    private void updateLignes(int nbLignes) {
        int palier = nbLignes / 10;
        this.lignesLabel.setText("Lignes : " + nbLignes + "  (palier " + palier + ")");
        this.lignesLabel.repaint();
        this.revalidate();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g.create();
        g2D.setColor(Color.DARK_GRAY);
        g2D.setFont(new Font("Arial", Font.PLAIN, 11));

        // --- Pièce suivante (centre haut) ---
        int yLabelSuivante = 60;
        g2D.drawString("Suivante :", 10, yLabelSuivante);
        if (this.vuePieceSuivante != null) {
            Graphics2D gp = (Graphics2D) g2D.create();
            gp.translate(this.getWidth() / 2 - 20, yLabelSuivante + 10);
            this.vuePieceSuivante.afficherPiece(gp);
            gp.dispose();
        }

        // --- Pièce stockée (centre bas) — EXTENSION (section 4.1) ---
        int yLabelStockee = 150;
        g2D.drawString("Stockée (C) :", 10, yLabelStockee);
        if (this.vuePieceStockee != null) {
            Graphics2D gp = (Graphics2D) g2D.create();
            gp.translate(this.getWidth() / 2 - 20, yLabelStockee + 10);
            this.vuePieceStockee.afficherPiece(gp);
            gp.dispose();
        }

        g2D.dispose();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (Puits.MODIFICATION_PIECE_SUIVANTE.equals(evt.getPropertyName())) {
            Piece nouvellePiece = (Piece) evt.getNewValue();
            this.vuePieceSuivante = (nouvellePiece != null) ? new VuePiece(nouvellePiece, 10) : null;
            this.repaint();
        } else if (Puits.MODIFICATION_PIECE_STOCKEE.equals(evt.getPropertyName())) {
            // EXTENSION (section 4.1) : Hold
            Piece nouvellePiece = (Piece) evt.getNewValue();
            this.vuePieceStockee = (nouvellePiece != null) ? new VuePiece(nouvellePiece, 10) : null;
            this.repaint();
        } else if (Puits.MODIFICATION_SCORE.equals(evt.getPropertyName())) {
            updateScore((int) evt.getNewValue());
        } else if (Puits.MODIFICATION_LIGNES_COMPLETES.equals(evt.getPropertyName())) {
            // EXTENSION (section 4.1) : palier de vitesse
            updateLignes((int) evt.getNewValue());
        }
    }
}
