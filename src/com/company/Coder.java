package com.company;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.*;

public class Coder {
//Binary tree realisation

    //each node needs to store frequency of each character
    class Node implements Comparable<Node> {
        final int freq;
        String encode;

        //build encoded message(used for internal nodes and for leafs)
        void buildEncode(String encode) {
            this.encode = encode;
        }

        public Node(int freq) {
            this.freq = freq;
        }

        //compareTo() finds the least element by it's frequency,comparing elements by frequency
        @Override
        public int compareTo(Node o) {
            return Integer.compare(freq, o.freq);
        }
    }

    //class that has two nodes(leafs): left and right
    class InternalNode extends Node {
        Node left;
        Node right;

        //Only for Internal nodes
        @Override
        void buildEncode(String encode) {
            super.buildEncode(encode);
            //recursive method from leafs(childrens)
            left.buildEncode(encode + "0");
            right.buildEncode(encode + "1");
        }

        public InternalNode(Node left, Node right) {
            super(left.freq + right.freq);
            this.left = left;
            this.right = right;
        }

    }
    //each leaf has his own character
    class LeafNode extends Node {
        char ch;
        @Override
        void buildEncode(String encode) {
            super.buildEncode(encode);
            System.out.println(ch + ": " + encode);
        }

        public LeafNode(char ch, int count) {
            super(count);
            this.ch = ch;

        }
    }

    //read text from file
    public static String readUsingScanner(String fileName) throws IOException {
        Scanner scanner = new Scanner(Paths.get(fileName));
        // we can use Delimiter regex as "\\A", "\\Z" or "\\z"
        String data = scanner.useDelimiter("\\A").next();
        return data;


    }

    //shows frequency of each character
    public String launch(String data) {
        Map<Character, Integer> count = new HashMap<>();
        for (int i = 0; i < data.length(); i++) {
            char ch = data.charAt(i);
            if (count.containsKey(ch)) {
                count.put(ch, count.get(ch) + 1);
            } else {
                count.put(ch, 1);
            }
        }
        //add element to queue-add()
        //get element from queue-poll()
        //get least element - peek()
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        //in this map we store nodes that relates to needed symbols
        Map<Character, Node> charNodes = new HashMap();
        //Key is a character,value-frequency
        for (Map.Entry<Character, Integer> entry : count.entrySet()) {
            LeafNode node = new LeafNode(entry.getKey(), entry.getValue());
            charNodes.put(entry.getKey(),node);
            priorityQueue.add(node);
        }
        int freq = 0;
        while (priorityQueue.size() > 1) {
            Node first = priorityQueue.poll();
            Node second = priorityQueue.poll();
            InternalNode node = new InternalNode(first, second);
            freq += node.freq;
            priorityQueue.add(node);
        }
        if (count.size() == 1) {
            freq = data.length();
        }
        System.out.println("Number of symbols: " + count.size() + " quantity: " + freq);
        Node root = priorityQueue.poll();
        root.buildEncode("");
        String encodedString = "";
        System.out.println("Encoded message:");
        for (int i = 0; i < data.length(); i++) {
            char ch = data.charAt(i);
            encodedString += charNodes.get(ch).encode;
        }

        System.out.println();
        System.out.println(encodedString);
        return encodedString;
    }


    //Counts enthropy
    public void countEnthropy(String text, String symbol) {
        float result;
        int count = 0;
        int reps = 0;
        int var = 0;

        float frequency = text.length() - text.replace(symbol, "").length() / symbol.length();
        float probability=frequency/text.length();
        result=(float)Math.abs((1/Math.log(probability)/Math.log(2)));
        System.out.println("Substroke "+symbol+" repeats :"+frequency+" times and has enthropy: "+result);
    }
    //double probability=count/text.length();
    //result=probability*(Math.log(1/probability)/Math.log(2));
    //System.out.println("For "+symbol+" we have : "+reps+" Capacity of enthropy is: "+ result);
}



