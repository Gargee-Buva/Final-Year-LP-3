import java.util.Arrays;

class Item {
    int value, weight;

    Item(int value, int weight) {
        this.value = value;
        this.weight = weight;
    }
}

public class FractionalKnapsack {

    // Function to get the maximum value in the knapsack
    static double getMaxValue(Item[] items, int capacity) {
        // Sort items by value-to-weight ratio (descending order) using lambda
        Arrays.sort(items, (a, b) -> 
            Double.compare((double) b.value / b.weight, (double) a.value / a.weight)
        );

        double totalValue = 0.0;

        for (Item item : items) {
            if (capacity >= item.weight) {
                // Take the whole item
                capacity -= item.weight;
                totalValue += item.value;
            } else {
                // Take fractional part of the item
                totalValue += item.value * ((double) capacity / item.weight);
                break; // Knapsack is full
            }
        }

        return totalValue;
    }

    public static void main(String[] args) {
        Item[] items = {
            new Item(40, 20),
            new Item(100, 10),
            new Item(60, 40),
            new Item(120, 30)
        };

        int capacity = 60;

        double maxValue = getMaxValue(items, capacity);
        System.out.println("Maximum value we can obtain = " + maxValue);
    }
}
