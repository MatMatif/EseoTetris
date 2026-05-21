package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fr.eseo.e3.poo.projet.blox.modele.BloxException;
import fr.eseo.e3.poo.projet.blox.modele.Coordonnees;
import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.Element;
import fr.eseo.e3.poo.projet.blox.modele.Puits;
import org.junit.jupiter.api.Test;

public class OTetrominoTest {

    @Test
    public void testConstructeur() {
        Coordonnees coords = new Coordonnees(6, 5);
        Couleur couleur = Couleur.CYAN;
        OTetromino oTetromino = new OTetromino(coords, couleur);

        assertNotNull(oTetromino.getElements());
        assertEquals(4, oTetromino.getElements().size());

        java.util.List<Element> elements = oTetromino.getElements();
        assertEquals(6, elements.get(0).getCoordonnees().getAbscisse());
        assertEquals(5, elements.get(0).getCoordonnees().getOrdonnee());
    }

    @Test
    public void testDeplacerDeValide() throws BloxException {
        OTetromino o = new OTetromino(new Coordonnees(5, 5), Couleur.CYAN);
        o.deplacerDe(1, 0); // Droite
        assertEquals(6, o.getElements().get(0).getCoordonnees().getAbscisse());
        o.deplacerDe(-1, 0); // Gauche
        assertEquals(5, o.getElements().get(0).getCoordonnees().getAbscisse());
        o.deplacerDe(0, 1); // Bas
        assertEquals(6, o.getElements().get(0).getCoordonnees().getOrdonnee());
    }

    @Test
    public void testDeplacerDeInvalide() throws BloxException {
        OTetromino o = new OTetromino(new Coordonnees(5, 5), Couleur.CYAN);
        // Haut (y < 0)
        assertThrows(IllegalArgumentException.class, () -> o.deplacerDe(0, -1), "Haut interdit");
        // Trop loin horizontalement (|x| > 1)
        assertThrows(IllegalArgumentException.class, () -> o.deplacerDe(2, 0), "Trop loin horizontalement");
        assertThrows(IllegalArgumentException.class, () -> o.deplacerDe(-2, 0), "Trop loin horizontalement");
        // Trop loin verticalement (y > 1)
        assertThrows(IllegalArgumentException.class, () -> o.deplacerDe(0, 2), "Trop loin verticalement");
        // Diagonal (|x| == 1 && y == 1)
        assertThrows(IllegalArgumentException.class, () -> o.deplacerDe(1, 1), "Diagonal interdit");
        assertThrows(IllegalArgumentException.class, () -> o.deplacerDe(-1, 1), "Diagonal interdit");
    }

    @Test
    public void testTourner() throws BloxException {
        Coordonnees initialCoords = new Coordonnees(5, 5);
        OTetromino o = new OTetromino(initialCoords, Couleur.CYAN);

        // Store initial state of all elements
        java.util.List<Coordonnees> initialElementCoords = new java.util.ArrayList<>();
        for (Element element : o.getElements()) {
            initialElementCoords.add(new Coordonnees(
                element.getCoordonnees().getAbscisse(),
                element.getCoordonnees().getOrdonnee()
            ));
        }

        // Call tourner() in one direction
        o.tourner(true);

        // Assert that nothing has changed
        for (int i = 0; i < o.getElements().size(); i++) {
            assertEquals(initialElementCoords.get(i), o.getElements().get(i).getCoordonnees(),
                "Les coordonnées de l'élément " + i + " ne devraient pas changer après rotation horaire.");
        }

        // Call tourner() in the other direction
        o.tourner(false);

        // Assert that nothing has changed
        for (int i = 0; i < o.getElements().size(); i++) {
            assertEquals(initialElementCoords.get(i), o.getElements().get(i).getCoordonnees(),
                "Les coordonnées de l'élément " + i + " ne devraient pas changer après rotation anti-horaire.");
        }
    }

    @Test
    public void testToString() {
        Coordonnees coords = new Coordonnees(6, 5);
        Couleur couleur = Couleur.CYAN;
        OTetromino oTetromino = new OTetromino(coords, couleur);
        String s = oTetromino.toString();
        org.junit.jupiter.api.Assertions.assertTrue(s.contains("OTetromino"));
        org.junit.jupiter.api.Assertions.assertTrue(s.contains("(6, 5)"));
    }

    @Test
    public void testCollisionPuits() {
        Puits p = new Puits(10, 20);
        OTetromino o = new OTetromino(new Coordonnees(8, 5), Couleur.CYAN);
        o.setPuits(p);
        // Sortie droite (x=8, element1 at x=9, next move x=10)
        assertThrows(BloxException.class, () -> o.deplacerDe(1, 0));
        // Collision fond
        o.setPosition(5, 19);
        assertThrows(BloxException.class, () -> o.deplacerDe(0, 1));
    }
}
