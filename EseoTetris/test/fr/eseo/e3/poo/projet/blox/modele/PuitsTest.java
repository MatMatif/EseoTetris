package fr.eseo.e3.poo.projet.blox.modele;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        // Vérification de la position (largeur/2, -4) -> (5, -4)
        assertEquals(5, p1.getElements()[0].getCoordonnees().getAbscisse());
        assertEquals(-4, p1.getElements()[0].getCoordonnees().getOrdonnee());
    }

    @Test
    public void testToString() {
        Puits puits = new Puits(10, 15);
        String expectedVide = "Puits : Dimension 10 x 15\n" +
                "Piece Actuelle : <aucune>\n" +
                "Piece Suivante : <aucune>";
        assertEquals(expectedVide, puits.toString());

        ITetromino i = new ITetromino(new Coordonnees(7, 8), Couleur.ROUGE);
        puits.setPieceSuivante(i);
        
        String expectedSuivante = "Puits : Dimension 10 x 15\n" +
                "Piece Actuelle : <aucune>\n" +
                "Piece Suivante : ITetromino :\n" +
                "\t(7, 8) - ROUGE\n" +
                "\t(7, 7) - ROUGE\n" +
                "\t(7, 6) - ROUGE\n" +
                "\t(7, 9) - ROUGE\n";
        
        // Note: L'ordre des éléments dans ITetromino dépend de setElements. 
        // Ma version est : (x,y), (x,y-1), (x,y-2), (x,y+1)
        assertEquals(expectedSuivante, puits.toString());
    }
}
