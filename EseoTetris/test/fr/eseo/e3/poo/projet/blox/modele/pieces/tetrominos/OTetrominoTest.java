package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

        assertNotNull(oTetromino.getElements());
        assertEquals(4, oTetromino.getElements().length);

        Element[] elements = oTetromino.getElements();
        assertEquals(6, elements[0].getCoordonnees().getAbscisse());
        assertEquals(5, elements[0].getCoordonnees().getOrdonnee());
    }

    @Test
    public void testDeplacerDeValide() {
        OTetromino o = new OTetromino(new Coordonnees(5, 5), Couleur.ROUGE);
        o.deplacerDe(1, 0); // Droite
        assertEquals(6, o.getElements()[0].getCoordonnees().getAbscisse());
        o.deplacerDe(-1, 0); // Gauche
        assertEquals(5, o.getElements()[0].getCoordonnees().getAbscisse());
        o.deplacerDe(0, 1); // Bas
        assertEquals(6, o.getElements()[0].getCoordonnees().getOrdonnee());
    }

    @Test
    public void testDeplacerDeInvalide() {
        OTetromino o = new OTetromino(new Coordonnees(5, 5), Couleur.ROUGE);
        assertThrows(IllegalArgumentException.class, () -> o.deplacerDe(0, -1), "Haut interdit");
        assertThrows(IllegalArgumentException.class, () -> o.deplacerDe(2, 0), "Trop loin interdit");
        assertThrows(IllegalArgumentException.class, () -> o.deplacerDe(1, 1), "Diagonal interdit");
    }

    @Test
    public void testTourner() {
        OTetromino o = new OTetromino(new Coordonnees(5, 5), Couleur.ROUGE);
        Coordonnees initial0 = new Coordonnees(o.getElements()[0].getCoordonnees().getAbscisse(), o.getElements()[0].getCoordonnees().getOrdonnee());
        Coordonnees initial1 = new Coordonnees(o.getElements()[1].getCoordonnees().getAbscisse(), o.getElements()[1].getCoordonnees().getOrdonnee());
        
        o.tourner(true);
        assertEquals(initial0.getAbscisse(), o.getElements()[0].getCoordonnees().getAbscisse());
        assertEquals(initial0.getOrdonnee(), o.getElements()[0].getCoordonnees().getOrdonnee());
        assertEquals(initial1.getAbscisse(), o.getElements()[1].getCoordonnees().getAbscisse());
        assertEquals(initial1.getOrdonnee(), o.getElements()[1].getCoordonnees().getOrdonnee());
    }

    @Test
    public void testToString() {
        Coordonnees coords = new Coordonnees(6, 5);
        Couleur couleur = Couleur.CYAN;
        OTetromino oTetromino = new OTetromino(coords, couleur);
        String s = oTetromino.toString();
        assertTrue(s.contains("OTetromino"));
        assertTrue(s.contains("(6, 5)"));
    }
    
    private void assertTrue(boolean condition) {
        if(!condition) throw new AssertionError();
    }
}
