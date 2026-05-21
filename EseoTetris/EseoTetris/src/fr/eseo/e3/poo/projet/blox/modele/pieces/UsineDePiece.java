package fr.eseo.e3.poo.projet.blox.modele.pieces;

import fr.eseo.e3.poo.projet.blox.modele.Coordonnees;
import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.ITetromino;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.OTetromino;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.TTetromino;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.LTetromino;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.JTetromino;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.ZTetromino;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.STetromino;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.Tetromino;
import java.util.Random;

public class UsineDePiece {
    public static final int ALEATOIRE_COMPLET = 0;
    public static final int ALEATOIRE_PIECE = 1;
    public static final int CYCLIC = 2;

    private static int mode = ALEATOIRE_PIECE;
    private static int cyclicIndex = 0;
    private static final Random random = new Random();

    private UsineDePiece() {
    }

    /**
     * @param mode le mode de génération
     */
    public static void setMode(int mode) {
        UsineDePiece.mode = mode;
        if (mode == CYCLIC) {
            cyclicIndex = 0;
        }
    }

    public static Tetromino genererTetromino() {
        int type;
        Couleur couleur;
        Coordonnees coords = new Coordonnees(2, 3);

        if (mode == ALEATOIRE_COMPLET) {
            type = random.nextInt(7);
            couleur = Couleur.values()[random.nextInt(Couleur.values().length)];
        } else if (mode == CYCLIC) {
            type = cyclicIndex;
            cyclicIndex = (cyclicIndex + 1) % 7;
            couleur = getCouleurParDefaut(type);
        } else {
            type = random.nextInt(7);
            couleur = getCouleurParDefaut(type);
        }

        switch (type) {
            case 0: return new OTetromino(coords, couleur);
            case 1: return new ITetromino(coords, couleur);
            case 2: return new TTetromino(coords, couleur);
            case 3: return new LTetromino(coords, couleur);
            case 4: return new JTetromino(coords, couleur);
            case 5: return new ZTetromino(coords, couleur);
            case 6: return new STetromino(coords, couleur);
            default: return new OTetromino(coords, couleur);
        }
    }

    /**
     * @param type le type de tetromino
     */
    private static Couleur getCouleurParDefaut(int type) {
        switch (type) {
            case 0: return Couleur.ROUGE;    // OTetromino
            case 1: return Couleur.ORANGE;   // ITetromino
            case 2: return Couleur.BLEU;     // TTetromino
            case 3: return Couleur.VERT;     // LTetromino
            case 4: return Couleur.JAUNE;    // JTetromino
            case 5: return Couleur.CYAN;     // ZTetromino
            case 6: return Couleur.VIOLET;   // STetromino
            default: return Couleur.ROUGE;
        }
    }
}
