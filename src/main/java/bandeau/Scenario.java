package bandeau;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.LinkedList;

/**
 * Classe utilitaire pour représenter la classe-association UML
 */
class ScenarioElement {

    Effect effect;
    int repeats;

    ScenarioElement(Effect e, int r) {
        effect = e;
        repeats = r;
    }
}

/**
 * Un scenario mémorise une liste d'effets, et le nombre de repetitions pour
 * chaque effet
 * Un scenario sait se jouer sur un bandeau.
 */
public class Scenario {

    private final List<ScenarioElement> myElements = new LinkedList<>();
    private final ReentrantReadWriteLock verrouRW = new ReentrantReadWriteLock();
    private final Lock read = verrouRW.readLock();
    private final Lock write = verrouRW.writeLock();

    /**
     * Ajouter un effect au scenario.
     *
     * @param e       l'effet à ajouter
     * @param repeats le nombre de répétitions pour cet effet
     */
    public void addEffect(Effect e, int repeats) {
        write.lock();
        try {
            myElements.add(new ScenarioElement(e, repeats));
        } finally {
            write.unlock();
        }
    }

    /**
     * Jouer ce scenario sur un bandeau
     *
     * @param b le bandeau ou s'afficher.
     * 
     */
    public void playOn(VerrouBandeau b) {
        read.lock();
        try {
            Thread t = new Thread(() -> {
                b.verrouiller();
                for (ScenarioElement element : myElements) {
                    for (int repeats = 0; repeats < element.repeats; repeats++) {
                        element.effect.playOn(b);
                    }
                }
                b.deverrouiller();
            });
            t.start();
        } finally {
            read.unlock();
        }
    }
}
