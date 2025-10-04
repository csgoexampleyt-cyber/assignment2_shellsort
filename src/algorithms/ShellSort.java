package algorithms;

import java.util.*;

public class ShellSort {

    public enum SequenceType { SHELL, KNUTH, SEDGEWICK }

    private static List<Integer> shellGaps(int n) {
        List<Integer> gaps = new ArrayList<>();
        for (int gap = n / 2; gap > 0; gap /= 2) gaps.add(gap);
        return gaps;
    }

    private static List<Integer> knuthGaps(int n) {
        List<Integer> gaps = new ArrayList<>();
        int h = 1;
        while (h < n) {
            gaps.add(h);
            h = 3 * h + 1;
        }
        Collections.reverse(gaps);
        return gaps;
    }

    private static List<Integer> sedgewickGaps(int n) {
        List<Integer> gaps = new ArrayList<>();
        int k = 0;
        while (true) {
            int gap;
            if (k % 2 == 0)
                gap = (int)(9 * Math.pow(2, k) - 9 * Math.pow(2, (double) k / 2) + 1);
            else
                gap = (int)(8 * Math.pow(2, k) - 6 * Math.pow(2, (double) (k + 1) / 2) + 1);

            if (gap > n) break;
            gaps.add(gap);
            k++;
        }
        Collections.reverse(gaps);
        if (!gaps.contains(1)) gaps.add(1);
        return gaps;
    }

    private static List<Integer> getGaps(int n, SequenceType type) {
        return switch (type) {
            case SHELL -> shellGaps(n);
            case KNUTH -> knuthGaps(n);
            case SEDGEWICK -> sedgewickGaps(n);
        };
    }

    public static void sort(int[] arr, SequenceType seqType) {
        List<Integer> gaps = getGaps(arr.length, seqType);
        for (int gap : gaps) {
            for (int i = gap; i < arr.length; i++) {
                int temp = arr[i];
                int j = i;
                while (j >= gap && arr[j - gap] > temp) {
                    arr[j] = arr[j - gap];
                    j -= gap;
                }
                arr[j] = temp;
            }
        }
    }
}
