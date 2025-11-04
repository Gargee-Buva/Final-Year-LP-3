// Program to calculate Fibonacci numbers using
// both recursive and non-recursive methods.

import java.util.Scanner;

public class Fibonacci {

    // Recursive method
    static int recursiveFibonacci(int n) {
        if (n <= 1)
            return n;
        return recursiveFibonacci(n - 1) + recursiveFibonacci(n - 2);
    }

    // Non-recursive (Iterative) method
    static int iterativeFibonacci(int n) {
        if (n <= 1)
            return n;

        int prev = 0, curr = 1, next = 0;
        for (int i = 2; i <= n; i++) {
            next = prev + curr;
            prev = curr;
            curr = next;
        }
        return curr;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter n: ");
        int n = sc.nextInt();

        System.out.println("\nUsing Recursive Method:");
        for (int i = 0; i < n; i++)
            System.out.print(recursiveFibonacci(i) + " ");

        System.out.println("\n\nUsing Iterative Method:");
        for (int i = 0; i < n; i++)
            System.out.print(iterativeFibonacci(i) + " ");

        sc.close();
    }
}
