package fr.eseo.e3.poo.projet.blox.modele;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

public class Gravite implements ActionListener {

    private final Puits puits;
    private final VuePuits vuePuits;
    private Timer timer;
    
    public static final int PERIOD_PAR_DEFAUT = 1000;

    public Gravite(Puits puits, VuePuits vuePuits) {
        this.puits = puits;
        this.vuePuits = vuePuits;
        this.timer = new Timer(PERIOD_PAR_DEFAUT, this);
    }
    
    public void setPeriod(int period) {
        this.timer.setDelay(period);
    }
    
    public int getPeriod() {
        return this.timer.getDelay();
    }
    
    public void start() {
        this.timer.start();
    }
    
    public void stop() {
        this.timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        faireTomberPiece();
    }
    
    public void faireTomberPiece() {
        if (puits.getPieceActuelle() != null) {
            try {
                puits.getPieceActuelle().deplacerDe(0, 1);
            } catch (BloxException ex) {
                if (ex.getType() == BloxException.BLOX_COLLISION) {
                    puits.gererCollision();
                }
            }
            // In a real scenario, repaint could be triggered by property changes from the model
            // But for now, we trigger it manually from the controller part.
            vuePuits.repaint();
        }
    }
}
