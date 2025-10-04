package metrics;

public class PerformanceTracker {
    private long startTime;
    private long endTime;
    private long shiftCount;

    public void start() {
        startTime = System.nanoTime();
        shiftCount = 0;
    }

    public void stop() {
        endTime = System.nanoTime();
    }

    public void incrementShifts() {
        shiftCount++;
    }

    public long getShiftCount() {
        return shiftCount;
    }

    public double getElapsedMillis() {
        return (endTime - startTime) / 1_000_000.0;
    }

    public void reset() {
        startTime = 0;
        endTime = 0;
        shiftCount = 0;
    }
}
