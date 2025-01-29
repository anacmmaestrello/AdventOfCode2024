package day01;

import java.io.*;
import java.util.*;

public class PartTwo {
    private static int[] leftList;

    public static void main(String[] args) throws IOException {
        String filePath = "src/day01/input.txt";
        List<int[]> lists = readAndParseInput(filePath);

        int[] leftList = lists.get(0);
        int[] rightList = lists.get(1);

        int similarityScore = calculateSimilarityScore(leftList, rightList);
        System.out.println("Similarity Score: " + similarityScore);
    }

    public static List<int[]> readAndParseInput(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        List<Integer> leftList = new ArrayList<>();
        List<Integer> rightList = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            String[] parts = line.trim().split("\\s+");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid input format: each line must contain exactly two numbers.");
            }
            leftList.add(Integer.parseInt(parts[0]));
            rightList.add(Integer.parseInt(parts[1]));
        }
        reader.close();

        int[] leftArray = leftList.stream().mapToInt(Integer::intValue).toArray();
        int[] rightArray = rightList.stream().mapToInt(Integer::intValue).toArray();

        return Arrays.asList(leftArray, rightArray);
    }

    public static int calculateSimilarityScore(int[] leftList, int[] rightList) {
        // Step 1: Create a frequency map for the right list
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : rightList) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        // Step 2: Calculate the similarity score
        int similarityScore = 0;
        for (int num : leftList) {
            int frequency = frequencyMap.getOrDefault(num, 0);
            similarityScore += num * frequency;
        }

        return similarityScore;
    }
}
