package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import fr.eseo.e3.poo.projet.blox.modele.BloxException;
import fr.eseo.e3.poo.projet.blox.modele.Coordonnees;
import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.Element;

public class OTetromino extends Tetromino {

    /**
     * @param coordonnees les coordonnées de la pièce
     * @param couleur la couleur de la pièce
     */
    public OTetromino(Coordonnees coordonnees, Couleur couleur) {
        super(coordonnees, couleur);
    }

    /**
     * @param coordonnees les coordonnées de référence
     * @param couleur la couleur des éléments
     */
    @Override
    protected void setElements(Coordonnees coordonnees, Couleur couleur) {
        int x = coordonnees.getAbscisse();
        int y = coordonnees.getOrdonnee();

        getElements().clear();
        getElements().add(new Element(x, y, couleur));
        getElements().add(new Element(x + 1, y, couleur));
        getElements().add(new Element(x, y - 1, couleur));
        getElements().add(new Element(x + 1, y - 1, couleur));
    }

    /**
     * La rotation d'un OTetromino est sans effet.
     * La méthode est surchargée pour ne rien faire.
     * @param sensHoraire non utilisé
     */
    @Override
    public void tourner(boolean sensHoraire) throws BloxException {
        // La rotation d'un bloc carré n'a aucun effet. L'implémentation est donc vide.
    }
}
