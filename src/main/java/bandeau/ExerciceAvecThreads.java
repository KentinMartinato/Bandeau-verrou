package bandeau;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ExerciceAvecThreads {

    public static void main(String[] args) {
        ExerciceAvecThreads instance = new ExerciceAvecThreads();
        instance.exemple();
    }

    public void exemple() {

        Scenario s = makeScenario();
        // On cree les bandeaux
        var b1 = new Bandeau();
        var b2 = new Bandeau();
        var b3 = new Bandeau();
        // On cree les verrous
        Lock VB1 = new ReentrantLock();
        Lock VB2 = new ReentrantLock();
        Lock VB3 = new ReentrantLock();
        System.out.println("CTRL-C pour terminer le programme");
        // On doit jouer le scénario en même temps sur les trois bandeaux
        // On crée un thread pour chaque bandeau

        Thread thread1 = new Thread(
                () -> {
                    s.playOn(b1, VB1);
                });

        Thread thread2 = new Thread(
                () -> {
                    s.playOn(b2, VB2);
                });

        Thread thread3 = new Thread(
                () -> {
                    s.playOn(b3, VB3);
                });

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // On rejoue le scénario sur b1 quand le premier jeu est fini
        s.playOn(b1, VB1);
    }

    private Scenario makeScenario() {
        // On crée un scenario
        Scenario s = new Scenario();
        // On lui ajoute des effets
        s.addEffect(new RandomEffect("Le jeu du pendu", 700), 1);
        // s.addEffect(new TeleType("Je m'affiche caractère par caractère", 100), 1);
        // s.addEffect(new Blink("Je clignote 10x", 100), 10);
        // s.addEffect(new Zoom("Je zoome", 50), 1);
        // s.addEffect(new FontEnumerator(10), 1);
        // s.addEffect(new Rainbow("Comme c'est joli !", 30), 1);
        s.addEffect(new Rotate("2 tours à droite", 180, 4000, true), 2);
        // s.addEffect(new Rotate("2 tours à gauche", 180, 4000, false), 2);
        return s;
    }
}
