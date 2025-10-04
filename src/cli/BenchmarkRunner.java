package cli;

import algorithms.ShellSort;
import algorithms.ShellSort.SequenceType;
import metrics.PerformanceTracker;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Random;

public class BenchmarkRunner {
    public static void main(String[] args) {
        int[] sizes = {1000, 5000, 10000, 50000, 100000};
        Random rand = new Random();

        try (PrintWriter writer = new PrintWriter(new FileWriter("benchmark.csv"))) {
            writer.println("Size,SequenceType,TimeMs,AvgSpeed(Elements/ms),Shifts");

            System.out.println("ShellSort Benchmark Results");

            for (int n : sizes) {
                int[] arr = new int[n];
                for (int i = 0; i < n; i++) arr[i] = rand.nextInt(1_000_000);

                for (SequenceType type : SequenceType.values()) {
                    int[] copy = arr.clone();
                    PerformanceTracker tracker = new PerformanceTracker();

                    tracker.start();
                    ShellSort.sort(copy, type, tracker);
                    tracker.stop();

                    double time = tracker.getElapsedMillis();
                    long shifts = tracker.getShiftCount();
                    double avgSpeed = n / time;

                    writer.printf("%d,%s,%.3f,%.3f,%d%n", n, type, time, avgSpeed, shifts);
                    System.out.printf("n=%-6d %-10s | time=%.3f ms | speed=%.2f el/ms | shifts=%d%n",
                            n, type, time, avgSpeed, shifts);
                }
            }

            System.out.println("Benchmark complete.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
