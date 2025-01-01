public class Searcher extends Worker {
    private final StonePool stonePool;
    private final int maxIterations = 100;  // Limite d'itérations

    public Searcher(int id, StonePool stonePool) {
        super(id);
        this.stonePool = stonePool;
    }

    @Override
    public void run() {
        try {
            int iterations = 0;
            while (iterations < maxIterations) {
                if (stonePool.getAvailableStones() < 100) {
                    stonePool.addStone();  // Ajouter une pierre si nécessaire
                    System.out.println("Searcher " + id + " found a stone.");
                }
                Thread.sleep((int) (Math.random() * 1000));  // Pause entre les itérations
                iterations++;
            }
            System.out.println("Searcher " + id + " finished after " + iterations + " iterations.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
