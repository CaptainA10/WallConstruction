public abstract class Worker implements Runnable {
    protected final int id;

    public Worker(int id) {
        this.id = id;
    }

    @Override
    public abstract void run();
}
