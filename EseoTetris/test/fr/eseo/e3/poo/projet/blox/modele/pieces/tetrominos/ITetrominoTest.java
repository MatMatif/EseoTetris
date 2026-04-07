package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fr.eseo.e3.poo.projet.blox.modele.Coordonnees;
import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.Element;
import org.junit.jupiter.api.Test;

public class ITetrominoTest {

    @Test
    public void testConstructeur() {
        Coordonnees coords = new Coordonnees(5, 5);
        Couleur couleur = Couleur.CYAN;
        ITetromino iTetromino = new ITetromino(coords, couleur);

        assertNotNull(iTetromino.getElements());
        assertEquals(4, iTetromino.getElements().length);

        Element[] elements = iTetromino.getElements();
        assertEquals(5, elements[0].getCoordonnees().getAbscisse());
        assertEquals(5, elements[0].getCoordonnees().getOrdonnee());
    }

    @Test
    public void testDeplacerDeValide() {
        ITetromino i = new ITetromino(new Coordonnees(5, 5), Couleur.CYAN);
        i.deplacerDe(1, 0); // Droite
        assertEquals(6, i.getElements()[0].getCoordonnees().getAbscisse());
        i.deplacerDe(0, 1); // Bas
        assertEquals(6, i.getElements()[0].getCoordonnees().getOrdonnee());
    }

    @Test
    public void testDeplacerDeInvalide() {
        ITetromino i = new ITetromino(new Coordonnees(5, 5), Couleur.CYAN);
        assertThrows(IllegalArgumentException.class, () -> i.deplacerDe(0, -1), "Haut interdit");
        assertThrows(IllegalArgumentException.class, () -> i.deplacerDe(-1, 1), "Diagonal interdit");
    }

    @Test
    public void testTournerHoraire() {
        ITetromino i = new ITetromino(new Coordonnees(5, 5), Couleur.CYAN);
        // Initiale : (5,5), (5,4), (5,3), (5,6)
        i.tourner(true);
        // Attendue (xRef - yRel, yRef + xRel) : (5,5), (6,5), (7,5), (4,5)
        assertEquals(5, i.getElements()[0].getCoordonnees().getAbscisse());
        assertEquals(5, i.getElements()[0].getCoordonnees().getOrdonnee());
        
        assertEquals(6, i.getElements()[1].getCoordonnees().getAbscisse());
        assertEquals(5, i.getElements()[1].getCoordonnees().getOrdonnee());
        
        assertEquals(7, i.getElements()[2].getCoordonnees().getAbscisse());
        assertEquals(5, i.getElements()[2].getCoordonnees().getOrdonnee());
        
        assertEquals(4, i.getElements()[3].getCoordonnees().getAbscisse());
        assertEquals(5, i.getElements()[3].getCoordonnees().getOrdonnee());
    }

    @Test
    public void testSetPosition() {
        ITetromino iTetromino = new ITetromino(new Coordonnees(5, 5), Couleur.CYAN);
        iTetromino.setPosition(10, 10);
        assertEquals(10, iTetromino.getElements()[0].getCoordonnees().getAbscisse());
        assertEquals(10, iTetromino.getElements()[0].getCoordonnees().getOrdonnee());
    }
}
