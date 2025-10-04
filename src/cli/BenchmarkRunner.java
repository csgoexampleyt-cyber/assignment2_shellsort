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
            writer.println("Size,SequenceType,TimeMs");

            for (int n : sizes) {
                int[] arr = new int[n];
                for (int i = 0; i < n; i++) arr[i] = rand.nextInt(1_000_000);

                for (SequenceType type : SequenceType.values()) {
                    int[] copy = arr.clone();
                    PerformanceTracker tracker = new PerformanceTracker();

                    tracker.start();
                    ShellSort.sort(copy, type);
                    tracker.stop();

                    double time = tracker.getElapsedMillis();

                    writer.println(n + "," + type + "," + time);

                    System.out.printf("n=%-6d %-10s -> %.3f ms%n", n, type, time);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
