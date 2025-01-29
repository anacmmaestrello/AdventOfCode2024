package day13;

import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
//        long startTime = System.nanoTime();
//        Map<Integer, String> map = new HashMap<>();
//        for (int i = 0; i < 1_000_000; i++) {
//            map.put(i, "Value" + i);
//        }
//        long endTime = System.nanoTime();
//        System.out.println("Execution time: " + (endTime - startTime) + " ns");
        // 150053200 ns
        // 118693200 ns
//
        long startTime = System.nanoTime();
        int initialCapacity = (int) (1_000_000 / 0.75) + 1;
        Map<Integer, String> map = new HashMap<>(initialCapacity);
        for (int i = 0; i < 1_000_000; i++) {
            map.put(i, "Value" + i);
        }
        long endTime = System.nanoTime();
        System.out.println("Execution time: " + (endTime - startTime) + " ns");


    }
}
