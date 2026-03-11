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
    // Takes sorted list of anagram lines, writes theAnagrams.tex
    
    public static void writeTexFile(List<String> lines) throws IOException {
        // Create latex directory if it doesn't exist
        File latexDir = new File("latex");
        if (!latexDir.exists()) {
            latexDir.mkdirs();
        }

        PrintWriter texFile = new PrintWriter(new FileWriter("latex/theAnagrams.tex"));
        char letter = 'X';
// Groups entries under bold uppercase letter headings
    
        for (String lemma : lines) {
            if (lemma.isEmpty()) continue;
            char initial = lemma.charAt(0);
            // Print a new letter heading when the first letter changes
            if (Character.toLowerCase(initial) != Character.toLowerCase(letter)) {
                letter = initial;
                texFile.printf(
                    "%n\\vspace{14pt}%n\\noindent\\textbf{\\Large %s}\\\\*[+12pt]%n",
                    Character.toUpperCase(initial)
                );
            }
            texFile.print(lemma + "\n");
        }
        texFile.close();
    }

    public static void main(String[] args) {
        // TODO: orchestrate all steps
    }

}
