package fr.eseo.e3.poo.projet.blox.modele;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fr.eseo.e3.poo.projet.blox.modele.pieces.UsineDePiece;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.ITetromino;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.OTetromino;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.Tetromino;
import org.junit.jupiter.api.Test;

public class UsineDePieceTest {

    @Test
    public void testModeCyclic() {
        // Initialisation du mode CYCLIC
        UsineDePiece.setMode(UsineDePiece.CYCLIC);
        
        Tetromino t1 = UsineDePiece.genererTetromino();
        assertTrue(t1 instanceof OTetromino, "La première pièce en mode CYCLIC doit être OTetromino");
        assertEquals(2, t1.getElements()[0].getCoordonnees().getAbscisse());
        assertEquals(3, t1.getElements()[0].getCoordonnees().getOrdonnee());

        Tetromino t2 = UsineDePiece.genererTetromino();
        assertTrue(t2 instanceof ITetromino, "La deuxième pièce en mode CYCLIC doit être ITetromino");

        Tetromino t3 = UsineDePiece.genererTetromino();
        assertTrue(t3 instanceof OTetromino, "La troisième pièce en mode CYCLIC doit être OTetromino (boucle)");
    }

    @Test
    public void testResetCyclic() {
        UsineDePiece.setMode(UsineDePiece.CYCLIC);
        UsineDePiece.genererTetromino(); // Consomme le O
        
        // Un nouvel appel à setMode(CYCLIC) doit réinitialiser l'ordre
        UsineDePiece.setMode(UsineDePiece.CYCLIC);
        Tetromino t = UsineDePiece.genererTetromino();
        assertTrue(t instanceof OTetromino, "Après setMode(CYCLIC), on doit recommencer par OTetromino");
    }

    @Test
    public void testCoordonneesInitiales() {
        UsineDePiece.setMode(UsineDePiece.ALEATOIRE_PIECE);
        Tetromino t = UsineDePiece.genererTetromino();
        assertEquals(2, t.getElements()[0].getCoordonnees().getAbscisse(), "L'abscisse initiale doit être 2");
        assertEquals(3, t.getElements()[0].getCoordonnees().getOrdonnee(), "L'ordonnée initiale doit être 3");
    }
}
