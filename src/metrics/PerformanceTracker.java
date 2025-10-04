package metrics;

public class PerformanceTracker {
    private long startTime;
    private long endTime;

    public void start() {
        startTime = System.nanoTime();
    }

    public void stop() {
        endTime = System.nanoTime();
    }

    public double getElapsedMillis() {
        return (endTime - startTime) / 1_000_000.0;
    }
}
