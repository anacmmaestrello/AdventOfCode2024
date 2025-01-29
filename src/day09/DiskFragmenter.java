package day09;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class DiskFragmenter {

    // Function to calculate the checksum of the disk after compacting
    private static long calculateChecksum(List<Integer> disk) {
        long checksum = 0;
        // Iterate through the disk and calculate checksum for file blocks
        for (int i = 0; i < disk.size(); i++) {
            int fileId = disk.get(i);
            if (fileId != -1) {  // Only count file blocks, not free space
                checksum += (long) i * fileId;
            }
        }
        return checksum;
    }

    private static List<Integer> compactDisk(List<Integer> disk) {

        // Initializing the pointers
        int start = 0;  // Pointer for free space (looking for -1)
        int end = disk.size() - 1;  // Pointer for the end of the disk (looking for files)

        // Loop through the disk until the start pointer is less than the end pointer
        while (start < end) {
            // Skip over free spaces from the start pointer
            while (start < disk.size() && disk.get(start) != -1) {
                start++;
            }

            // Skip over non-free spaces (files) from the end pointer
            while (end >= 0 && disk.get(end) == -1) {
                end--;
            }

            // If a file block is found at the end pointer and a free space is found at the start pointer
            if (start < end) {
                // Move the file block to the free space
                disk.set(start, disk.get(end));
                disk.set(end, -1);  // Set the original position as free space (-1)
                start++;  // Move the start pointer to the next free space
                end--;  // Move the end pointer to the next file block
            }
        }

        return disk;
    }

    // Function to parse the disk map into a list of used and free spaces
    private static List<Integer> parseDiskMap(String diskMap) {
        List<Integer> parsedList = new ArrayList<>();

        // Parse the disk map
        for (int i = 0; i < diskMap.length(); i++) {
            int size = Character.getNumericValue(diskMap.charAt(i));
            if (i % 2 == 0) {  // Even index -> used space (file)
                for (int j = 0; j < size; j++) {
                    parsedList.add(i / 2);  // Mark file with its ID
                }
            } else {  // Odd index -> free space
                for (int j = 0; j < size; j++) {
                    parsedList.add(-1);  // Free space marked by -1
                }
            }
        }
        return parsedList;
    }

    // Function to read the disk input from a file
    private static String readDiskInputFromFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readString(path).trim();  // Read the file content and trim any extra spaces/newlines
    }

    // Main method to initiate and calculate
    public static void main(String[] args) {
        try {
            // Read the disk input from the file
            String diskInput = readDiskInputFromFile("src/day09/input.txt");

            // Parse the disk map into the disk layout
            List<Integer> parsedDisk = parseDiskMap(diskInput);

            // Simulate compacting the disk
            List<Integer> compactedDisk = compactDisk(parsedDisk);

            // Calculate the final checksum
            long checksumResult = calculateChecksum(compactedDisk);

            // Output the checksum
            System.out.println("Final Checksum: " + checksumResult);
        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
        }
    }
}
