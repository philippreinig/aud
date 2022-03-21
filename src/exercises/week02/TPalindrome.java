package exercises.week02;

import aud.Stack;

public class TPalindrome {
    public static boolean isTPalindrome(String text) {
        Character[] chars = text.chars().mapToObj(c -> (char) c).toArray(Character[]::new);
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < text.length(); i++) {
            if (chars[i] != ')') {
                stack.push(chars[i]);
            } else {
                String temp = "";
                while (stack.top() != '(') {
                    temp += stack.pop();
                }
                stack.pop();
                if (isPalindrome(temp)) {
                    stack.push('*');
                } else {
                    return false;
                }
            }
        }
        String temp = "";
        while (!stack.isEmpty()) {
            temp += stack.pop();
        }
        return isPalindrome(temp);
    }

    private static boolean isPalindrome(String text) {
        Character[] chars = text.chars().mapToObj(c -> (char) c).toArray(Character[]::new);
        int mid = (int) Math.round(chars.length / 2.0);
        Stack<Character> stackFromFront = new Stack<>();
        Stack<Character> stackFromBack = new Stack<>();
        for (int i = 0; i < mid; i++) {
            stackFromFront.push(chars[i]);
            stackFromBack.push(chars[chars.length - 1 - i]);
        }
        while (!stackFromFront.isEmpty()) {
//            System.out.println("sfF: " + stackFromFront.toString());
//            System.out.println("sfB: " + stackFromBack.toString());
            if (!stackFromFront.pop().toString().equals(stackFromBack.pop().toString())) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        String testCase1 = "abc(aha)(u)cba";
        String testCase2 = "abc(ah(otto)v(atta)ha)cba";
        String testCase3 = "*(*)";
        String testCase4 = "(()())";
        String[] testCases = new String[]{testCase1, testCase2, testCase3, testCase4};
        for (String testCase : testCases) {
            System.out.print(testCase + ": ");
            if (isTPalindrome(testCase)) {
                System.out.println("SUCCESSFUL");
            } else {
                System.out.println("FAILED");
            }
        }
    }
}