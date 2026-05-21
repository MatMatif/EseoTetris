package fr.eseo.e3.poo.projet.blox.modele;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import java.util.Random;

public class TasTest {

    @Test
    public void testConstructeurPuits() {
        Puits puits = new Puits();
        Tas tas = new Tas(puits);
        assertEquals(puits, tas.getPuits());
        assertEquals(0, tas.getElements().size());
    }

    @Test
    public void testConstructeurNbElements() {
        Puits puits = new Puits(10, 20);
        int nbElements = 10;
        Tas tas = new Tas(puits, nbElements);
        assertEquals(nbElements, tas.getElements().size());
    }

    @Test
    public void testConstructeurSeed() {
        Puits puits = new Puits(10, 20);
        Random rand = new Random(12345);
        Tas tas1 = new Tas(puits, 5, 2, new Random(12345));
        Tas tas2 = new Tas(puits, 5, 2, new Random(12345));
        
        assertEquals(tas1.getElements().size(), tas2.getElements().size());
        for(int i=0; i<tas1.getElements().size(); i++) {
            assertEquals(tas1.getElements().get(i).getCoordonnees(), tas2.getElements().get(i).getCoordonnees());
        }
    }

    @Test
    public void testIllegalArgument() {
        Puits puits = new Puits(10, 20);
        // Trop d'éléments pour les lignes
        assertThrows(IllegalArgumentException.class, () -> new Tas(puits, 21, 2));
        // Trop de lignes pour le puits
        assertThrows(IllegalArgumentException.class, () -> new Tas(puits, 5, 20));
    }

    @Test
    public void testElementExists() {
        Puits puits = new Puits(10, 20);
        Tas tas = new Tas(puits, 1, 1, new Random(10));
        Element e = tas.getElements().get(0);
        assertTrue(tas.elementExists(e.getCoordonnees().getAbscisse(), e.getCoordonnees().getOrdonnee()));
    }
}
