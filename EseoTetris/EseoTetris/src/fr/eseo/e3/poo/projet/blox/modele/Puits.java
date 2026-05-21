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
    public static final String MODIFICATION_SCORE = "score";

    private int largeur;
    private int profondeur;
    private Piece pieceActuelle;
    private Piece pieceSuivante;
    private PropertyChangeSupport pcs;
    private Tas tas;
    private int score;

    public Puits() {
        this(LARGEUR_PAR_DEFAUT, PROFONDEUR_PAR_DEFAUT);
    }

    /**
     * @param largeur la largeur
     * @param profondeur la profondeur
     */
    public Puits(int largeur, int profondeur) {
        this(largeur, profondeur, 0, 0);
    }

    /**
     * @param largeur la largeur
     * @param profondeur la profondeur
     * @param nbElements nombre d'éléments initial dans le tas
     * @param nbLignes nombre de lignes initiales dans le tas
     */
    public Puits(int largeur, int profondeur, int nbElements, int nbLignes) {
        this.setLargeur(largeur);
        this.setProfondeur(profondeur);
        this.pcs = new PropertyChangeSupport(this);
        if (nbElements > 0) {
            this.setTas(new Tas(this, nbElements, nbLignes));
        } else {
            this.setTas(new Tas(this));
        }
        this.score = 0;
    }

    public int getLargeur() {
        return largeur;
    }

    /**
     * @param largeur la nouvelle largeur
     */
    public void setLargeur(int largeur) {
        if (largeur < 5 || largeur > 15) {
            throw new IllegalArgumentException("Largeur hors limites [5, 15] : " + largeur);
        }
        this.largeur = largeur;
    }

    public int getProfondeur() {
        return profondeur;
    }

    /**
     * @param profondeur la nouvelle profondeur
     */
    public void setProfondeur(int profondeur) {
        if (profondeur < 15 || profondeur > 25) {
            throw new IllegalArgumentException("Profondeur hors limites [15, 25] : " + profondeur);
        }
        this.profondeur = profondeur;
    }

    public Piece getPieceActuelle() {
        return pieceActuelle;
    }

    public Piece getPieceSuivante() {
        return pieceSuivante;
    }
    
    public int getScore() {
        return score;
    }

    public Tas getTas() {
        return tas;
    }

    /**
     * @param tas le nouveau tas
     */
    public void setTas(Tas tas) {
        this.tas = tas;
    }

    /**
     * @param piece la nouvelle pièce suivante
     */
    public void setPieceSuivante(Piece piece) {
        if (this.pieceSuivante != null) {
            Piece anciennePieceActuelle = this.pieceActuelle;
            this.pieceActuelle = this.pieceSuivante;
            this.pieceActuelle.setPosition(this.largeur / 2, -4);
            this.pcs.firePropertyChange(MODIFICATION_PIECE_ACTUELLE, anciennePieceActuelle, this.pieceActuelle);
        }
        Piece anciennePieceSuivante = this.pieceSuivante;
        this.pieceSuivante = piece;
        if (this.pieceSuivante != null) {
            this.pieceSuivante.setPuits(this);
        }
        this.pcs.firePropertyChange(MODIFICATION_PIECE_SUIVANTE, anciennePieceSuivante, this.pieceSuivante);
    }

    public void gravite() {
        if (this.pieceActuelle != null) {
            try {
                this.pieceActuelle.deplacerDe(0, 1);
                this.pcs.firePropertyChange(MODIFICATION_PIECE_ACTUELLE, null, this.pieceActuelle);
            } catch (BloxException e) {
                if (e.getType() == BloxException.BLOX_COLLISION) {
                    this.gererCollision();
                }
            }
        }
    }

    public void gererCollision() {
        if (this.pieceActuelle != null) {
            this.tas.ajouterElements(this.pieceActuelle);
            int lignesSupprimees = this.tas.supprimerLignesCompletes();
            
            if (lignesSupprimees > 0) {
                int oldScore = this.score;
                switch (lignesSupprimees) {
                    case 1:
                        this.score += 100;
                        break;
                    case 2:
                        this.score += 300;
                        break;
                    case 3:
                        this.score += 500;
                        break;
                    case 4:
                        this.score += 800;
                        break;
                    default: // Bonus pour plus de 4 lignes, même si c'est rare
                        this.score += 800 + (lignesSupprimees - 4) * 200;
                        break;
                }
                this.pcs.firePropertyChange(MODIFICATION_SCORE, oldScore, this.score);
            }
            this.setPieceSuivante(UsineDePiece.genererTetromino());
        }
    }

    /**
     * @param listener l'écouteur à ajouter
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    /**
     * @param listener l'écouteur à supprimer
     */
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
            sb.append(this.pieceActuelle.toString());
        }
        sb.append("Piece Suivante : ");
        if (this.pieceSuivante == null) {
            sb.append("<aucune>");
        } else {
            String s = this.pieceSuivante.toString();
            if (s.endsWith("\n")) {
                s = s.substring(0, s.length() - 1);
            }
            sb.append(s);
        }
        return sb.toString();
    }
}
