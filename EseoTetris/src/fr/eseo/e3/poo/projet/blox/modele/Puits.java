package fr.eseo.e3.poo.projet.blox.modele;

import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;

public class Puits {
    private Piece pieceActuelle;
    private Piece pieceSuivante;
    private Tas tas;

    public Puits() {
        this.tas = new Tas(this);
    }

    public Piece getPieceActuelle() {
        return pieceActuelle;
    }

    public void setPieceActuelle(Piece pieceActuelle) {
        this.pieceActuelle = pieceActuelle;
    }

    public Piece getPieceSuivante() {
        return pieceSuivante;
    }

    public void setPieceSuivante(Piece pieceSuivante) {
        this.pieceSuivante = pieceSuivante;
    }

    public Tas getTas() {
        return tas;
    }

    public void setTas(Tas tas) {
        this.tas = tas;
    }
}
