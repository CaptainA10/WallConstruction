import java.util.concurrent.Semaphore;

public class ToolSemaphore {
    private final Semaphore semaphore;

    public ToolSemaphore(int toolCount) {
        this.semaphore = new Semaphore(toolCount);
    }

    public void acquireTool() throws InterruptedException {
        semaphore.acquire();
    }

    public void releaseTool() {
        semaphore.release();
    }
}
