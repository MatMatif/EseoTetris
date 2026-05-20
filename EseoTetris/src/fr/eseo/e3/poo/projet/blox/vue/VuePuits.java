package fr.eseo.e3.poo.projet.blox.vue;

import fr.eseo.e3.poo.projet.blox.controleur.PieceClavier;
import fr.eseo.e3.poo.projet.blox.controleur.PieceRotation;
import fr.eseo.e3.poo.projet.blox.modele.Element;
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
    private PieceRotation pieceRotation;
    private PieceClavier pieceClavier;

    /**
     * @param puits le modèle du puits de jeu à afficher
     */
    public VuePuits(Puits puits) {
        this(puits, TAILLE_PAR_DEFAUT);
    }

    /**
     * @param puits le modèle du puits de jeu à afficher
     * @param taille la taille en pixels de chaque cellule du puits
     */
    public VuePuits(Puits puits, int taille) {
        this.taille = taille;
        this.setBackground(Color.WHITE);

        this.pieceRotation = new PieceRotation(this);
        this.addMouseListener(this.pieceRotation);

        this.pieceClavier = new PieceClavier(this);
        this.addKeyListener(this.pieceClavier);
        this.setFocusable(true);

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
        if (this.pieceClavier != null) {
            this.pieceClavier.setPuits(puits);
        }
        if (this.puits != null) {
            this.puits.addPropertyChangeListener(this);
            this.updatePreferredSize();
            if (this.puits.getPieceActuelle() != null) {
                this.setVuePiece(new VuePiece(this.puits.getPieceActuelle(), this.taille));
            }
        }
    }

    public Puits getPuits() {
        return puits;
    }

    public int getTaille() {
        return taille;
    }

    /**
     * @param taille la nouvelle dimension en pixels pour les cellules du puits
     */
    public void setTaille(int taille) {
        this.taille = taille;
        this.updatePreferredSize();
    }

    public VuePiece getVuePiece() {
        return vuePiece;
    }

    /**
     * @param vuePiece la nouvelle représentation graphique de la pièce à afficher
     */
    private void setVuePiece(VuePiece vuePiece) {
        this.vuePiece = vuePiece;
    }

    private void updatePreferredSize() {
        if (this.puits != null) {
            this.setPreferredSize(new Dimension(puits.getLargeur() * taille, puits.getProfondeur() * taille));
        }
    }

    /**
     * @param g le contexte graphique utilisé pour dessiner le puits et son contenu
     */
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

            if (this.puits.getTas() != null) {
                for (Element e : this.puits.getTas().getElements()) {
                    Color couleurBase = e.getCouleur().getCouleurPourAffichage();
                    g2D.setColor(couleurBase);
                    g2D.fill3DRect(e.getCoordonnees().getAbscisse() * taille,
                                   e.getCoordonnees().getOrdonnee() * taille,
                                   taille, taille, true);
                }
            }
        }

        if (this.vuePiece != null) {
            this.vuePiece.afficherPiece(g2D);
        }
        g2D.dispose();
    }

    /**
     * @param evt l'événement de modification de propriété provenant du modèle (puits)
     */
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
