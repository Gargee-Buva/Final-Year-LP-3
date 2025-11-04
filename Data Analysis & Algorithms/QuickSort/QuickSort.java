import java.util.*;

public class QuickSort {

    // ----------------- DETERMINISTIC QUICK SORT -----------------
    static void deterministicQuickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            deterministicQuickSort(arr, low, pivotIndex - 1);
            deterministicQuickSort(arr, pivotIndex + 1, high);
        }
    }

    // Partition method (last element as pivot)
    static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    // ----------------- RANDOMIZED QUICK SORT -----------------
    static void randomizedQuickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = randomizedPartition(arr, low, high);
            randomizedQuickSort(arr, low, pivotIndex - 1);
            randomizedQuickSort(arr, pivotIndex + 1, high);
        }
    }

    // Randomized partition: randomly pick a pivot index
    static int randomizedPartition(int[] arr, int low, int high) {
        int randomPivot = low + (int)(Math.random() * (high - low + 1));
        swap(arr, randomPivot, high); // Move random pivot to end
        return partition(arr, low, high);
    }

    // ----------------- UTILITY METHODS -----------------
    static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    static void printArray(int[] arr) {
        for (int val : arr)
            System.out.print(val + " ");
        System.out.println();
    }

    // ----------------- MAIN -----------------
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of elements: ");
        int n = sc.nextInt();
        int[] arr = new int[n];

        System.out.println("Enter " + n + " elements:");
        for (int i = 0; i < n; i++)
            arr[i] = sc.nextInt();

        // Copy array for comparison
        int[] arr1 = Arrays.copyOf(arr, n);
        int[] arr2 = Arrays.copyOf(arr, n);

        // Deterministic Quick Sort
        long start1 = System.nanoTime();
        deterministicQuickSort(arr1, 0, n - 1);
        long end1 = System.nanoTime();
        long time1 = end1 - start1;

        // Randomized Quick Sort
        long start2 = System.nanoTime();
        randomizedQuickSort(arr2, 0, n - 1);
        long end2 = System.nanoTime();
        long time2 = end2 - start2;

        // Output
        System.out.println("\nSorted Array (Deterministic Quick Sort):");
        printArray(arr1);
        System.out.println("Time taken (ns): " + time1);

        System.out.println("\nSorted Array (Randomized Quick Sort):");
        printArray(arr2);
        System.out.println("Time taken (ns): " + time2);

        sc.close();
    }
}
