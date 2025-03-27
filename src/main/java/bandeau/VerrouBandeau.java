package bandeau;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

public class VerrouBandeau extends Bandeau {
    private final Lock verrou = new ReentrantLock();

    public void verrouiller() {
        verrou.lock();
    }

    public void deverrouiller() {
        verrou.unlock();
    }
}