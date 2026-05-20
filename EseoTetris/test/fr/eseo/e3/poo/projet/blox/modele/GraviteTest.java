package fr.eseo.e3.poo.projet.blox.modele;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import javax.swing.JFrame;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.ITetromino;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

class GraviteTest {

    private Puits puits;
    private VuePuits vuePuits;
    private Gravite gravite;
    private ITetromino piece;
    private JFrame frame;

    @BeforeEach
    void setUp() {
        puits = new Puits(10, 20);
        piece = new ITetromino(new Coordonnees(5, 5), Couleur.ROUGE);
        puits.setPieceSuivante(piece);
        puits.setPieceSuivante(piece);

        frame = new JFrame();
        vuePuits = new VuePuits(puits);
        frame.add(vuePuits);
        frame.setVisible(true);

        gravite = new Gravite(puits, vuePuits);
    }

    @AfterEach
    void tearDown() {
        gravite.stop();
        frame.dispose();
    }
    
    @Test
    void testFaireTomberPiece() {
        int initialY = piece.getElements().get(0).getCoordonnees().getOrdonnee();
        gravite.faireTomberPiece();
        assertEquals(initialY + 1, piece.getElements().get(0).getCoordonnees().getOrdonnee(), "La pièce aurait dû descendre d'un cran.");
    }
    
    @Test
    void testFaireTomberPieceCollision() {
        // Place la pièce juste avant le fond
        piece.setPosition(5, puits.getProfondeur() - 2);
        int initialTasSize = puits.getTas().getElements().size();
        Piece pieceActuelleAvantCollision = puits.getPieceActuelle();
        
        // Le prochain appel devrait causer une collision
        gravite.faireTomberPiece();
        
        // Vérifier que la pièce a été ajoutée au tas
        assertEquals(initialTasSize + 4, puits.getTas().getElements().size(), "La pièce aurait dû être ajoutée au tas.");
        
        // Vérifier qu'une nouvelle pièce est devenue la pièce actuelle
        assertNotEquals(pieceActuelleAvantCollision, puits.getPieceActuelle(), "Une nouvelle pièce aurait dû être générée.");
    }
    
    @Test
    void testPeriodSetterAndGetter() {
        int newPeriod = 500;
        gravite.setPeriod(newPeriod);
        assertEquals(newPeriod, gravite.getPeriod(), "La période du timer aurait dû être mise à jour.");
    }
}
