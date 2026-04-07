package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import fr.eseo.e3.poo.projet.blox.modele.Coordonnees;
import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.Element;
import org.junit.jupiter.api.Test;

public class OTetrominoTest {

    @Test
    public void testConstructeur() {
        Coordonnees coords = new Coordonnees(6, 5);
        Couleur couleur = Couleur.CYAN;
        OTetromino oTetromino = new OTetromino(coords, couleur);

        assertNotNull(oTetromino.getElements(), "Le tableau d'éléments ne doit pas être null");
        assertEquals(4, oTetromino.getElements().length, "Il doit y avoir 4 éléments");

        Element[] elements = oTetromino.getElements();

        // Vérification de l'élément de référence
        assertEquals(6, elements[0].getCoordonnees().getAbscisse());
        assertEquals(5, elements[0].getCoordonnees().getOrdonnee());
        assertEquals(couleur, elements[0].getCouleur());

        // Vérification des autres éléments formant le carré
        assertEquals(7, elements[1].getCoordonnees().getAbscisse());
        assertEquals(5, elements[1].getCoordonnees().getOrdonnee());

        assertEquals(6, elements[2].getCoordonnees().getAbscisse());
        assertEquals(4, elements[2].getCoordonnees().getOrdonnee());

        assertEquals(7, elements[3].getCoordonnees().getAbscisse());
        assertEquals(4, elements[3].getCoordonnees().getOrdonnee());
    }

    @Test
    public void testToString() {
        Coordonnees coords = new Coordonnees(6, 5);
        Couleur couleur = Couleur.CYAN;
        OTetromino oTetromino = new OTetromino(coords, couleur);

        String expected = "OTetromino :\n" +
                "\t(6, 5) - CYAN\n" +
                "\t(7, 5) - CYAN\n" +
                "\t(6, 4) - CYAN\n" +
                "\t(7, 4) - CYAN\n";

        assertEquals(expected, oTetromino.toString(), "Le format de toString est incorrect");
    }
}
