package fr.eseo.e3.poo.projet.blox.modele;

public class BloxException extends Exception {
    public static final int BLOX_COLLISION = 0;
    public static final int BLOX_SORTIE_PUITS = 1;

    private final int type;

    /**
     * @param message le message d'erreur
     * @param type le type de l'exception
     */
    public BloxException(String message, int type) {
        super(message);
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
