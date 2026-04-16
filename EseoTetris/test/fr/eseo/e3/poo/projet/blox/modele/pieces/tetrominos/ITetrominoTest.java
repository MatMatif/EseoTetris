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

public class ITetrominoTest {

    @Test
    public void testConstructeur() {
        Coordonnees coords = new Coordonnees(5, 5);
        Couleur couleur = Couleur.CYAN;
        ITetromino iTetromino = new ITetromino(coords, couleur);

        assertNotNull(iTetromino.getElements());
        assertEquals(4, iTetromino.getElements().size());

        java.util.List<Element> elements = iTetromino.getElements();
        assertEquals(5, elements.get(0).getCoordonnees().getAbscisse());
        assertEquals(5, elements.get(0).getCoordonnees().getOrdonnee());
    }

    @Test
    public void testDeplacerDeValide() throws BloxException {
        ITetromino i = new ITetromino(new Coordonnees(5, 5), Couleur.CYAN);
        i.deplacerDe(1, 0); // Droite
        assertEquals(6, i.getElements().get(0).getCoordonnees().getAbscisse());
        i.deplacerDe(0, 1); // Bas
        assertEquals(6, i.getElements().get(0).getCoordonnees().getOrdonnee());
    }

    @Test
    public void testDeplacerDeInvalide() throws BloxException {
        ITetromino i = new ITetromino(new Coordonnees(5, 5), Couleur.CYAN);
        // Haut (y < 0)
        assertThrows(IllegalArgumentException.class, () -> i.deplacerDe(0, -1), "Haut interdit");
        // Trop loin horizontalement (|x| > 1)
        assertThrows(IllegalArgumentException.class, () -> i.deplacerDe(2, 0), "Trop loin horizontalement");
        assertThrows(IllegalArgumentException.class, () -> i.deplacerDe(-2, 0), "Trop loin horizontalement");
        // Trop loin verticalement (y > 1)
        assertThrows(IllegalArgumentException.class, () -> i.deplacerDe(0, 2), "Trop loin verticalement");
        // Diagonal (|x| == 1 && y == 1)
        assertThrows(IllegalArgumentException.class, () -> i.deplacerDe(1, 1), "Diagonal interdit");
        assertThrows(IllegalArgumentException.class, () -> i.deplacerDe(-1, 1), "Diagonal interdit");
    }

    @Test
    public void testTournerHoraire() throws BloxException {
        ITetromino i = new ITetromino(new Coordonnees(5, 5), Couleur.CYAN);
        // Initial elements relative to (5,5): (0,0), (0,-1), (0,-2), (0,1)
        i.tourner(true);
        // After clockwise rotation: (0,0), (1,0), (2,0), (-1,0)
        // Absolute: (5,5), (6,5), (7,5), (4,5)
        assertEquals(5, i.getElements().get(0).getCoordonnees().getAbscisse());
        assertEquals(5, i.getElements().get(0).getCoordonnees().getOrdonnee());
        
        assertEquals(6, i.getElements().get(1).getCoordonnees().getAbscisse());
        assertEquals(5, i.getElements().get(1).getCoordonnees().getOrdonnee());
        
        assertEquals(7, i.getElements().get(2).getCoordonnees().getAbscisse());
        assertEquals(5, i.getElements().get(2).getCoordonnees().getOrdonnee());
        
        assertEquals(4, i.getElements().get(3).getCoordonnees().getAbscisse());
        assertEquals(5, i.getElements().get(3).getCoordonnees().getOrdonnee());
    }

    @Test
    public void testTournerAntiHoraire() throws BloxException {
        ITetromino i = new ITetromino(new Coordonnees(5, 5), Couleur.CYAN);
        // Initial elements relative to (5,5): (0,0), (0,-1), (0,-2), (0,1)
        i.tourner(false);
        // After anti-clockwise rotation: (0,0), (-1,0), (-2,0), (1,0)
        // Absolute: (5,5), (4,5), (3,5), (6,5)
        assertEquals(5, i.getElements().get(0).getCoordonnees().getAbscisse());
        assertEquals(5, i.getElements().get(0).getCoordonnees().getOrdonnee());
        
        assertEquals(4, i.getElements().get(1).getCoordonnees().getAbscisse());
        assertEquals(5, i.getElements().get(1).getCoordonnees().getOrdonnee());
        
        assertEquals(3, i.getElements().get(2).getCoordonnees().getAbscisse());
        assertEquals(5, i.getElements().get(2).getCoordonnees().getOrdonnee());
        
        assertEquals(6, i.getElements().get(3).getCoordonnees().getAbscisse());
        assertEquals(5, i.getElements().get(3).getCoordonnees().getOrdonnee());
    }

    @Test
    public void testTournerCercleComplet() throws BloxException {
        ITetromino i = new ITetromino(new Coordonnees(5, 5), Couleur.CYAN);
        java.util.List<Coordonnees> initiales = new java.util.ArrayList<>();
        for(Element e : i.getElements()) {
            initiales.add(new Coordonnees(e.getCoordonnees().getAbscisse(), e.getCoordonnees().getOrdonnee()));
        }

        // 4 rotations horaires = retour à l'état initial
        for(int r = 0; r < 4; r++) {
            i.tourner(true);
        }

        for(int idx = 0; idx < 4; idx++) {
            assertEquals(initiales.get(idx).getAbscisse(), i.getElements().get(idx).getCoordonnees().getAbscisse());
            assertEquals(initiales.get(idx).getOrdonnee(), i.getElements().get(idx).getCoordonnees().getOrdonnee());
        }
    }

    @Test
    public void testSetPosition() throws BloxException {
        ITetromino iTetromino = new ITetromino(new Coordonnees(5, 5), Couleur.CYAN);
        iTetromino.setPosition(10, 10);
        assertEquals(10, iTetromino.getElements().get(0).getCoordonnees().getAbscisse());
        assertEquals(10, iTetromino.getElements().get(0).getCoordonnees().getOrdonnee());
    }

    @Test
    public void testCollisionPuits() {
        Puits p = new Puits(10, 20);
        ITetromino i = new ITetromino(new Coordonnees(0, 5), Couleur.CYAN);
        i.setPuits(p);
        // Sortie gauche
        assertThrows(BloxException.class, () -> i.deplacerDe(-1, 0));
        // Collision fond
        i.setPosition(5, 19);
        assertThrows(BloxException.class, () -> i.deplacerDe(0, 1));
    }
}
