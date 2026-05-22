package fr.eseo.e3.poo.projet.blox.modele;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.OTetromino;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.ITetromino;
import org.junit.jupiter.api.Test;

public class PuitsTest {

    @Test
    public void testConstructeurDefaut() {
        Puits puits = new Puits();
        assertEquals(10, puits.getLargeur());
        assertEquals(15, puits.getProfondeur());
        assertNull(puits.getPieceActuelle());
        assertNull(puits.getPieceSuivante());
    }

    @Test
    public void testLimitesDimensions() {
        assertThrows(IllegalArgumentException.class, () -> new Puits(4, 15));
        assertThrows(IllegalArgumentException.class, () -> new Puits(16, 15));
        assertThrows(IllegalArgumentException.class, () -> new Puits(10, 14));
        assertThrows(IllegalArgumentException.class, () -> new Puits(10, 26));
    }

    @Test
    public void testTasInitialise() {
        Puits puits = new Puits();
        assertNotNull(puits.getTas());
        assertEquals(0, puits.getTas().getElements().size());
    }

    @Test
    public void testConstructeurQuatreArguments() {
        Puits puits = new Puits(10, 20, 5, 2);
        assertNotNull(puits.getTas());
        assertEquals(5, puits.getTas().getElements().size());
    }

    @Test
    public void testSetPieceSuivante() {
        Puits puits = new Puits(10, 20);
        OTetromino p1 = new OTetromino(new Coordonnees(0, 0), Couleur.ROUGE);
        OTetromino p2 = new OTetromino(new Coordonnees(0, 0), Couleur.BLEU);

        puits.setPieceSuivante(p1);
        assertEquals(p1, puits.getPieceSuivante());
        assertEquals(puits, p1.getPuits());
        assertNull(puits.getPieceActuelle());

        puits.setPieceSuivante(p2);
        assertEquals(p2, puits.getPieceSuivante());
        assertEquals(p1, puits.getPieceActuelle());
        assertEquals(5, p1.getElements().get(0).getCoordonnees().getAbscisse());
        assertEquals(-4, p1.getElements().get(0).getCoordonnees().getOrdonnee());
    }

    @Test
    void testGravitePasDeCollision() {
        Puits puits = new Puits();
        OTetromino piece = new OTetromino(new Coordonnees(5, 5), Couleur.ROUGE);
        puits.setPieceSuivante(piece);
        puits.setPieceSuivante(new ITetromino(new Coordonnees(0,0), Couleur.BLEU));

        int yInitial = puits.getPieceActuelle().getElements().get(0).getCoordonnees().getOrdonnee();
        puits.gravite();
        int yFinal = puits.getPieceActuelle().getElements().get(0).getCoordonnees().getOrdonnee();
        
        assertEquals(yInitial + 1, yFinal, "La pièce aurait dû descendre.");
    }

    @Test
    void testDescenteDirecte() {
        Puits puits = new Puits();
        OTetromino p1 = new OTetromino(new Coordonnees(5, 5), Couleur.ROUGE);
        ITetromino p2 = new ITetromino(new Coordonnees(0, 0), Couleur.BLEU);
        
        puits.setPieceSuivante(p1);
        puits.setPieceSuivante(p2);
        
        // p1 est maintenant pieceActuelle, on s'assure qu'elle n'est pas déjà au fond
        puits.getPieceActuelle().setPosition(5, 5);
        
        Piece actuelleAvant = puits.getPieceActuelle();
        assertEquals(p1, actuelleAvant);
        
        puits.descenteDirecte();
        
        Piece actuelleApres = puits.getPieceActuelle();
        // La pièce actuelle doit avoir changé (p2 a été promu)
        assertEquals(p2, actuelleApres, "La pièce devrait avoir changé après la descente directe.");
        // p1 doit être dans le tas
        assertEquals(4, puits.getTas().getElements().size(), "Les éléments de p1 devraient être dans le tas.");
    }
}
