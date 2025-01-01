import java.util.concurrent.locks.*;

public class StonePool {
    private int availableStones;
    private final Lock lock = new ReentrantLock();
    private final Condition stonesAvailable = lock.newCondition();

    public StonePool(int initialStones) {
        this.availableStones = initialStones;
    }

    // Méthode pour ajouter une pierre
    public void addStone() {
        lock.lock();
        try {
            availableStones++;
            stonesAvailable.signal();
        } finally {
            lock.unlock();
        }
    }

    // Méthode pour retirer une pierre
    public void takeStone() throws InterruptedException {
        lock.lock();
        try {
            while (availableStones <= 0) {
                stonesAvailable.await();
            }
            availableStones--;
        } finally {
            lock.unlock();
        }
    }

    // Méthode pour obtenir le nombre de pierres disponibles
    public int getAvailableStones() {
        lock.lock();
        try {
            return availableStones;
        } finally {
            lock.unlock();
        }
    }
}
