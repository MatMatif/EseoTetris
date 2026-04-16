package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import fr.eseo.e3.poo.projet.blox.modele.Coordonnees;
import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.Element;

public class STetromino extends Tetromino {
    public STetromino(Coordonnees coordonnees, Couleur couleur) {
        super(coordonnees, couleur);
    }

    @Override
    protected void setElements(Coordonnees coordonnees, Couleur couleur) {
        int x = coordonnees.getAbscisse();
        int y = coordonnees.getOrdonnee();
        getElements().clear();
        getElements().add(new Element(x, y, couleur));
        getElements().add(new Element(x + 1, y - 1, couleur));
        getElements().add(new Element(x, y - 1, couleur));
        getElements().add(new Element(x - 1, y, couleur));
    }
}
