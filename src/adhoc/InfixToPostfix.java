package adhoc;

import exercises.week01.Palindrome.Stack;

public class InfixToPostfix {

    private static final char OPENING_BRACKET = '(';
    private static final char CLOSING_BRACKET = ')';
    private static final char PLUS = '+';
    private static final char MINUS = '-';
    private static final char MULTIPLICATION = '*';
    private static final char DIVISION = '/';

    private static int operatorPriority(Character c) {
        switch (c) {
            case PLUS:
            case MINUS:
                return 1;
            case MULTIPLICATION:
            case DIVISION:
                return 2;
            case OPENING_BRACKET:
            case CLOSING_BRACKET:
                return 3;
            default:
                return -1;
        }
    }

    private static boolean isNumber(Character c) {
        return Character.isDigit(c);
    }

    private static boolean isOpeningBracket(Character c) {
        return c.equals('(');
    }

    private static boolean isClosingBraket(Character c) {
        return c.equals(')');
    }

    private static boolean isPlusSign(Character c) {
        return c.equals('+');
    }

    private static boolean isMinusSign(Character c) {
        return c.equals('-');
    }

    private static boolean isMultiplicationSign(Character c) {
        return c.equals('*');
    }

    private static boolean isDivisionSign(Character c) {
        return c.equals('/');
    }

    private static boolean isOperator(Character c) {
        return isPlusSign(c) || isMinusSign(c) || isMultiplicationSign(c) || isDivisionSign(c);
    }

    public static String infixToPostfix(String infix) {
        String output = "";
        Character[] chars = infix.chars().mapToObj(c -> (char) c).toArray(Character[]::new);
        Stack<Character> charStack = new Stack<>();
        for (Character c : chars) {
            if (isOperator(c)) {
                while (!charStack.isEmpty() && !isOpeningBracket(charStack.top()) && operatorPriority(charStack.top()) > operatorPriority(c)) {
                    output += charStack.pop();
                }
                charStack.push(c);
            } else if (isNumber(c)) {
                output += c;
            } else if (isOpeningBracket(c)) {
                charStack.push(c);
            } else if (isClosingBraket(c)) {
                while (!charStack.isEmpty() && !isOpeningBracket(charStack.top())) {
                    output += charStack.pop();
                }
                charStack.pop();
            }
        }
        while (!charStack.isEmpty()) {
            output += charStack.pop();
        }
        return output;
    }
}
