package uk.co.imrichardcole.butler;

import org.junit.Ignore;
import org.junit.Test;
import uk.co.imrichardcole.butler.config.MonitorConfig;
import uk.co.imrichardcole.butler.config.MonitorConfigParser;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Ignore
public class EndToEndTest {

    @Test
    public void wire_it_all_up() throws InterruptedException {
        final MonitorConfigParser parser = new MonitorConfigParser();
        final Map<String, MonitorConfig> config = parser.parse("example.conf");
        final ExecutorService service = Executors.newFixedThreadPool(2);
        for (String monitorName : config.keySet()) {
            final MonitorConfig monitorConfig = config.get(monitorName);
            final JMXMonitor jmxMonitor = new JMXMonitor(monitorConfig);
            final CSVFileWriter fileWriter = new CSVFileWriter(monitorConfig.getFileName());
            service.submit(new TimedMonitor(monitorConfig.getOutputFrequencyMillis(), jmxMonitor, fileWriter));
        }
        service.awaitTermination(5000, TimeUnit.SECONDS);
    }

}
