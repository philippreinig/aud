package Übungsaufgaben.Archive;

public class TailRecursion {

    // Compute power 2^n recursively.
    //
    // The computation must use _tail recursion_!
    // Note: This requires the implementation of a tail recursive
    //       auxiliary method that is called by pow2()!)
    //
    public static int pow2(int n) {
        return pow2(n, 1);
    }

    public static int pow2(int n, int tempResult) {
        if (n == 0) return tempResult;
        else return pow2(n - 1, tempResult * 2);
    }

    // Compute the sum of factors (divisors) of n
    // The factors include 1 but exclude n, e.g., sumFactors(6) ==
    // 1+2+3 == 6 .
    //
    // The computation must use _tail recursion_!
    //
    private static int sumFactors(int n, int sum, int tempNum) {
        if (tempNum == n) return sum;
        else if (n % tempNum == 0) return sumFactors(n, sum + tempNum, tempNum + 1);
        else return sumFactors(n, sum, tempNum + 1);

    }

    public static int sumFactors(int n) {
        return sumFactors(n, 0, 1);
    }

    public static void main(String[] args) {
        System.out.println(pow2(4));
        System.out.println(sumFactors(100));
    }
}