public class Mason extends Worker {
    private final Wall wall;
    private final StonePool stonePool;
    private final int maxIterations = 100;  // Limite d'itérations

    public Mason(int id, Wall wall, StonePool stonePool) {
        super(id);
        this.wall = wall;
        this.stonePool = stonePool;
    }

    @Override
    public void run() {
        try {
            int iterations = 0;
            while (!wall.isComplete() && stonePool.getAvailableStones() > 0 && iterations < maxIterations) {
                int column = (int) (Math.random() * wall.getColumnCount());
                wall.addStone(id, column);
                Thread.sleep((int) (Math.random() * 1000));  // Pause entre les itérations
                iterations++;
            }
            System.out.println("Mason " + id + " finished after " + iterations + " iterations.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
