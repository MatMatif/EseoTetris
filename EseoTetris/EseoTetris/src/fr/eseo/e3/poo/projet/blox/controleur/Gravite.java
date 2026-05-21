package fr.eseo.e3.poo.projet.blox.controleur;

import fr.eseo.e3.poo.projet.blox.vue.VuePuits;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gravite implements ActionListener {

    private final VuePuits vuePuits;
    private Timer timer;

    public Gravite(VuePuits vuePuits) {
        this.vuePuits = vuePuits;
        this.timer = new Timer(1000, this); // Périodicité par défaut
        this.timer.start();
    }

    public int getPeriodicite() {
        return this.timer.getDelay();
    }

    public void setPeriodicite(int periodicite) {
        this.timer.setDelay(periodicite);
        this.timer.setInitialDelay(periodicite);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.vuePuits != null && this.vuePuits.getPuits() != null) {
            this.vuePuits.getPuits().gravite();
            this.vuePuits.repaint();
        }
    }
}
