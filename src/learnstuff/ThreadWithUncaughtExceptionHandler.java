package learnstuff;

import java.util.Random;

public class ThreadWithUncaughtExceptionHandler {

    public static void main(final String[] args) {
        final Thread thread = new Thread(() -> {
            final Random rand = new Random();
            while (true) {
                final int i = rand.nextInt(20);
                System.out.println("Generated random int: " + i);
                if (i >= 18) {
                    throw new IllegalStateException();
                }
            }
        });
        final Thread.UncaughtExceptionHandler uncaughtExceptionHandler = (t, e) -> System.err.println("Caught an exception in thread " + t + " - " + e);
        thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);

        thread.start();
    }
}
