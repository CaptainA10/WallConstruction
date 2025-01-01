import java.util.concurrent.locks.*;

public class Wall {
    private final int[][] wallStructure;
    private int currentRow = 0;
    private final Lock lock = new ReentrantLock();
    private final Condition rowComplete = lock.newCondition();
    private int totalStonesPlaced = 0;

    public Wall(int rows, int columns) {
        this.wallStructure = new int[rows][columns];
    }

    public void addStone(int workerId, int column) throws InterruptedException {
        lock.lock();
        try {
            while (currentRow >= wallStructure.length || wallStructure[currentRow][column] != 0) {
                System.out.println("Worker " + workerId + " waiting for row " + currentRow + " to be free.");
                rowComplete.await();
            }
            wallStructure[currentRow][column] = workerId;
            totalStonesPlaced++;
            System.out.println("Worker " + workerId + " placed a stone at row " + currentRow + ", column " + column);
            if (isRowComplete(currentRow)) {
                System.out.println("Row " + currentRow + " is complete. Moving to next row.");
                currentRow++;
                rowComplete.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }

    private boolean isRowComplete(int row) {
        for (int stone : wallStructure[row]) {
            if (stone == 0) return false;
        }
        return true;
    }

    public boolean isComplete() {
        return currentRow >= wallStructure.length;
    }

    public int getColumnCount() {
        if (wallStructure.length > 0) {
            return wallStructure[0].length;
        }
        return 0;
    }

    public int getTotalStonesPlaced() {
        return totalStonesPlaced;
    }

    public void displayWall() {
        System.out.println("Current wall progress: ");
        for (int i = 0; i < wallStructure.length; i++) {
            for (int j = 0; j < wallStructure[i].length; j++) {
                System.out.print(wallStructure[i][j] == 0 ? "." : "#");
            }
            System.out.println();
        }
    }

    public void displayWallStatus() {
        System.out.println("Wall construction status:");
        System.out.println("Rows completed: " + currentRow + "/" + wallStructure.length);
        System.out.println("Total stones placed: " + totalStonesPlaced);
        displayWall();
    }
}