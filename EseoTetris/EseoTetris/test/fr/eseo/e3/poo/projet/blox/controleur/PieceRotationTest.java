package fr.eseo.e3.poo.projet.blox.controleur;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.eseo.e3.poo.projet.blox.modele.Coordonnees;
import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.ITetromino;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

class PieceRotationTest {

    private Puits puits;
    private VuePuits vuePuits;
    private PieceRotation pieceRotation;
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

        pieceRotation = new PieceRotation(vuePuits);
        vuePuits.addMouseListener(pieceRotation);
        vuePuits.requestFocusInWindow();
    }

    @AfterEach
    void tearDown() {
        frame.dispose();
    }

    private MouseEvent createMouseEvent(int button) {
        return new MouseEvent(vuePuits, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), 0, 10, 10, 1, false, button);
    }

    @Test
    void testMouseClickedLeft() {
        Coordonnees el1Initial = new Coordonnees(piece.getElements().get(1).getCoordonnees().getAbscisse(), piece.getElements().get(1).getCoordonnees().getOrdonnee());
        // Le clic gauche déclenche une rotation anti-horaire
        pieceRotation.mouseClicked(createMouseEvent(MouseEvent.BUTTON1));
        assertNotEquals(el1Initial, piece.getElements().get(1).getCoordonnees(), "La pièce aurait dû tourner (sens anti-horaire)");
    }

    @Test
    void testMouseClickedRight() {
        Coordonnees el1Initial = new Coordonnees(piece.getElements().get(1).getCoordonnees().getAbscisse(), piece.getElements().get(1).getCoordonnees().getOrdonnee());
        // Le clic droit déclenche une rotation horaire
        pieceRotation.mouseClicked(createMouseEvent(MouseEvent.BUTTON3));
        assertNotEquals(el1Initial, piece.getElements().get(1).getCoordonnees(), "La pièce aurait dû tourner (sens horaire)");
    }

    @Test
    void testMouseClickedOther() {
        Coordonnees initialCoords = new Coordonnees(piece.getElements().get(0).getCoordonnees().getAbscisse(), piece.getElements().get(0).getCoordonnees().getOrdonnee());
        pieceRotation.mouseClicked(createMouseEvent(MouseEvent.BUTTON2)); // Clic du milieu
        assertEquals(initialCoords, piece.getElements().get(0).getCoordonnees(), "Un clic non-assigné ne doit pas faire tourner la pièce");
    }

    @Test
    void testPieceIsNull() {
        Puits puitsVide = new Puits();
        VuePuits vuePuitsVide = new VuePuits(puitsVide);
        PieceRotation rotationVide = new PieceRotation(vuePuitsVide);
        // On s'attend à ce qu'aucune exception ne soit levée
        rotationVide.mouseClicked(createMouseEvent(MouseEvent.BUTTON1));
    }

    @Test
    void testCollisionDoesNotThrow() {
        piece.setPosition(0, 5);
        Coordonnees initialCoords = new Coordonnees(piece.getElements().get(0).getCoordonnees().getAbscisse(), piece.getElements().get(0).getCoordonnees().getOrdonnee());
        // Tenter de tourner va causer une BloxException, qui doit être attrapée.
        // Le test réussit si aucune exception n'est propagée.
        pieceRotation.mouseClicked(createMouseEvent(MouseEvent.BUTTON3));
        assertEquals(initialCoords, piece.getElements().get(0).getCoordonnees(), "La pièce ne doit pas bouger si la rotation cause une collision");
    }
}
