package exercises.week04.RecursionToIterative;

import aud.Stack;

public class RecursionToStack {

    public static int whatRec(int n) {
        if (n < 10) return n;
        else return whatRec(n / 10) + n % 10;
    }

    //-----------------------------------------------------------------//
    public static int whatStack(int n) {
        Stack<Integer> stack = new Stack<>();
        while (n >= 10) {
            stack.push(n % 10);
            n /= 10;
        }
        int crossSum = n;
        while (!stack.isEmpty()) crossSum += stack.pop();
        return crossSum;
    }

    //-----------------------------------------------------------------//
    public static void main(String[] args) {
        int[] testNums = {45743289, 57438235, 840943822, 54783, 57849060, 4578933};
        for (int testNum : testNums)
            System.out.println(testNum + ": whatRec: " + whatRec(testNum) + ", whatStack: " + whatStack(testNum) + ", same result: " + (whatRec(testNum) == whatStack(testNum)));
    }
}