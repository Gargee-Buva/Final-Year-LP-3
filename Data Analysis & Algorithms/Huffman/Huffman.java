//javac Huffman.java
//java Huffman
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Node {
    char ch;        // character for leaf nodes
    int freq;       // frequency for leaf or sum for internal nodes
    Node left, right;

    Node(char ch, int freq) {
        this.ch = ch;
        this.freq = freq;
    }

    Node(int freq, Node left, Node right) {
        this.ch = '\0';
        this.freq = freq;
        this.left = left;
        this.right = right;
    }

    boolean isLeaf() {
        return left == null && right == null;
    }
}

public class Huffman {

    // Build Huffman tree from frequency map using a PriorityQueue with a lambda comparator
    public static Node buildHuffmanTree(Map<Character, Integer> freqMap) {
        PriorityQueue<Node> pq = new PriorityQueue<>(
            (n1, n2) -> Integer.compare(n1.freq, n2.freq) // lambda comparator (min-heap by freq)
        );

        // Create leaf nodes
        for (Map.Entry<Character, Integer> e : freqMap.entrySet()) {
            pq.add(new Node(e.getKey(), e.getValue()));
        }

        // Edge-case: single unique character
        if (pq.size() == 1) {
            // duplicate the single node to create a valid tree with two children
            Node only = pq.poll();
            pq.add(new Node(only.freq, only, null));
        }

        // Combine two smallest nodes until one tree remains
        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();
            Node parent = new Node(left.freq + (right != null ? right.freq : 0), left, right);
            pq.add(parent);
        }

        return pq.poll(); // root
    }

    // Generate Huffman codes (recursive)
    public static void generateCodes(Node root, String prefix, Map<Character, String> codeMap) {
        if (root == null) return;

        if (root.isLeaf()) {
            // if prefix is empty (single unique char) assign "0"
            codeMap.put(root.ch, prefix.length() > 0 ? prefix : "0");
            return;
        }

        generateCodes(root.left, prefix + '0', codeMap);
        generateCodes(root.right, prefix + '1', codeMap);
    }

    // Encode text using code map
    public static String encode(String text, Map<Character, String> codeMap) {
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            sb.append(codeMap.get(c));
        }
        return sb.toString();
    }

    // Decode bitstring using the Huffman tree
    public static String decode(String encoded, Node root) {
        if (root == null) return "";
        StringBuilder sb = new StringBuilder();
        Node current = root;
        for (int i = 0; i < encoded.length(); i++) {
            current = (encoded.charAt(i) == '0') ? current.left : current.right;
            if (current == null) break;
            if (current.isLeaf()) {
                sb.append(current.ch);
                current = root;
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter text to encode: ");
        String text = sc.nextLine();
        sc.close();

        if (text == null || text.isEmpty()) {
            System.out.println("Empty input. Nothing to encode.");
            return;
        }

        // Build frequency map
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char c : text.toCharArray()) {
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
        }

        // Build Huffman tree
        Node root = buildHuffmanTree(freqMap);

        // Generate codes
        Map<Character, String> codeMap = new HashMap<>();
        generateCodes(root, "", codeMap);

        // Print codes (using lambda forEach)
        System.out.println("\nHuffman Codes (char : code) :");
        codeMap.forEach((ch, code) -> System.out.println("'" + ch + "' : " + code));

        // Encode & Decode
        String encoded = encode(text, codeMap);
        String decoded = decode(encoded, root);

        System.out.println("\nOriginal text: " + text);
        System.out.println("Encoded bitstring: " + encoded);
        System.out.println("Decoded text: " + decoded);
    }
}
