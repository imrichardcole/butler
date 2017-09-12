package uk.co.imrichardcole.butler;

import org.junit.Test;
import uk.co.imrichardcole.butler.config.MonitorConfig;

import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class JMXMonitorTest {

    @Test
    public void can_get_attribute_names() {
        final MonitorConfig config = new MonitorConfig("sample", "sample.csv", 1000, "java.lang:type=Memory");
        final JMXMonitor memoryMonitor = new JMXMonitor(config);
        final Map<String, Object> attributes = memoryMonitor.getAttributes();
        assertThat(attributes.size(), equalTo(5));
    }

}
