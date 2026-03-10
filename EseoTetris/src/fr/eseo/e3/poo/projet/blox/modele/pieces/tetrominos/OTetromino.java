package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import fr.eseo.e3.poo.projet.blox.modele.Coordonnees;
import fr.eseo.e3.poo.projet.blox.modele.Element;
import fr.eseo.e3.poo.projet.blox.modele.Puits;

public class OTetromino extends Tetromino{
	
	private Element[] elements;
	
	public OTetromino(Coordonnees coordonnes, Couleur couleur ) {
        // On initialise le tableau pour qu'il puisse contenir exactement 4 cases
        this.elements = new Element[4];
	}

	public Element[] getElements() {
		return elements;
	}

	public void setElements(Element[] elements) {
		this.elements = elements;
	}
    
	
}
