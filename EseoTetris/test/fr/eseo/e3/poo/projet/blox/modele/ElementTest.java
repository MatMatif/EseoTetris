package fr.eseo.e3.poo.projet.blox.modele;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

public class ElementTest {

    @Test
    void testConstructeurIntInt() {
        Element el = new Element(5, 10);
        assertEquals(new Coordonnees(5, 10), el.getCoordonnees(), "Les coordonnées doivent être (5, 10)");
        assertEquals(Couleur.ROUGE, el.getCouleur(), "La couleur par défaut doit être ROUGE");
    }

    @Test
    void testConstructeurIntIntCouleur() {
        Element el = new Element(5, 10, Couleur.BLEU);
        assertEquals(new Coordonnees(5, 10), el.getCoordonnees(), "Les coordonnées doivent être (5, 10)");
        assertEquals(Couleur.BLEU, el.getCouleur(), "La couleur doit être BLEU");
    }

    @Test
    void testConstructeurCoordonnees() {
        Coordonnees coords = new Coordonnees(7, 12);
        Element el = new Element(coords);
        assertEquals(coords, el.getCoordonnees(), "Les coordonnées doivent être l'objet Coordonnees fourni");
        assertEquals(Couleur.ROUGE, el.getCouleur(), "La couleur par défaut doit être ROUGE");
    }
    
    @Test
    void testConstructeurCoordonneesCouleur() {
        Coordonnees coords = new Coordonnees(7, 12);
        Element el = new Element(coords, Couleur.VERT);
        assertEquals(coords, el.getCoordonnees(), "Les coordonnées doivent être l'objet Coordonnees fourni");
        assertEquals(Couleur.VERT, el.getCouleur(), "La couleur doit être VERT");
    }

    @Test
    void testSetters() {
        Element el = new Element(0, 0);
        Coordonnees newCoords = new Coordonnees(25, 30);
        el.setCoordonnees(newCoords);
        assertEquals(newCoords, el.getCoordonnees(), "Les coordonnées doivent être mises à jour");
        
        el.setCouleur(Couleur.JAUNE);
        assertEquals(Couleur.JAUNE, el.getCouleur(), "La couleur doit être mise à jour");
    }
    
    @Test
    void testEquals() {
        Element el1 = new Element(new Coordonnees(1, 2), Couleur.CYAN);
        Element el2 = new Element(new Coordonnees(1, 2), Couleur.CYAN);
        Element el3 = new Element(new Coordonnees(3, 2), Couleur.CYAN);
        Element el4 = new Element(new Coordonnees(1, 2), Couleur.VIOLET);

        assertTrue(el1.equals(el2), "Deux éléments avec les mêmes attributs doivent être égaux");
        assertFalse(el1.equals(el3), "Deux éléments avec des coordonnées différentes ne doivent pas être égaux");
        assertFalse(el1.equals(el4), "Deux éléments avec des couleurs différentes ne doivent pas être égaux");
        assertFalse(el1.equals(null), "Un élément ne doit pas être égal à null");
        assertFalse(el1.equals(new Object()), "Un élément ne doit pas être égal à un objet d'une autre classe");
    }

    @Test
    void testHashCode() {
        Element el1 = new Element(new Coordonnees(1, 2), Couleur.CYAN);
        Element el2 = new Element(new Coordonnees(1, 2), Couleur.CYAN);
        Element el3 = new Element(new Coordonnees(3, 2), Couleur.CYAN);

        assertEquals(el1.hashCode(), el2.hashCode(), "Les hash codes de deux éléments égaux doivent être identiques");
        assertNotEquals(el1.hashCode(), el3.hashCode(), "Les hash codes de deux éléments différents devraient être différents");
    }

    @Test
    void testToString() {
        Element el = new Element(8, 4, Couleur.ORANGE);
        assertEquals("(8, 4) - ORANGE", el.toString(), "Le format de toString est incorrect");
    }

    @Test
    public void testDeplacerDe() {
        Element element = new Element(new Coordonnees(10, 10), Couleur.ROUGE);
        element.deplacerDe(1, 2);
        assertEquals(11, element.getCoordonnees().getAbscisse(), "L'abscisse doit être 11");
        assertEquals(12, element.getCoordonnees().getOrdonnee(), "L'ordonnée doit être 12");
    }

    @Test
    public void testDeplacerDeNegatif() {
        Element element = new Element(new Coordonnees(10, 10), Couleur.ROUGE);
        element.deplacerDe(-5, -3);
        assertEquals(5, element.getCoordonnees().getAbscisse(), "L'abscisse doit être 5");
        assertEquals(7, element.getCoordonnees().getOrdonnee(), "L'ordonnée doit être 7");
    }
}
