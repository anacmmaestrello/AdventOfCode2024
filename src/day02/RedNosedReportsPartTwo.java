package day02;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class RedNosedReportsPartTwo {

    public static void main(String[] args) {
        // Path to the input file
        String filePath = "src/day02/input.txt";

        try {
            // Read all lines from the file
            List<String> reports = Files.readAllLines(Paths.get(filePath));

            // Count the number of safe reports (considering the Problem Dampener)
            int safeReports = countSafeReports(reports);

            // Print the result
            System.out.println("Number of safe reports (with Problem Dampener): " + safeReports);
        } catch (IOException e) {
            System.err.println("Error reading the input file: " + e.getMessage());
        }
    }

    // Function to count the number of safe reports
    public static int countSafeReports(List<String> reports) {
        int safeCount = 0;

        for (String reportLine : reports) {
            // Parse the report into a list of integers
            List<Integer> report = parseReport(reportLine);

            // Check if the report is safe, considering the Problem Dampener
            if (isSafeReport(report) || canBeMadeSafeWithOneRemoval(report)) {
                safeCount++;
            }
        }

        return safeCount;
    }

    // Function to check if a single report is safe
    public static boolean isSafeReport(List<Integer> report) {
        if (report.size() < 2) return false; // Not enough data to evaluate

        boolean isIncreasing = true;
        boolean isDecreasing = true;

        for (int i = 0; i < report.size() - 1; i++) {
            int current = report.get(i);
            int next = report.get(i + 1);
            int diff = next - current;

            // Check if the difference is valid (1 to 3 or -1 to -3)
            if (Math.abs(diff) < 1 || Math.abs(diff) > 3) {
                return false;
            }

            // Check monotonicity
            if (diff > 0) {
                isDecreasing = false; // Not decreasing
            } else if (diff < 0) {
                isIncreasing = false; // Not increasing
            }
        }

        // A report is safe if it is either all increasing or all decreasing
        return isIncreasing || isDecreasing;
    }

    // Function to check if a report can be made safe by removing one level
    public static boolean canBeMadeSafeWithOneRemoval(List<Integer> report) {
        // Try removing each element once and check if the report becomes safe
        for (int i = 0; i < report.size(); i++) {
            List<Integer> modifiedReport = new ArrayList<>(report);
            modifiedReport.remove(i);  // Remove the element at index i

            // Check if the modified report is safe
            if (isSafeReport(modifiedReport)) {
                return true;
            }
        }
        return false;
    }

    // Function to parse a report line into a list of integers
    public static List<Integer> parseReport(String reportLine) {
        String[] parts = reportLine.trim().split("\\s+");
        List<Integer> report = new ArrayList<>();
        for (String part : parts) {
            report.add(Integer.parseInt(part));
        }
        return report;
    }
}
