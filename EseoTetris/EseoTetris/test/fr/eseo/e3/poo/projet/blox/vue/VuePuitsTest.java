package fr.eseo.e3.poo.projet.blox.vue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import fr.eseo.e3.poo.projet.blox.modele.Puits;
import org.junit.jupiter.api.Test;

public class VuePuitsTest {

    @Test
    public void testConstructeurUnParametre() {
        Puits puits = new Puits();
        VuePuits vuePuits = new VuePuits(puits);
        assertEquals(puits, vuePuits.getPuits(), "Le puits doit être celui passé en paramètre");
        assertEquals(VuePuits.TAILLE_PAR_DEFAUT, vuePuits.getTaille(), "La taille doit être la taille par défaut");
    }

    @Test
    public void testConstructeurDeuxParametres() {
        Puits puits = new Puits();
        int taille = 20;
        VuePuits vuePuits = new VuePuits(puits, taille);
        assertEquals(puits, vuePuits.getPuits(), "Le puits doit être celui passé en paramètre");
        assertEquals(taille, vuePuits.getTaille(), "La taille doit être celle passée en paramètre");
    }

    @Test
    public void testSetters() {
        Puits puits1 = new Puits(10, 20);
        Puits puits2 = new Puits(12, 18);
        VuePuits vuePuits = new VuePuits(puits1);
        
        vuePuits.setPuits(puits2);
        assertEquals(puits2, vuePuits.getPuits(), "Le puits doit avoir été mis à jour");
        
        vuePuits.setTaille(25);
        assertEquals(25, vuePuits.getTaille(), "La taille doit avoir été mise à jour");
    }

    @Test
    public void testPreferredSize() {
        Puits puits = new Puits(10, 20);
        int taille = 15;
        VuePuits vuePuits = new VuePuits(puits, taille);
        java.awt.Dimension expected = new java.awt.Dimension(10 * 15, 20 * 15);
        assertEquals(expected, vuePuits.getPreferredSize(), "La taille de préférence est incorrecte");
    }
}
