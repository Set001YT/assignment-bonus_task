// Example main class with three tests
// main: runs three example tests (short, medium, long) and prints results

public class Main{
    public static void main(String[] args) {
        // Short test
        String textShort = "abababab";
        String patShort = "ab";
        KMP.runTest("Short", textShort, patShort);

        // Medium test
        String textMedium = "abxabcabcaby";
        String patMedium = "abcaby";
        KMP.runTest("Medium", textMedium, patMedium);

        // Long test: repeat "abcde" 2000 times -> length 10000
        String unit = "abcde";
        String textLong = KMP.buildLongText(unit);
        String patLong = "cdeab";
        KMP.runTest("Long (10000 chars)", textLong, patLong);
    }
}
