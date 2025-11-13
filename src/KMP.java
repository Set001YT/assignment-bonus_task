// Simple, clear KMP (Knuth-Morris-Pratt) implementation in Java.
// - buildLPS: builds the longest proper prefix-suffix (lps) array
// - search: returns list of starting indices where pattern occurs in text
import java.util.*;

public class KMP {
    // Build LPS (longest proper prefix which is also suffix) array for pattern
    public static int[] buildLPS(String pat) {
        int m = pat.length();
        int[] lps = new int[m];
        int len = 0; // length of the previous longest prefix suffix
        lps[0] = 0; // lps[0] is always 0
        int i = 1;
        while (i < m) {
            if (pat.charAt(i) == pat.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    // fall back in the pattern
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }

    // Search for all occurrences of pattern in text using KMP
    // Returns list of 0-based starting indices
    public static List<Integer> search(String text, String pat) {
        List<Integer> occurrences = new ArrayList<>();
        int n = text.length();
        int m = pat.length();
        if (m == 0) {
            // define behavior: empty pattern matches at every position (common convention)
            for (int i = 0; i <= n; i++) occurrences.add(i);
            return occurrences;
        }
        int[] lps = buildLPS(pat);

        int i = 0; // index for text
        int j = 0; // index for pat
        while (i < n) {
            if (text.charAt(i) == pat.charAt(j)) {
                i++;
                j++;
                if (j == m) {
                    occurrences.add(i - j);
                    // continue searching for next matches
                    j = lps[j - 1];
                }
            } else {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
        return occurrences;
    }

    // Helper to run a single test and print summary
    static void runTest(String name, String text, String pat) {
        System.out.println("=== Test: " + name + " ===");
        System.out.println("Text length: " + text.length() + ", Pattern length: " + pat.length());
        long t0 = System.nanoTime();
        List<Integer> occ = search(text, pat);
        long t1 = System.nanoTime();
        System.out.println("Occurrences count: " + occ.size());
        if (!occ.isEmpty()) {
            System.out.println("First 10 indices (or fewer): " + occ.subList(0, Math.min(10, occ.size())));
            if (occ.size() > 10) {
                System.out.println("Last index: " + occ.getLast());
            }
        } else {
            System.out.println("No occurrences found.");
        }
        double elapsedMs = (t1 - t0) / 1_000_000.0;
        System.out.printf("Elapsed time: %.4f ms%n", elapsedMs);
        System.out.println();
    }

    // Build a long test text by repeating a small pattern N times
    static String buildLongText(String unit) {
        return unit.repeat(Math.max(0, 2000));
    }

}
