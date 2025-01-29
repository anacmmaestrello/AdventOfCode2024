package day13;

import java.io.*;
import java.util.*;

public class ClawContraption {
    public static class Machine {
        int aX, aY, bX, bY, pX, pY;

        public Machine(int aX, int aY, int bX, int bY, int pX, int pY) {
            this.aX = aX;
            this.aY = aY;
            this.bX = bX;
            this.bY = bY;
            this.pX = pX;
            this.pY = pY;
        }
    }

    private static class GCDResult {
        int gcd, x, y;

        GCDResult(int gcd, int x, int y) {
            this.gcd = gcd;
            this.x = x;
            this.y = y;
        }
    }

    private static GCDResult extendedGCD(int a, int b) {
        if (b == 0) {
            return new GCDResult(a, 1, 0);
        }
        GCDResult result = extendedGCD(b, a % b);
        int x = result.y;
        int y = result.x - (a / b) * result.y;
        return new GCDResult(result.gcd, x, y);
    }

    private static Integer solveMachine(Machine machine) {
        int aX = machine.aX, aY = machine.aY, bX = machine.bX, bY = machine.bY;
        int pX = machine.pX, pY = machine.pY;

        System.out.println("Solving machine with:");
        System.out.println("  Button A: X+" + aX + ", Y+" + aY);
        System.out.println("  Button B: X+" + bX + ", Y+" + bY);
        System.out.println("  Prize: X=" + pX + ", Y=" + pY);

        // Extended GCD for X-axis
        GCDResult xResult = extendedGCD(aX, bX);
        if (pX % xResult.gcd != 0) {
            System.out.println("  Prize unreachable on X-axis.");
            return null; // X-axis unreachable
        }

        // Extended GCD for Y-axis
        GCDResult yResult = extendedGCD(aY, bY);
        if (pY % yResult.gcd != 0) {
            System.out.println("  Prize unreachable on Y-axis.");
            return null; // Y-axis unreachable
        }

        // Scale the base solutions to the target
        int scaleX = pX / xResult.gcd;
        int scaleY = pY / yResult.gcd;

        int xBaseA = xResult.x * scaleX;
        int xBaseB = xResult.y * scaleX;
        int yBaseA = yResult.x * scaleY;
        int yBaseB = yResult.y * scaleY;

        // Step adjustments for valid solutions
        int aStepX = bX / xResult.gcd;
        int bStepX = -aX / xResult.gcd;
        int aStepY = bY / yResult.gcd;
        int bStepY = -aY / yResult.gcd;

        int bestCost = Integer.MAX_VALUE;

        // Adjust coefficients (kX and kY independently)
        // Adjust solutions for alignment
        for (int kX = -1000; kX <= 1000; kX++) {
            int alignedAX = xBaseA + kX * aStepX;
            int alignedBX = xBaseB + kX * bStepX;

            if (alignedAX < 0 || alignedBX < 0) continue; // Skip invalid solutions

            for (int kY = -1000; kY <= 1000; kY++) {
                int alignedAY = yBaseA + kY * aStepY;
                int alignedBY = yBaseB + kY * bStepY;

                if (alignedAY < 0 || alignedBY < 0) continue; // Skip invalid solutions

                // Check alignment
                if (alignedAX == alignedAY && alignedBX == alignedBY) {
                    int cost = 3 * alignedAX + alignedBX;
                    bestCost = Math.min(bestCost, cost);
                }
            }
        }

        if (bestCost == Integer.MAX_VALUE) {
            System.out.println("  No valid solution found for this machine.");
            return null;
        }

        System.out.println("  Best cost for this machine: " + bestCost);
        return bestCost;
    }


    public static void main(String[] args) {
        List<Machine> machines = new ArrayList<>();
        String inputFile = "src/day13/sample_input.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] machineLines = new String[3];
                machineLines[0] = line;
                machineLines[1] = br.readLine().trim();
                machineLines[2] = br.readLine().trim();

                int aX = Integer.parseInt(machineLines[0].split("X\\+")[1].split(",")[0].trim());
                int aY = Integer.parseInt(machineLines[0].split("Y\\+")[1].trim());
                int bX = Integer.parseInt(machineLines[1].split("X\\+")[1].split(",")[0].trim());
                int bY = Integer.parseInt(machineLines[1].split("Y\\+")[1].trim());
                int pX = Integer.parseInt(machineLines[2].split("X=")[1].split(",")[0].trim());
                int pY = Integer.parseInt(machineLines[2].split("Y=")[1].trim());

                machines.add(new Machine(aX, aY, bX, bY, pX, pY));
                br.readLine(); // Skip empty line between machines
            }
        } catch (IOException e) {
            System.out.println("Error reading input file: " + e.getMessage());
            return;
        }

        int totalCost = 0;
        int prizesWon = 0;

        for (Machine machine : machines) {
            Integer cost = solveMachine(machine);
            if (cost != null) {
                prizesWon++;
                totalCost += cost;
            }
        }

        System.out.println("Prizes won: " + prizesWon);
        System.out.println("Total cost: " + totalCost);
    }
}
