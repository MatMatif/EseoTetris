package fr.eseo.e3.poo.projet.blox.modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;

public class Tas {
    private final Puits puits;
    private final List<Element> elements;

    /**
     * @param puits le puits associé
     */
    public Tas(Puits puits) {
        this.puits = puits;
        this.elements = new ArrayList<>();
    }

    /**
     * @param puits le puits associé
     * @param nbElements nombre d'éléments initial
     */
    public Tas(Puits puits, int nbElements) {
        this(puits, nbElements, nbElements / puits.getLargeur() + 1);
    }

    /**
     * @param puits le puits associé
     * @param nbElements nombre d'éléments initial
     * @param nbLignes nombre de lignes initiales
     */
    public Tas(Puits puits, int nbElements, int nbLignes) {
        this(puits, nbElements, nbLignes, new Random());
    }

    /**
     * @param puits le puits associé
     * @param nbElements nombre d'éléments initial
     * @param nbLignes nombre de lignes initiales
     * @param rand générateur aléatoire
     */
    public Tas(Puits puits, int nbElements, int nbLignes, Random rand) {
        this.puits = puits;
        this.elements = new ArrayList<>();
        this.construireTas(nbElements, nbLignes, rand);
    }

    public Puits getPuits() {
        return puits;
    }

    public List<Element> getElements() {
        return elements;
    }

    /**
     * @param nbElements nombre d'éléments initial
     * @param nbLignes nombre de lignes initiales
     * @param rand générateur aléatoire
     */
    private void construireTas(int nbElements, int nbLignes, Random rand) {
        if (nbElements != 0 && (nbElements > puits.getLargeur() * nbLignes || nbLignes >= puits.getProfondeur())) {
            throw new IllegalArgumentException("Nombre d'éléments ou lignes invalide");
        }
        if (nbElements != 0) {
            int elementsPlaces = 0;
            while (elementsPlaces < nbElements) {
                int ordonnee = (puits.getProfondeur() - 1) - rand.nextInt(nbLignes);
                int abscisse = rand.nextInt(puits.getLargeur());
                if (!elementExists(abscisse, ordonnee)) {
                    int indiceCouleur = rand.nextInt(Couleur.values().length);
                    this.elements.add(new Element(abscisse, ordonnee, Couleur.values()[indiceCouleur]));
                    elementsPlaces++;
                }
            }
        }
    }

    /**
     * @param abscisse l'abscisse
     * @param ordonnee l'ordonnée
     */
    public boolean elementExists(int abscisse, int ordonnee) {
        for (Element element : this.elements) {
            if (element.getCoordonnees().getAbscisse() == abscisse &&
                    element.getCoordonnees().getOrdonnee() == ordonnee) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param piece la pièce dont on veut ajouter les éléments
     */
    public void ajouterElements(Piece piece) {
        this.elements.addAll(piece.getElements());
    }

    public int supprimerLignesCompletes() {
        int nbLignesSupprimees = 0;
        int nbColonnes = puits.getLargeur();
        int nbLignes = puits.getProfondeur();

        for (int y = 0; y < nbLignes; y++) {
            int count = 0;
            for (int x = 0; count < nbColonnes && x < nbColonnes; x++) {
                if (elementExists(x, y)) {
                    count++;
                }
            }

            if (count == nbColonnes) {
                nbLignesSupprimees++;
                final int finalY = y;
                this.elements.removeIf(e -> e.getCoordonnees().getOrdonnee() == finalY);
                for (Element e : this.elements) {
                    if (e.getCoordonnees().getOrdonnee() < finalY) {
                        e.deplacerDe(0, 1);
                    }
                }
                y--;
            }
        }
        return nbLignesSupprimees;
    }
}
