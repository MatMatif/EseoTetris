package fr.eseo.e3.poo.projet.blox.modele;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fr.eseo.e3.poo.projet.blox.modele.pieces.UsineDePiece;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.ITetromino;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.JTetromino;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.LTetromino;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.OTetromino;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.STetromino;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.TTetromino;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.Tetromino;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.ZTetromino;

class UsineDePieceTest {

    @Test
    void testGenererTetrominoAleatoirePiece() {
        UsineDePiece.setMode(UsineDePiece.ALEATOIRE_PIECE);
        for (int i = 0; i < 20; i++) {
            Tetromino tetromino = UsineDePiece.genererTetromino();
            assertNotNull(tetromino, "Le tetromino généré ne doit pas être null");
        }
    }

    @Test
    void testGenererTetrominoAleatoireComplet() {
        UsineDePiece.setMode(UsineDePiece.ALEATOIRE_COMPLET);
        for (int i = 0; i < 20; i++) {
            Tetromino tetromino = UsineDePiece.genererTetromino();
            assertNotNull(tetromino, "Le tetromino généré ne doit pas être null");
        }
    }

    @Test
    void testGenererTetrominoCyclic() {
        UsineDePiece.setMode(UsineDePiece.CYCLIC);

        // Premier cycle (O, I, T, L, J, Z, S)
        assertTrue(UsineDePiece.genererTetromino() instanceof OTetromino, "La 1ère pièce doit être un OTetromino");
        assertTrue(UsineDePiece.genererTetromino() instanceof ITetromino, "La 2ème pièce doit être un ITetromino");
        assertTrue(UsineDePiece.genererTetromino() instanceof TTetromino, "La 3ème pièce doit être un TTetromino");
        assertTrue(UsineDePiece.genererTetromino() instanceof LTetromino, "La 4ème pièce doit être un LTetromino");
        assertTrue(UsineDePiece.genererTetromino() instanceof JTetromino, "La 5ème pièce doit être un JTetromino");
        assertTrue(UsineDePiece.genererTetromino() instanceof ZTetromino, "La 6ème pièce doit être un ZTetromino");
        assertTrue(UsineDePiece.genererTetromino() instanceof STetromino, "La 7ème pièce doit être un STetromino");

        // Deuxième cycle pour vérifier que ça boucle
        assertTrue(UsineDePiece.genererTetromino() instanceof OTetromino, "La 8ème pièce doit être un OTetromino (boucle)");
    }
}
