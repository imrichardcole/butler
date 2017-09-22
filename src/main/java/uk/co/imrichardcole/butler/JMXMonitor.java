package uk.co.imrichardcole.butler;

import uk.co.imrichardcole.butler.config.MonitorConfig;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.TabularDataSupport;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JMXMonitor {

    private final MonitorConfig config;
    private final MBeanServer mBeanServer;

    public JMXMonitor(MonitorConfig config) {
        this.config = config;
        this.mBeanServer = ManagementFactory.getPlatformMBeanServer();
    }

    public Map<String, Object> getAttributes() {
        try {
            final Map<String, Object> attributes = new HashMap<>();
            final ObjectName objectName = new ObjectName(config.getObjectName());
            final MBeanInfo info = mBeanServer.getMBeanInfo(objectName);
            for (MBeanAttributeInfo attributeInfo : info.getAttributes()) {
                attributes.put(attributeInfo.getName(), attributeInfo);
            }
            return attributes;
        } catch (Exception e) {
            throw new RuntimeException("unable to query jmx bean " + config.getObjectName(), e);
        }
    }

    public Map<String, Object> getAttributeValues() {
        try {
            final Map<String, Object> values = new HashMap<>();
            final ObjectName objectName = new ObjectName(config.getObjectName());
            final MBeanInfo info = mBeanServer.getMBeanInfo(objectName);
            for (MBeanAttributeInfo attributeInfo : info.getAttributes()) {
                final Object value = mBeanServer.getAttribute(objectName, attributeInfo.getName());
                if (value instanceof CompositeData) {
                    final CompositeData compositeData = (CompositeData) value;
                    final Set<String> names = compositeData.getCompositeType().keySet();
                    for (String name : names) {
                        final Object foo = compositeData.get(name);
                        if (!(foo instanceof TabularDataSupport)) {
                            values.put(attributeInfo.getName() + "/" + name, foo);
                        }
                    }
                } else {
                    values.put(attributeInfo.getName(), value);
                }
            }
            return values;
        } catch (Exception e) {
            throw new RuntimeException("unable to query jmx bean " + config.getObjectName(), e);
        }
    }

}
