package uk.co.imrichardcole.butler.utils;

public class Sleeper {

    private Sleeper() {}

    public static void sleep(long timeInMillis) {
        try {
            Thread.sleep(timeInMillis);
        } catch (InterruptedException e) {
            System.out.println("error whilst sleeping" + e);
        }
    }
}
