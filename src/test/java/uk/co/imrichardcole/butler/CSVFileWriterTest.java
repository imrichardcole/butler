package uk.co.imrichardcole.butler;

import org.junit.Test;

public class CSVFileWriterTest {

    @Test
    public void can_write_out_values_to_file() {
        final JMXMonitor monitor = new JMXMonitor("java.lang:type=Memory");
        final CSVFileWriter fileWriter = new CSVFileWriter("memory-stats.csv", monitor);
        fileWriter.start(200);
    }

}
