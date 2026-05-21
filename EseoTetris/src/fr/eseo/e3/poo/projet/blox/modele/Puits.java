package fr.eseo.e3.poo.projet.blox.modele;

import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;
import fr.eseo.e3.poo.projet.blox.modele.pieces.UsineDePiece;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Puits {
    public static final int LARGEUR_PAR_DEFAUT = 10;
    public static final int PROFONDEUR_PAR_DEFAUT = 15;
    public static final String MODIFICATION_PIECE_ACTUELLE = "pieceActuelle";
    public static final String MODIFICATION_PIECE_SUIVANTE = "pieceSuivante";
    public static final String MODIFICATION_PIECE_STOCKEE = "pieceStockee";
    public static final String MODIFICATION_SCORE = "score";
    public static final String MODIFICATION_LIGNES_COMPLETES = "lignesCompletes";
    public static final String MODIFICATION_FIN_PARTIE = "finPartie";

    private int largeur;
    private int profondeur;
    private Piece pieceActuelle;
    private Piece pieceSuivante;
    private Piece pieceStockee;       // EXTENSION (section 4.1) : Hold
    private boolean peutStocker = true; // verrou « une fois par tour »
    private boolean finPartie = false;
    private PropertyChangeSupport pcs;
    private Tas tas;
    private int score;
    private int nbLignesCompletes; // EXTENSION (section 4.1) : palier vitesse

    public Puits() {
        this(LARGEUR_PAR_DEFAUT, PROFONDEUR_PAR_DEFAUT);
    }

    public Puits(int largeur, int profondeur) {
        this(largeur, profondeur, 0, 0);
    }

    public Puits(int largeur, int profondeur, int nbElements, int nbLignes) {
        this.setLargeur(largeur);
        this.setProfondeur(profondeur);
        this.pcs = new PropertyChangeSupport(this);
        this.tas = new Tas(this, nbElements, nbLignes);
        this.score = 0;
    }

    public int getLargeur() {
        return largeur;
    }

    public void setLargeur(int largeur) {
        if (largeur < 5 || largeur > 15) {
            throw new IllegalArgumentException("Largeur invalide");
        }
        this.largeur = largeur;
    }

    public int getProfondeur() {
        return profondeur;
    }

    public void setProfondeur(int profondeur) {
        if (profondeur < 15 || profondeur > 25) {
            throw new IllegalArgumentException("Profondeur invalide");
        }
        this.profondeur = profondeur;
    }

    public Piece getPieceActuelle() {
        return pieceActuelle;
    }

    public Piece getPieceSuivante() {
        return pieceSuivante;
    }

    /**
     * @return true si la partie est terminée
     */
    public boolean isFinPartie() {
        return this.finPartie;
    }

    public void setPieceSuivante(Piece piece) {
        if (this.pieceSuivante != null) {
            Piece anciennePieceActuelle = this.pieceActuelle;
            this.pieceActuelle = this.pieceSuivante;
            this.pieceActuelle.setPosition(this.largeur / 2, -4);

            // Vérification de collision immédiate (Fin de partie)
            for (Element element : this.pieceActuelle.getElements()) {
                Coordonnees coords = element.getCoordonnees();
                if (coords.getOrdonnee() >= 0 && this.tas.elementExists(coords.getAbscisse(), coords.getOrdonnee())) {
                    this.finPartie = true;
                    break;
                }
            }

            if (this.finPartie) {
                this.pieceActuelle = null;
                this.pcs.firePropertyChange(MODIFICATION_FIN_PARTIE, false, true);
            } else {
                this.pcs.firePropertyChange(MODIFICATION_PIECE_ACTUELLE, anciennePieceActuelle, this.pieceActuelle);
            }
        }
        Piece anciennePieceSuivante = this.pieceSuivante;
        this.pieceSuivante = piece;
        if (this.pieceSuivante != null) {
            this.pieceSuivante.setPuits(this);
        }
        this.pcs.firePropertyChange(MODIFICATION_PIECE_SUIVANTE, anciennePieceSuivante, this.pieceSuivante);
    }

    public Tas getTas() {
        return tas;
    }

    public void setTas(Tas tas) {
        this.tas = tas;
    }

    public int getScore() {
        return score;
    }

    /**
     * EXTENSION (section 4.1) — nombre total de lignes complétées
     * depuis le début de la partie. Utilisé par {@code Gravite} pour
     * déterminer le palier de vitesse.
     */
    public int getNbLignesCompletes() {
        return this.nbLignesCompletes;
    }

    /**
     * Tente de déplacer la pieceActuelle d'une case vers le bas.
     * En cas de collision avec le fond du Puits ou le Tas,
     * la collision est gérée automatiquement.
     */
    public void gravite() {
        if (this.pieceActuelle == null || this.finPartie) {
            return;
        }
        try {
            this.pieceActuelle.deplacerDe(0, 1);
            this.pcs.firePropertyChange(MODIFICATION_PIECE_ACTUELLE, null, this.pieceActuelle);
        } catch (BloxException e) {
            if (e.getType() == BloxException.BLOX_COLLISION) {
                this.gererCollision();
            }
        }
    }

    /**
     * EXTENSION (section 4.1) — Descente directe (Hard Drop).
     * Déplace la pièce actuelle vers le bas jusqu'à ce qu'une collision
     * survienne et qu'une nouvelle pièce soit mise en jeu.
     */
    public void descenteDirecte() {
        if (this.pieceActuelle == null || this.finPartie) {
            return;
        }
        Piece p = this.pieceActuelle;
        while (this.pieceActuelle == p && !this.finPartie) {
            this.gravite();
        }
    }

    private void gererCollision() {
        if (this.pieceActuelle == null) {
            return;
        }
        this.tas.ajouterElements(this.pieceActuelle);
        int lignesSupprimees = this.tas.supprimerLignesCompletes();
        if (lignesSupprimees > 0) {
            int oldScore = this.score;
            // EXTENSION (section 4.1) : barème de score
            //   - 10 points par ligne complétée (1 → 10, 2 → 20, 3 → 30, 4 → 40)
            //   - Bonus de +20 points pour un Tetris (4 lignes d'un seul coup)
            this.score += lignesSupprimees * 10;
            if (lignesSupprimees == 4) {
                this.score += 20;
            }
            this.pcs.firePropertyChange(MODIFICATION_SCORE, oldScore, this.score);

            // EXTENSION (section 4.1) : palier de vitesse de gravité.
            // On notifie le compteur cumulé pour que Gravite ajuste la périodicité.
            int oldLignes = this.nbLignesCompletes;
            this.nbLignesCompletes += lignesSupprimees;
            this.pcs.firePropertyChange(MODIFICATION_LIGNES_COMPLETES, oldLignes, this.nbLignesCompletes);
        }
        this.setPieceSuivante(UsineDePiece.genererTetromino());
        // EXTENSION (section 4.1) : nouvelle pièce en jeu → on autorise
        // à nouveau le stockage pour le tour suivant.
        this.peutStocker = true;
    }

    /**
     * EXTENSION (section 4.1) — Stockage de la pièce actuelle (mécanisme "Hold").
     * @return la pièce actuellement stockée, ou null si rien n'est stocké
     */
    public Piece getPieceStockee() {
        return this.pieceStockee;
    }

    /**
     * @return true si le joueur a le droit de stocker pendant ce tour,
     *         false s'il l'a déjà fait depuis la dernière pose
     */
    public boolean peutStocker() {
        return this.peutStocker;
    }

    /**
     * EXTENSION (section 4.1) — "Hold" : stocke la pieceActuelle dans
     * un emplacement réservé. Comportement :
     *   - Si aucune pièce n'était stockée, la pieceActuelle y est placée
     *     et la pieceSuivante prend immédiatement sa place (une nouvelle
     *     pieceSuivante est générée par l'UsineDePiece).
     *   - Sinon, pieceActuelle et pieceStockee sont échangées.
     * Dans les deux cas, la nouvelle pieceActuelle est repositionnée
     * en haut du puits (largeur/2, -4) et le verrou peutStocker passe
     * à false jusqu'à la prochaine collision.
     */
    public void stocker() {
        if (!this.peutStocker || this.pieceActuelle == null) {
            return;
        }
        Piece anciennePieceStockee = this.pieceStockee;
        Piece anciennePieceActuelle = this.pieceActuelle;

        if (this.pieceStockee == null) {
            // Premier stockage : on déplace l'actuelle vers le stockage
            // et on fait passer la suivante en actuelle.
            this.pieceStockee = this.pieceActuelle;

            Piece anciennePieceSuivante = this.pieceSuivante;
            this.pieceActuelle = this.pieceSuivante;
            if (this.pieceActuelle != null) {
                this.pieceActuelle.setPosition(this.largeur / 2, -4);
            }
            this.pieceSuivante = UsineDePiece.genererTetromino();
            if (this.pieceSuivante != null) {
                this.pieceSuivante.setPuits(this);
            }
            this.pcs.firePropertyChange(MODIFICATION_PIECE_ACTUELLE, anciennePieceActuelle, this.pieceActuelle);
            this.pcs.firePropertyChange(MODIFICATION_PIECE_SUIVANTE, anciennePieceSuivante, this.pieceSuivante);
        } else {
            // Échange direct entre l'actuelle et la stockée
            this.pieceActuelle = this.pieceStockee;
            this.pieceStockee = anciennePieceActuelle;
            if (this.pieceActuelle != null) {
                this.pieceActuelle.setPosition(this.largeur / 2, -4);
            }
            this.pcs.firePropertyChange(MODIFICATION_PIECE_ACTUELLE, anciennePieceActuelle, this.pieceActuelle);
        }

        this.pcs.firePropertyChange(MODIFICATION_PIECE_STOCKEE, anciennePieceStockee, this.pieceStockee);
        this.peutStocker = false;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Puits : Dimension ").append(this.largeur).append(" x ").append(this.profondeur).append("\n");

        sb.append("Piece Actuelle : ");
        if (this.pieceActuelle == null) {
            sb.append("<aucune>\n");
        } else {
            String actuelleStr = this.pieceActuelle.toString();
            sb.append(actuelleStr);
            if (!actuelleStr.endsWith("\n")) {
                sb.append("\n");
            }
        }

        sb.append("Piece Suivante : ");
        if (this.pieceSuivante == null) {
            sb.append("<aucune>");
        } else {
            sb.append(this.pieceSuivante.toString());
        }
        return sb.toString();
    }
}
