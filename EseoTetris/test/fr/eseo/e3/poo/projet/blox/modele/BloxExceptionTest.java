package fr.eseo.e3.poo.projet.blox.modele;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class BloxExceptionTest {

    @Test
    void testConstructeurEtGettersCollision() {
        String message = "Collision détectée";
        int type = BloxException.BLOX_COLLISION;
        
        BloxException exception = new BloxException(message, type);
        
        assertEquals(message, exception.getMessage(), "Le message de l'exception doit être correct");
        assertEquals(type, exception.getType(), "Le type de l'exception doit être BLOX_COLLISION");
    }
    
    @Test
    void testConstructeurEtGettersSortiePuits() {
        String message = "Sortie du puits";
        int type = BloxException.BLOX_SORTIE_PUITS;
        
        BloxException exception = new BloxException(message, type);
        
        assertEquals(message, exception.getMessage(), "Le message de l'exception doit être correct");
        assertEquals(type, exception.getType(), "Le type de l'exception doit être BLOX_SORTIE_PUITS");
    }
}
