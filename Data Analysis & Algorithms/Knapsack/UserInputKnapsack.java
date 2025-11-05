import java.util.Arrays;
import java.util.Scanner;

class Item {
    int value, weight;
    Item(int value, int weight) {
        this.value = value;
        this.weight = weight;
    }
}

public class UserInputKnapsack {

    static double getMaxValue(Item[] items, int capacity) {
        // Sort using lambda instead of anonymous class
        Arrays.sort(items, (a, b) -> 
            Double.compare((double)b.value / b.weight, (double)a.value / a.weight)
        );

        double totalValue = 0.0;

        for (Item item : items) {
            if (capacity >= item.weight) {
                capacity = capacity - item.weight;
                totalValue = totalValue + item.value;
            } else {
                totalValue = totalValue + item.value * ((double) capacity / item.weight);
                break;
            }
        }
        return totalValue;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of items: ");
        int n = sc.nextInt();
        Item[] items = new Item[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter value and weight of item " + (i + 1) + ": ");
            int value = sc.nextInt();
            int weight = sc.nextInt();
            items[i] = new Item(value, weight);
        }

        System.out.print("Enter capacity of knapsack: ");
        int capacity = sc.nextInt();

        double maxValue = getMaxValue(items, capacity);
        System.out.println("\nMaximum value we can obtain = " + maxValue);

        sc.close();
    }
}
