package fr.eseo.e3.poo.projet.blox.modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tas {
    private Puits puits;
    private List<Element> elements;

    public Tas(Puits puits) {
        this.puits = puits;
        this.elements = new ArrayList<>();
    }

    public Tas(Puits puits, int nbElements) {
        this(puits, nbElements, nbElements / puits.getLargeur() + 1);
    }

    public Tas(Puits puits, int nbElements, int nbLignes) {
        this(puits, nbElements, nbLignes, new Random());
    }

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

    public boolean elementExists(int abscisse, int ordonnee) {
        for (Element element : this.elements) {
            if (element.getCoordonnees().getAbscisse() == abscisse &&
                element.getCoordonnees().getOrdonnee() == ordonnee) {
                return true;
            }
        }
        return false;
    }
}
