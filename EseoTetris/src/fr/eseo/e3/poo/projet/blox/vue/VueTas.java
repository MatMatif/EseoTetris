package fr.eseo.e3.poo.projet.blox.vue;

import java.awt.Color;
import java.awt.Graphics2D;

import fr.eseo.e3.poo.projet.blox.modele.Element;
import fr.eseo.e3.poo.projet.blox.modele.Tas;

public class VueTas {

    private final Tas tas;
    private final int taille;

    public VueTas(Tas tas, int taille) {
        this.tas = tas;
        this.taille = taille;
    }
    
    public void afficher(Graphics2D g2D) {
        if (this.tas != null) {
            for (Element e : this.tas.getElements()) {
                Color couleurBase = e.getCouleur().getCouleurPourAffichage();
                g2D.setColor(couleurBase);
                g2D.fill3DRect(e.getCoordonnees().getAbscisse() * this.taille,
                               e.getCoordonnees().getOrdonnee() * this.taille,
                               this.taille, this.taille, true);
            }
        }
    }
}
