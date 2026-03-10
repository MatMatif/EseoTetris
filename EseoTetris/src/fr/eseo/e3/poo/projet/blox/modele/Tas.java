package fr.eseo.e3.poo.projet.blox.modele;

import java.util.ArrayList;
import java.util.List;

public class Tas {
    private Puits puits;
    private List<Element> elements;

    public Tas(Puits puits) {
        this.puits = puits;
        this.elements = new ArrayList<>();
    }

    public Puits getPuits() {
        return puits;
    }

    public void setPuits(Puits puits) {
        this.puits = puits;
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }
}
