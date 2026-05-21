package fr.eseo.e3.poo.projet.blox.controleur;

import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Gravite implements ActionListener, PropertyChangeListener {

    /** Périodicité initiale du timer en millisecondes. */
    public static final int PERIODICITE_INITIALE = 1000;
    /**
     * EXTENSION (section 4.1) — Pas d'accélération : on retire ce delta
     * en millisecondes par palier de 10 lignes complétées.
     */
    public static final int DELTA_PAR_PALIER = 100;
    /** Plancher de sécurité : la périodicité ne descend pas en-dessous. */
    public static final int PERIODICITE_MINIMALE = 100;
    /** Taille d'un palier de lignes pour déclencher une accélération. */
    public static final int LIGNES_PAR_PALIER = 10;

    private final VuePuits vuePuits;
    private Timer timer;
    private int dernierPalier; // EXTENSION (section 4.1) : palier courant

    public Gravite(VuePuits vuePuits) {
        this.vuePuits = vuePuits;
        this.timer = new Timer(PERIODICITE_INITIALE, this);
        this.dernierPalier = 0;

        // EXTENSION (section 4.1) : s'abonner aux notifications du Puits
        // pour ajuster la périodicité quand un palier de 10 lignes est franchi.
        if (vuePuits != null && vuePuits.getPuits() != null) {
            vuePuits.getPuits().addPropertyChangeListener(this);
        }
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

    /**
     * EXTENSION (section 4.1) — Accélération par paliers : à chaque
     * franchissement d'un palier de {@link #LIGNES_PAR_PALIER} lignes,
     * la périodicité du timer baisse de {@link #DELTA_PAR_PALIER} ms,
     * sans descendre sous {@link #PERIODICITE_MINIMALE} ms.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (Puits.MODIFICATION_FIN_PARTIE.equals(evt.getPropertyName())) {
            this.timer.stop();
            if (this.vuePuits != null && this.vuePuits.getPuits() != null) {
                this.vuePuits.getPuits().removePropertyChangeListener(this);
            }
            return;
        }
        if (!Puits.MODIFICATION_LIGNES_COMPLETES.equals(evt.getPropertyName())) {
            return;
        }
        int nbLignes = (int) evt.getNewValue();
        int nouveauPalier = nbLignes / LIGNES_PAR_PALIER;
        if (nouveauPalier > this.dernierPalier) {
            int nouvellePeriode = Math.max(
                PERIODICITE_MINIMALE,
                PERIODICITE_INITIALE - nouveauPalier * DELTA_PAR_PALIER
            );
            this.setPeriodicite(nouvellePeriode);
            this.dernierPalier = nouveauPalier;
        }
    }
}
