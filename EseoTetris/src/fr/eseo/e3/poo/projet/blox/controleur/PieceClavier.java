package fr.eseo.e3.poo.projet.blox.controleur;

import fr.eseo.e3.poo.projet.blox.modele.BloxException;
import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Contrôleur clavier — EXTENSION (section 4.1 du PDF : "Utiliser le clavier
 * pour la rotation et le mouvement des pièces").
 *
 * Cette classe ne fait PAS partie de la version basique demandée
 * dans les sections 3.1 à 3.8.6 ; elle s'ajoute en complément des
 * contrôleurs souris {@link PieceDeplacement} et {@link PieceRotation}.
 * On hérite de {@link KeyAdapter} (même logique que {@link java.awt.event.MouseAdapter}
 * pour PieceDeplacement) pour n'avoir à redéfinir que les méthodes utilisées.
 */
public class PieceClavier extends KeyAdapter {

    private final VuePuits vuePuits;

    public PieceClavier(VuePuits vuePuits) {
        this.vuePuits = vuePuits;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        Puits puits = this.vuePuits.getPuits();
        if (puits == null || puits.getPieceActuelle() == null) {
            return;
        }

        try {
            switch (event.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    puits.getPieceActuelle().deplacerDe(-1, 0);
                    break;
                case KeyEvent.VK_RIGHT:
                    puits.getPieceActuelle().deplacerDe(1, 0);
                    break;
                case KeyEvent.VK_DOWN:
                    puits.gravite();
                    break;
                case KeyEvent.VK_V:
                    puits.descenteDirecte();
                    break;
                case KeyEvent.VK_UP:
                    puits.getPieceActuelle().tourner(true);
                    break;
                case KeyEvent.VK_SPACE:
                    puits.getPieceActuelle().tourner(false);
                    break;
                case KeyEvent.VK_C:
                    // EXTENSION (section 4.1) : Hold — stocke la pièce actuelle.
                    // Le verrou "une fois par tour" est géré dans Puits.stocker()
                    // et réarmé par Puits.gererCollision() à la prochaine pose.
                    puits.stocker();
                    break;
                default:
                    return;
            }
            this.vuePuits.repaint();
        } catch (BloxException e) {
            // Mouvement impossible (sortie du puits ou collision) : on ignore
        }
    }
}
