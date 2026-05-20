package fr.eseo.e3.poo.projet.blox.controleur;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.eseo.e3.poo.projet.blox.modele.Coordonnees;
import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.ITetromino;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

class PieceClavierTest {

    private Puits puits;
    private VuePuits vuePuits;
    private PieceClavier pieceClavier;
    private ITetromino piece;
    private JFrame frame;

    @BeforeEach
    void setUp() {
        puits = new Puits(10, 20);
        piece = new ITetromino(new Coordonnees(5, 5), fr.eseo.e3.poo.projet.blox.modele.Couleur.ROUGE);
        puits.setPieceSuivante(piece);
        puits.setPieceSuivante(piece); // Pour que la première pièce devienne la pièce actuelle

        frame = new JFrame();
        vuePuits = new VuePuits(puits);
        frame.add(vuePuits);
        frame.setVisible(true);
        
        // On teste une instance de PieceClavier directement
        pieceClavier = new PieceClavier(vuePuits);
        vuePuits.requestFocusInWindow();
    }

    @AfterEach
    void tearDown() {
        frame.dispose();
    }

    private KeyEvent createKeyEvent(int keyCode) {
        return new KeyEvent(vuePuits, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, keyCode, KeyEvent.CHAR_UNDEFINED);
    }

    @Test
    void testKeyPressedLeft() {
        int initialX = piece.getElements().getFirst().getCoordonnees().getAbscisse();
        pieceClavier.keyPressed(createKeyEvent(KeyEvent.VK_LEFT));
        assertEquals(initialX - 1, piece.getElements().getFirst().getCoordonnees().getAbscisse(), "La pièce aurait dû se déplacer à gauche");
    }

    @Test
    void testKeyPressedRight() {
        int initialX = piece.getElements().getFirst().getCoordonnees().getAbscisse();
        pieceClavier.keyPressed(createKeyEvent(KeyEvent.VK_RIGHT));
        assertEquals(initialX + 1, piece.getElements().getFirst().getCoordonnees().getAbscisse(), "La pièce aurait dû se déplacer à droite");
    }

    @Test
    void testKeyPressedDown() {
        int initialY = piece.getElements().getFirst().getCoordonnees().getOrdonnee();
        pieceClavier.keyPressed(createKeyEvent(KeyEvent.VK_DOWN));
        assertEquals(initialY + 1, piece.getElements().getFirst().getCoordonnees().getOrdonnee(), "La pièce aurait dû se déplacer vers le bas");
    }
    
    @Test
    void testKeyPressedUp() {
        Coordonnees el1Initial = new Coordonnees(piece.getElements().get(1).getCoordonnees().getAbscisse(), piece.getElements().get(1).getCoordonnees().getOrdonnee());
        pieceClavier.keyPressed(createKeyEvent(KeyEvent.VK_UP));
        assertNotEquals(el1Initial, piece.getElements().get(1).getCoordonnees(), "La pièce aurait dû tourner");
    }

    @Test
    void testKeyPressedOther() {
        Coordonnees initialCoords = new Coordonnees(piece.getElements().getFirst().getCoordonnees().getAbscisse(), piece.getElements().getFirst().getCoordonnees().getOrdonnee());
        pieceClavier.keyPressed(createKeyEvent(KeyEvent.VK_A));
        assertEquals(initialCoords, piece.getElements().getFirst().getCoordonnees(), "Une touche non assignée ne doit pas bouger la pièce");
    }

    @Test
    void testPieceIsNull() {
        // Cree un Puits sans piece actuelle.
        Puits puitsVide = new Puits();
        VuePuits vuePuitsVide = new VuePuits(puitsVide);
        PieceClavier clavierVide = new PieceClavier(vuePuitsVide);
        // On s'attend à ce qu'aucune exception ne soit levée
        clavierVide.keyPressed(createKeyEvent(KeyEvent.VK_LEFT));
    }
    
    @Test
    void testCollisionThrowsRuntimeException() {
        piece.setPosition(0, 5);
        assertThrows(RuntimeException.class, () -> {
            pieceClavier.keyPressed(createKeyEvent(KeyEvent.VK_LEFT));
        }, "Une collision doit lever une RuntimeException");
    }
}
