package uk.co.imrichardcole.butler;

import static uk.co.imrichardcole.butler.utils.Sleeper.sleep;

public class SampleApp {

    public static void main(String[] args) {
        new SampleApp().go();
    }

    private void go() {
        for(int i = 0; i < 10_000_000; i++) {
            final Double junk = i * 3.14d;
            System.out.println(junk);
            sleep(100);
        }
    }

}
