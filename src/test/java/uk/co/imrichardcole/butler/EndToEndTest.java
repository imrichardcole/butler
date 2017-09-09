package uk.co.imrichardcole.butler;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.Test;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EndToEndTest {

    @Test
    public void run_everything() {
        final Config monitorConfig = ConfigFactory.load("example.conf");
        final List<MonitorConfig> monitors = monitorConfig.getConfigList("monitors")
                .stream()
                .map((Function<Config, MonitorConfig>) config -> {
                    final String monitorName = config.getString("name");
                    final String fileName = config.getString("file.name");
                    final long outputFrequencyMillis = config.getLong("output.frequency");
                    final String objectName = config.getString("object.name");
                    return new MonitorConfig(monitorName, fileName, outputFrequencyMillis, objectName);
                }).collect(Collectors.toList());

        for(MonitorConfig config : monitors) {
            final JMXMonitor jmxMonitor = new JMXMonitor(config.getObjectName());
            new CSVFileWriter(config.getFileName(), jmxMonitor).start(config.getOutputFrequencyMillis());
        }
    }

}
