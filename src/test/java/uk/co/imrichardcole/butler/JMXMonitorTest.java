package uk.co.imrichardcole.butler;

import org.junit.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class JMXMonitorTest {

    @Test
    public void can_get_attribute_names() {
        final JMXMonitor memoryMonitor = new JMXMonitor("java.lang:type=Memory");
        final Map<String, Object> attributes = memoryMonitor.getAttributes();
        assertThat(attributes.size(), equalTo(5));
    }

    @Test
    public void simple_test() {
        final JMXMonitor memoryMonitor = new JMXMonitor("java.lang:type=Memory");
        final Map<String, Object> attributes = memoryMonitor.getAttributeValues();
        assertThat(attributes.size(), equalTo(5));
    }

}
