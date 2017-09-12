package uk.co.imrichardcole.butler;

import java.util.Map;
import java.util.concurrent.Callable;

public class TimedMonitor implements Callable<Void> {

    private final long queryFrequency;
    private final JMXMonitor monitor;
    private final CSVFileWriter writer;

    public TimedMonitor(long queryFrequency,
                        JMXMonitor monitor,
                        CSVFileWriter writer) {
        this.queryFrequency = queryFrequency;
        this.monitor = monitor;
        this.writer = writer;
    }

    @Override
    public Void call() throws Exception {
        long start = System.currentTimeMillis();
        while(true) {
            if((System.currentTimeMillis() - queryFrequency) > start) {
                final Map<String, Object> values = monitor.getAttributeValues();
                writer.write(values);
                start = System.currentTimeMillis();
            }
        }
    }

}
