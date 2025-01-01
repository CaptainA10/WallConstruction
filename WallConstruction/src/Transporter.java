public class Transporter extends Worker {
    private final StonePool stonePool;
    private final ToolSemaphore toolSemaphore;
    private final int maxIterations = 100;  // Limite d'itérations

    public Transporter(int id, StonePool stonePool, ToolSemaphore toolSemaphore) {
        super(id);
        this.stonePool = stonePool;
        this.toolSemaphore = toolSemaphore;
    }

    @Override
    public void run() {
        try {
            int iterations = 0;
            while (iterations < maxIterations) {
                toolSemaphore.acquireTool();  // Acquisition d'outil
                if (stonePool.getAvailableStones() > 0) {
                    stonePool.takeStone();  // Transporter une pierre
                    System.out.println("Transporter " + id + " delivered a stone.");
                }
                toolSemaphore.releaseTool();  // Libération d'outil
                Thread.sleep((int) (Math.random() * 1000));  // Pause entre les itérations
                iterations++;
            }
            System.out.println("Transporter " + id + " finished after " + iterations + " iterations.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
