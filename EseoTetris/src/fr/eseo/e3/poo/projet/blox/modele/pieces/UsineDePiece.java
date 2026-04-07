package fr.eseo.e3.poo.projet.blox.modele.pieces;

import fr.eseo.e3.poo.projet.blox.modele.Coordonnees;
import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.ITetromino;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.OTetromino;
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
        // Constructeur privé pour empêcher l'instanciation
    }

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
            type = random.nextInt(2);
            couleur = Couleur.values()[random.nextInt(Couleur.values().length)];
        } else if (mode == CYCLIC) {
            type = cyclicIndex;
            cyclicIndex = (cyclicIndex + 1) % 2;
            couleur = getCouleurParDefaut(type);
        } else { // ALEATOIRE_PIECE (par défaut)
            type = random.nextInt(2);
            couleur = getCouleurParDefaut(type);
        }

        if (type == 0) {
            return new OTetromino(coords, couleur);
        } else {
            return new ITetromino(coords, couleur);
        }
    }

    private static Couleur getCouleurParDefaut(int type) {
        // Couleurs selon Figure 1 : O (rouge), I (orange)
        if (type == 0) {
            return Couleur.ROUGE;
        }
        return Couleur.ORANGE;
    }
}
