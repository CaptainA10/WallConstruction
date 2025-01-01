import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        int rows = 3, columns = 5;  // Taille du mur (3 rangées, 5 colonnes)
        Wall wall = new Wall(rows, columns);  // Créer le mur
        StonePool stonePool = new StonePool(10);  // Créer le pool de pierres avec 10 pierres initiales
        ToolSemaphore toolSemaphore = new ToolSemaphore(3);  // Créer un semaphore pour les outils (maximum 3 travailleurs à la fois)

        // Créer et lancer les travailleurs
        ExecutorService executorService = Executors.newFixedThreadPool(3);  // Créer un pool de threads avec 3 travailleurs
        executorService.submit(new Searcher(1, stonePool));  // Chercheur 1
        executorService.submit(new Transporter(1, stonePool, toolSemaphore));  // Transporteur 1
        executorService.submit(new Mason(1, wall, stonePool));  // Maçon 1

        // Démarrer les événements aléatoires
        EventManager eventManager = new EventManager();
        eventManager.startEvents(stonePool, toolSemaphore);

        // Exécuter pendant 30 secondes
        try {
            Thread.sleep(30000);  // Exécution pendant 30 secondes
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Arrêter le pool de threads proprement
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }

        // Afficher le progrès du mur en temps réel
        System.out.println("Final Wall Progress:");
        wall.displayWallStatus();  // Affiche le mur après chaque action
        System.out.println("Total stones placed: " + wall.getTotalStonesPlaced());  // Affiche le nombre total de pierres placées

        System.out.println("Simulation finished.");
    }
}