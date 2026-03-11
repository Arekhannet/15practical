// AREKHANNE 4493911
// Practical 15 - Anagrams using HashMap
// AI Assistance: Claude (claude.ai)  was used to help translate Python logic to Java.

import java.io.*;
import java.util.*;

public class Anagrams {
    //sort characters of word alphabetically
    public static String signature(String word) {
        char[] chars = word.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }
     public static Map<String, Integer> readWords(String filename) throws IOException {
        Map<String, Integer> D = new HashMap<>();

        BufferedReader f = new BufferedReader(
            new InputStreamReader(new FileInputStream(filename), "ISO-8859-1")
        );

        String line = f.readLine();
        while (line != null) {
            String[] words = line.split("\\s+");
            for (String w : words) {
                // Clean: strip punctuation from edges, keep apostrophes
                String W = w.replaceAll("^[0-9(,.;:_.!?\\-\\[\\]]+|[0-9(,.;:_.!?\\-\\[\\]]+$", "");
                w = W;
                if (w.isEmpty()) continue;
                if (D.containsKey(w)) {
                    D.put(w, D.get(w) + 1);
                } else {
                    D.put(w, 1);
                }
            }
            line = f.readLine();
        }
        f.close();
        return D;
    }

    // Mirrors Python's A = {} anagram grouping loop
    
     public static Map<String, List<String>> buildAnagramGroups(Map<String, Integer> D) {
        Map<String, List<String>> A = new HashMap<>();
            // Takes word frequency map, groups words by their signature
        for (String w : D.keySet()) {
            String sig = signature(w.toLowerCase());
            if (!A.containsKey(sig)) {
                A.put(sig, new ArrayList<>());
            }
            A.get(sig).add(w);
        }
        return A;// Returns HashMap of signature >>>>>> list of anagram words (mirrors Python's A = {})
    }

    public static void main(String[] args) {
        // TODO: orchestrate all steps
    }

}
