package uk.co.imrichardcole.butler.config;

import org.junit.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class MonitorConfigParserTest {

    @Test
    public void can_load_config_from_file() {
        final MonitorConfigParser parser = new MonitorConfigParser();
        final Map<String, MonitorConfig> configMap = parser.parse("basic-example.conf");
        assertThat(configMap.size(), equalTo(2));

        final MonitorConfig memoryMonitorConfig = configMap.get("JMX memory logger");
        assertThat(memoryMonitorConfig.getFileName(), equalTo("memory-stats.csv"));
        assertThat(memoryMonitorConfig.getOutputFrequencyMillis(), equalTo(1000L));
        assertThat(memoryMonitorConfig.getObjectName(), equalTo("java.lang:type=Memory"));

        final MonitorConfig threadMonitorConfig = configMap.get("JMX thread logger");
        assertThat(threadMonitorConfig.getFileName(), equalTo("thread-stats.csv"));
        assertThat(threadMonitorConfig.getOutputFrequencyMillis(), equalTo(2000L));
        assertThat(threadMonitorConfig.getObjectName(), equalTo("java.lang:type=Threading"));
    }

}
