package fr.eseo.e3.poo.projet.blox.vue;

import fr.eseo.e3.poo.projet.blox.controleur.PieceClavier;
import fr.eseo.e3.poo.projet.blox.controleur.PieceDeplacement;
import fr.eseo.e3.poo.projet.blox.controleur.PieceRotation;
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
    private VueTas vueTas;
    private PieceRotation pieceRotation;
    private PieceDeplacement pieceDeplacement;
    private PieceClavier pieceClavier;

    public VuePuits(Puits puits) {
        this(puits, TAILLE_PAR_DEFAUT);
    }

    public VuePuits(Puits puits, int taille) {
        this.taille = taille;
        this.setBackground(Color.WHITE);

        this.pieceRotation = new PieceRotation(this);
        this.addMouseListener(this.pieceRotation);
        
        this.pieceDeplacement = new PieceDeplacement(this);
        this.addMouseListener(this.pieceDeplacement);
        this.addMouseMotionListener(this.pieceDeplacement);
        this.addMouseWheelListener(this.pieceDeplacement);

        // EXTENSION (section 4.1) : contrôle au clavier en complément de la souris
        this.pieceClavier = new PieceClavier(this);
        this.addKeyListener(this.pieceClavier);
        this.setFocusable(true);
        // Récupérer le focus dès qu'on clique sur le panneau pour que
        // les événements clavier soient bien captés
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                VuePuits.this.requestFocusInWindow();
            }
        });

        this.setPuits(puits);
    }

    public void setPuits(Puits puits) {
        if (this.puits != null) {
            this.puits.removePropertyChangeListener(this);
        }
        this.puits = puits;
        if (this.pieceRotation != null) {
            this.pieceRotation.setPuits(puits);
        }
        if (this.puits != null) {
            this.puits.addPropertyChangeListener(this);
            this.updatePreferredSize();

            if (this.puits.getTas() != null) {
                this.vueTas = new VueTas(this);
            } else {
                this.vueTas = null;
            }

            if (this.puits.getPieceActuelle() != null) {
                this.setVuePiece(new VuePiece(this.puits.getPieceActuelle(), this.taille));
            }
        }
        this.repaint();
    }

    public Puits getPuits() {
        return puits;
    }

    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
        this.updatePreferredSize();
        if (this.puits != null) {
            if (this.puits.getTas() != null) {
                this.vueTas = new VueTas(this);
            }
            if (this.puits.getPieceActuelle() != null) {
                 this.setVuePiece(new VuePiece(this.puits.getPieceActuelle(), this.taille));
            }
        }
        this.repaint();
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

            if (this.vueTas != null) {
                this.vueTas.afficher(g2D);
            }
        }

        if (this.vuePiece != null) {
            this.vuePiece.afficherPiece(g2D);
        }

        if (this.puits != null && this.puits.isFinPartie()) {
            g2D.setColor(new Color(0, 0, 0, 150));
            g2D.fillRect(0, 0, getWidth(), getHeight());
            g2D.setColor(Color.WHITE);
            String msg = "GAME OVER";
            int strWidth = g2D.getFontMetrics().stringWidth(msg);
            g2D.drawString(msg, (getWidth() - strWidth) / 2, getHeight() / 2);
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
        } else if (Puits.MODIFICATION_PIECE_SUIVANTE.equals(evt.getPropertyName()) ||
                   Puits.MODIFICATION_FIN_PARTIE.equals(evt.getPropertyName())) {
            this.repaint();
        }
    }
}
