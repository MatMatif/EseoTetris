package fr.eseo.e3.poo.projet.blox.vue;

import fr.eseo.e3.poo.projet.blox.modele.Element;
import fr.eseo.e3.poo.projet.blox.modele.Tas;

import java.awt.Color;
import java.awt.Graphics2D;

public class VueTas {

    public static final double MULTIPLIER_NUANCE = 0.4;
    private final Tas tas;
    private final VuePuits vuePuits;
    
    public VueTas(VuePuits vuePuits) {
        this.vuePuits = vuePuits;
        this.tas = vuePuits.getPuits().getTas();
    }

    public VuePuits getVuePuits() {
        return this.vuePuits;
    }

    public Color nuance(Color couleur) {
        int r = couleur.getRed();
        int g = couleur.getGreen();
        int b = couleur.getBlue();

        r = (int) (r * (1 - MULTIPLIER_NUANCE));
        g = (int) (g * (1 - MULTIPLIER_NUANCE));
        b = (int) (b * (1 - MULTIPLIER_NUANCE));

        return new Color(r, g, b);
    }
    
    public void afficher(Graphics2D g2D) {
        int taille = this.vuePuits.getTaille();

        if (this.tas != null) {
            for (Element element : this.tas.getElements()) {
                if (element != null) {
                    Color couleurBase = element.getCouleur().getCouleurPourAffichage();
                    g2D.setColor(nuance(couleurBase));
                    g2D.fill3DRect(
                        element.getCoordonnees().getAbscisse() * taille,
                        element.getCoordonnees().getOrdonnee() * taille,
                        taille, 
                        taille, 
                        false // Effet en dessous de la surface
                    );
                }
            }
        }
    }
}
