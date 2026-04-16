package fr.eseo.e3.poo.projet.blox.modele;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ElementTest {

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
