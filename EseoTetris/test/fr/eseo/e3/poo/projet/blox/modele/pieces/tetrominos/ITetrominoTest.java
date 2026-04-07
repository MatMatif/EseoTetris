package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

        assertNotNull(iTetromino.getElements(), "Le tableau d'éléments ne doit pas être null");
        assertEquals(4, iTetromino.getElements().length, "Il doit y avoir 4 éléments");

        Element[] elements = iTetromino.getElements();

        assertEquals(5, elements[0].getCoordonnees().getAbscisse());
        assertEquals(5, elements[0].getCoordonnees().getOrdonnee());
        assertEquals(couleur, elements[0].getCouleur());

        assertEquals(5, elements[1].getCoordonnees().getAbscisse());
        assertEquals(4, elements[1].getCoordonnees().getOrdonnee());

        assertEquals(5, elements[2].getCoordonnees().getAbscisse());
        assertEquals(3, elements[2].getCoordonnees().getOrdonnee());

        assertEquals(5, elements[3].getCoordonnees().getAbscisse());
        assertEquals(6, elements[3].getCoordonnees().getOrdonnee());
    }

    @Test
    public void testSetPosition() {
        ITetromino iTetromino = new ITetromino(new Coordonnees(5, 5), Couleur.CYAN);
        iTetromino.setPosition(10, 10);
        
        assertEquals(10, iTetromino.getElements()[0].getCoordonnees().getAbscisse());
        assertEquals(10, iTetromino.getElements()[0].getCoordonnees().getOrdonnee());
        assertEquals(10, iTetromino.getElements()[3].getCoordonnees().getAbscisse());
        assertEquals(11, iTetromino.getElements()[3].getCoordonnees().getOrdonnee());
    }

    @Test
    public void testToString() {
        ITetromino iTetromino = new ITetromino(new Coordonnees(5, 5), Couleur.CYAN);
        String expected = "ITetromino :\n" +
                "\t(5, 5) - CYAN\n" +
                "\t(5, 4) - CYAN\n" +
                "\t(5, 3) - CYAN\n" +
                "\t(5, 6) - CYAN\n";
        assertEquals(expected, iTetromino.toString());
    }
}
