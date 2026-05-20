package fr.eseo.e3.poo.projet.blox.modele;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class CoordonneesTest {

    @Test
    void testConstructeur() {
        Coordonnees coords = new Coordonnees(10, 20);
        assertEquals(10, coords.getAbscisse(), "L'abscisse initiale doit être 10");
        assertEquals(20, coords.getOrdonnee(), "L'ordonnée initiale doit être 20");
    }

    @Test
    void testSetters() {
        Coordonnees coords = new Coordonnees(0, 0);
        coords.setAbscisse(5);
        assertEquals(5, coords.getAbscisse(), "L'abscisse doit être mise à jour à 5");
        coords.setOrdonnee(15);
        assertEquals(15, coords.getOrdonnee(), "L'ordonnée doit être mise à jour à 15");
    }

    @Test
    void testEquals() {
        Coordonnees coords1 = new Coordonnees(5, 10);
        Coordonnees coords2 = new Coordonnees(5, 10);
        Coordonnees coords3 = new Coordonnees(10, 10);
        Coordonnees coords4 = new Coordonnees(5, 20);
        String notCoords = "not a coordinate object";

        assertTrue(coords1.equals(coords2), "Deux objets Coordonnees avec les mêmes valeurs doivent être égaux");
        assertFalse(coords1.equals(coords3), "Deux objets Coordonnees avec des abscisses différentes ne doivent pas être égaux");
        assertFalse(coords1.equals(coords4), "Deux objets Coordonnees avec des ordonnées différentes ne doivent pas être égaux");
        assertFalse(coords1.equals(null), "Un objet Coordonnees ne doit pas être égal à null");
        assertFalse(coords1.equals(notCoords), "Un objet Coordonnees ne doit pas être égal à un objet d'une autre classe");
    }

    @Test
    void testHashCode() {
        Coordonnees coords1 = new Coordonnees(5, 10);
        Coordonnees coords2 = new Coordonnees(5, 10);
        Coordonnees coords3 = new Coordonnees(10, 5);

        assertEquals(coords1.hashCode(), coords2.hashCode(), "Les hash codes de deux objets Coordonnees égaux doivent être identiques");
        assertNotEquals(coords1.hashCode(), coords3.hashCode(), "Les hash codes de deux objets Coordonnees différents devraient être différents");
    }

    @Test
    void testToString() {
        Coordonnees coords = new Coordonnees(7, 42);
        assertEquals("(7, 42)", coords.toString(), "Le format de toString n'est pas correct");
    }
}