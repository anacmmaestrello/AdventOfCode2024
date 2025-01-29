package day09;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DiskFragmenterPartTwo {
    public static void main(String[] args) throws IOException {
        String input = new BufferedReader(new FileReader("src/day09/sample_input1.txt")).readLine();
        int[] filesystem = new int[input.length()];
        for(int i = 0; i < filesystem.length; i++) {
            filesystem[i] = input.charAt(i) - '0';
        }

        solvePartOne(filesystem);
        solvePartTwo(filesystem);
    }

    private static void solvePartOne(int[] filesystem) {
        long checksum = 0;
        int currIndex = 0;

        int left = 0;
        int right = filesystem.length - 1;
        int spaceNeeded = filesystem[right];
        while(left < right) {
            for(int i = 0; i < filesystem[left]; i++){
                checksum += (long) (left / 2) * currIndex;
                currIndex++;
            }
            left++;

            for(int i = 0; i < filesystem[left]; i++) {
                if(spaceNeeded == 0) {
                    right -= 2;
                    if(right <= left) {
                        break;
                    }
                    spaceNeeded = filesystem[right];
                }
                checksum += (long) (right / 2) * currIndex;
                currIndex++;
                spaceNeeded--;
            }
            left++;
        }
        for(int i = 0; i < spaceNeeded; i++) {
            checksum += (long) (right / 2) * currIndex;
            currIndex++;
        }

        System.out.println(checksum);
    }

    private static void solvePartTwo(int[] filesystem) {
        // Initialize the checksum variable
        long checksum = 0L;

        // Step 1: Prepare an array to track the starting positions of free space blocks
        // openStartIndex[i] will store the start position of the i-th free block
        int[] openStartIndex = new int[filesystem.length];

        // The first index starts at 0
        openStartIndex[0] = 0;

        // Step 2: Calculate the starting positions of all free space blocks
        // Each entry in filesystem represents either a file block or a free block
        // The openStartIndex array tracks the starting index for each block of free space
        for (int i = 1; i < filesystem.length; i++) {
            // The starting position of the i-th block is calculated as the sum of the previous starting positions
            openStartIndex[i] = openStartIndex[i - 1] + filesystem[i - 1];
        }

        // Step 3: Iterate through the filesystem from right to left (i.e., starting from the last block)
        // We process files from right to left to move the largest file IDs first (which ensures better compaction)
        for (int right = filesystem.length - 1; right >= 0; right -= 2) {
            boolean found = false; // This flag indicates if we found space to move the current file

            // Step 4: Try to find space for the current file to move to the left (search among previous free blocks)
            for (int left = 1; left < right; left += 2) {
                // Check if the current free block has enough space to hold the current file
                if (filesystem[left] >= filesystem[right]) {
                    // Step 5: Move the current file to the left if there is enough space
                    // Calculate the checksum by updating the positions of the file blocks
                    for (int i = 0; i < filesystem[right]; i++) {
                        checksum += (long) (right / 2) * (openStartIndex[left] + i);
                    }

                    // Update the free block by decreasing its size by the size of the file being moved
                    filesystem[left] -= filesystem[right];

                    // Update the starting position of the free block after moving the file
                    openStartIndex[left] += filesystem[right];

                    // Set the flag to true indicating the file has been moved
                    found = true;
                    break; // Exit the inner loop since we found a place for this file
                }
            }

            // Step 6: If no space was found, place the file in its current position
            if (!found) {
                for (int i = 0; i < filesystem[right]; i++) {
                    checksum += (long) (right / 2) * (openStartIndex[right] + i);
                }
            }
        }

        // Step 7: Log the compacted disk to track the final positions of files and free spaces
        System.out.println("Compacted Disk: ");
        for (int i = 0; i < filesystem.length; i++) {
            System.out.print(filesystem[i] + " ");
        }
        System.out.println(); // Print a newline for better formatting

        // Step 8: Output the final checksum value
        System.out.println("Final Checksum: " + checksum);
    }

}
