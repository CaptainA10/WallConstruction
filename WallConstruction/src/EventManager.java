import java.util.concurrent.*;

public class EventManager {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final int maxEvents = 10;  // Limite des événements
    private int eventCounter = 0;

    public void startEvents(StonePool stonePool, ToolSemaphore toolSemaphore) {
        // Événement : ajout de pierres aléatoires
        scheduler.scheduleAtFixedRate(() -> {
            if (stonePool.getAvailableStones() < 100 && eventCounter < maxEvents) {
                stonePool.addStone();
                System.out.println("Random event: New stones added.");
                eventCounter++;
            }
        }, 5, 10, TimeUnit.SECONDS);

        // Événement : panne d'outil
        scheduler.scheduleAtFixedRate(() -> {
            if (eventCounter < maxEvents) {
                try {
                    System.out.println("Random event: Tool malfunction!");
                    toolSemaphore.acquireTool();
                    Thread.sleep(3000);
                    toolSemaphore.releaseTool();
                    System.out.println("Random event: Tool malfunction resolved.");
                    eventCounter++;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, 10, 15, TimeUnit.SECONDS);
    }
}
