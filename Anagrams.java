// AREKHANNE 4493911
// Practical 15 - Anagrams using HashMap
// AI Assistance: Claude - was used to help translate some Python logic to Java.

import java.io.*;
import java.util.*;

public class Anagrams {

    // Sorts characters of a word alphabetically to create a unique key
    // e.g. "reader" -> "adeerr"
    public static String signature(String word) {
        char[] chars = word.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    // Reads words from file, strips punctuation, counts frequency
    // Returns a HashMap of word >>>>>> frequency (mirrors Python's D = {})
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

    // Groups words by their signature into anagram groups
    // Returns HashMap of signature -> list of anagram words (mirrors Python's A = {})
    public static Map<String, List<String>> buildAnagramGroups(Map<String, Integer> D) {
        Map<String, List<String>> A = new HashMap<>();

        for (String w : D.keySet()) {
            String sig = signature(w.toLowerCase());
            if (!A.containsKey(sig)) {
                A.put(sig, new ArrayList<>());
            }
            A.get(sig).add(w);
        }
        return A;
    }

    // Takes sorted list of anagram lines, writes theAnagrams.tex
    // Groups entries under bold uppercase letter headings
    public static void writeTexFile(List<String> lines) throws IOException {
        // Create latex directory if it doesn't exist
        File latexDir = new File("latex");
        if (!latexDir.exists()) {
            latexDir.mkdirs();
        }

        PrintWriter texFile = new PrintWriter(new FileWriter("latex/theAnagrams.tex"));
        char letter = 'X';

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

    // Main method - orchestrates all steps
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Anagrams <inputfile>");
            return;
        }

        try {
            // Step 1: Read and clean words from file
            System.out.println("Reading words from: " + args[0]);
            Map<String, Integer> D = readWords(args[0]);
            System.out.println("Unique words found: " + D.size());

            // Step 2: Build anagram groups
            System.out.println("Building anagram groups...");
            Map<String, List<String>> A = buildAnagramGroups(D);

            // Step 3: Build sorted list of anagram lines with rotations
            System.out.println("Generating anagram lines...");
            List<String> anagramLines = new ArrayList<>();

            for (String key : A.keySet()) {
                List<String> group = A.get(key);
                if (group.size() > 1) {
                    // Build the anagram list string
                    StringBuilder sb = new StringBuilder();
                    for (String word : group) {
                        if (sb.length() == 0) {
                            sb.append(word);
                        } else {
                            sb.append(" ").append(word);
                        }
                    }
                    // Add all rotations (mirrors Python's repeat loop)
                    String al = sb.toString();
                    anagramLines.add(al + "\\\\");
                    for (int i = 0; i < group.size() - 1; i++) {
                        int space = al.indexOf(' ');
                        al = al.substring(space + 1) + ' ' + al.substring(0, space);
                        anagramLines.add(al + "\\\\");
                    }
                }
            }

            // Step 4: Sort the lines
            Collections.sort(anagramLines);

            // Step 5: Write the LaTeX file
            System.out.println("Writing latex/theAnagrams.tex...");
            writeTexFile(anagramLines);

            System.out.println("Done! Anagram groups found: " +
                    A.values().stream().filter(g -> g.size() > 1).count());

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
