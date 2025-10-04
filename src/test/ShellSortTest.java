package test;
import algorithms.ShellSort;
import java.util.Arrays;
import java.util.Random;

public class ShellSortTest {

    private static final Random rand = new Random();

    public static void main(String[] args) {
        System.out.println("Correctness Tests");
        checkCorrectness();

        System.out.println("\nRandom Tests");
        randomTests(10, 500);

        System.out.println("\nCross Validation");
        checkCrossValidation();

        System.out.println("\nPerformance");
        perfTests();
    }

    private static void checkCorrectness() {
        int[][] tests = {
                {},                  // empty
                {7},                 // single element
                {4, 4, 4},           // all same
                {1, 2, 3, 4, 5},     // already sorted
                {9, 8, 7, 6, 5}      // reversed
        };

        for (int[] arr : tests) {
            for (ShellSort.SequenceType seq : ShellSort.SequenceType.values()) {
                int[] copy = arr.clone();
                ShellSort.sort(copy, seq);
                if (isSorted(copy)) {
                    System.out.println(seq + " ok: " + Arrays.toString(arr));
                } else {
                    System.out.println(seq + " failed on " + Arrays.toString(arr));
                }
            }
        }
    }

    private static void randomTests(int trials, int maxSize) {
        for (int t = 0; t < trials; t++) {
            int n = rand.nextInt(maxSize + 1);
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rand.nextInt(1000);

            int[] expected = arr.clone();
            Arrays.sort(expected);

            for (ShellSort.SequenceType seq : ShellSort.SequenceType.values()) {
                int[] copy = arr.clone();
                ShellSort.sort(copy, seq);
                if (!Arrays.equals(copy, expected)) {
                    System.out.println("Random test failed for " + seq);
                    System.out.println("Input: " + Arrays.toString(arr));
                    return;
                }
            }
        }
        System.out.println("All random tests passed");
    }

    private static void checkCrossValidation() {
        int[] arr = new int[20];
        for (int i = 0; i < arr.length; i++) arr[i] = rand.nextInt(100);

        int[] sorted = arr.clone();
        Arrays.sort(sorted);

        for (ShellSort.SequenceType seq : ShellSort.SequenceType.values()) {
            int[] copy = arr.clone();
            ShellSort.sort(copy, seq);
            System.out.println(seq + " matches built-in? " + Arrays.equals(copy, sorted));
        }
    }

    private static void perfTests() {
        int[] sizes = {100, 1000, 10_000, 100_000};
        for (int n : sizes) {
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rand.nextInt(1_000_000);

            for (ShellSort.SequenceType seq : ShellSort.SequenceType.values()) {
                int[] copy = arr.clone();
                long start = System.nanoTime();
                ShellSort.sort(copy, seq);
                long time = (System.nanoTime() - start) / 1_000_000;
                System.out.println("n=" + n + ", " + seq + " took " + time + " ms, sorted? " + isSorted(copy));
            }
        }
    }

    private static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) return false;
        }
        return true;
    }
}
