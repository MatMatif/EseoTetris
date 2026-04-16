package fr.eseo.e3.poo.projet.blox.vue;

import fr.eseo.e3.poo.projet.blox.modele.Element;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;

import java.awt.Color;
import java.awt.Graphics2D;

public class VuePiece {
    public static final double MULTIPLIER_TEINTE = 0.5;

    private final Piece piece;
    private final int taille;

    public VuePiece(Piece piece, int taille) {
        this.piece = piece;
        this.taille = taille;
    }

    public Color teinte(Color couleur) {
        int r = couleur.getRed();
        int g = couleur.getGreen();
        int b = couleur.getBlue();

        r = (int) (r + (255 - r) * MULTIPLIER_TEINTE);
        g = (int) (g + (255 - g) * MULTIPLIER_TEINTE);
        b = (int) (b + (255 - b) * MULTIPLIER_TEINTE);

        return new Color(r, g, b);
    }

    protected void afficherPiece(Graphics2D g2D) {
        java.util.List<Element> elements = piece.getElements();
        for (int i = 0; i < elements.size(); i++) {
            Color couleurBase = elements.get(i).getCouleur().getCouleurPourAffichage();
            if (i == 0) {
                g2D.setColor(teinte(couleurBase));
            } else {
                g2D.setColor(couleurBase);
            }
            g2D.fill3DRect(elements.get(i).getCoordonnees().getAbscisse() * taille,
                           elements.get(i).getCoordonnees().getOrdonnee() * taille,
                           taille, taille, true);
        }
    }
}
