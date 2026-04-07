package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import fr.eseo.e3.poo.projet.blox.modele.Coordonnees;
import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.Element;

public class OTetromino extends Tetromino {

    public OTetromino(Coordonnees coordonnees, Couleur couleur) {
        super(coordonnees, couleur);
    }

    @Override
    protected void setElements(Coordonnees coordonnees, Couleur couleur) {
        int x = coordonnees.getAbscisse();
        int y = coordonnees.getOrdonnee();

        getElements()[0] = new Element(x, y, couleur);
        getElements()[1] = new Element(x + 1, y, couleur);
        getElements()[2] = new Element(x, y - 1, couleur);
        getElements()[3] = new Element(x + 1, y - 1, couleur);
    }

    @Override
    public void tourner(boolean sensHoraire) {
        // Le OTetromino ne tourne pas
    }
}
