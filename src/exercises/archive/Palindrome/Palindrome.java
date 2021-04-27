package exercises.archive.Palindrome;

public class Palindrome {
    // Test if text is a palindrome.
    // Ignore upper/lower case, white space, and punctuation.
    //

    public static boolean isPalindrome(String text) {
        //    text = text.toLowerCase(Locale.ROOT).replace(".", "").replace(",", "").replace(";", "").replace(" ", "").replace("?", "").replace("-", "").replace("!", "");
        text = text.toLowerCase().replaceAll("[^a-z0-9]", "");
        Character[] chars = text.chars().mapToObj(c -> (char) c).toArray(Character[]::new);
        int mid = (int) Math.round(chars.length / 2.0);
        Stack<Character> stackFromFront = new Stack<>();
        Stack<Character> stackFromBack = new Stack<>();
        for (int i = 0; i < mid; i++) {
            stackFromFront.push(chars[i]);
            stackFromBack.push(chars[chars.length - 1 - i]);
        }


        while (!stackFromFront.isEmpty()) {
            System.out.println("sfF: " + stackFromFront.toString());
            System.out.println("sfB: " + stackFromBack.toString());
            if (!stackFromFront.pop().toString().equals(stackFromBack.pop().toString())) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        String[] testCases = {"Reliefpfeiler", "Madam", "Lagerregal", "Marktkram", "Ein Esel lese nie.", "Gurken hol ohne Krug!", "Na, Fakir, Paprika-Fan?", "O, Streit irritiert so!", "Eine Horde bedrohe nie!"};
        boolean allTestsSuccessful = true;
        for (String testCase : testCases) {
            System.out.println("testing: " + testCase + "| result: " + isPalindrome(testCase));
            if (!isPalindrome(testCase)) allTestsSuccessful = false;
        }
        if (allTestsSuccessful) {
            System.out.println("ALL TESTS PASSED SUCCESSFUL");
        } else {
            System.out.println("TESTS FAILED");
        }
    }
}
