package fr.eseo.e3.poo.projet.blox.modele;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.awt.Color;
import org.junit.jupiter.api.Test;

public class CouleurTest {

    @Test
    public void testGetCouleurPourAffichage() {
        assertEquals(Color.RED, Couleur.ROUGE.getCouleurPourAffichage(), "ROUGE doit être associé à Color.RED");
        assertEquals(Color.ORANGE, Couleur.ORANGE.getCouleurPourAffichage(), "ORANGE doit être associé à Color.ORANGE");
        assertEquals(Color.BLUE, Couleur.BLEU.getCouleurPourAffichage(), "BLEU doit être associé à Color.BLUE");
        assertEquals(Color.GREEN, Couleur.VERT.getCouleurPourAffichage(), "VERT doit être associé à Color.GREEN");
        assertEquals(Color.YELLOW, Couleur.JAUNE.getCouleurPourAffichage(), "JAUNE doit être associé à Color.YELLOW");
        assertEquals(Color.CYAN, Couleur.CYAN.getCouleurPourAffichage(), "CYAN doit être associé à Color.CYAN");
        assertEquals(Color.MAGENTA, Couleur.VIOLET.getCouleurPourAffichage(), "VIOLET doit être associé à Color.MAGENTA");
    }
}
