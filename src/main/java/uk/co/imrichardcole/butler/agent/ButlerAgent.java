package uk.co.imrichardcole.butler.agent;

import uk.co.imrichardcole.butler.CSVFileWriter;
import uk.co.imrichardcole.butler.JMXMonitor;
import uk.co.imrichardcole.butler.TimedMonitor;
import uk.co.imrichardcole.butler.config.MonitorConfig;
import uk.co.imrichardcole.butler.config.MonitorConfigParser;

import java.lang.instrument.Instrumentation;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ButlerAgent {

    public static void premain(String args, Instrumentation instrumentation) {
        final String configFile = args;
        final Map<String, MonitorConfig> configMap = new MonitorConfigParser().parse(configFile);
        final ExecutorService service = Executors.newFixedThreadPool(2);
        for (String monitorName : configMap.keySet()) {
            final MonitorConfig monitorConfig = configMap.get(monitorName);
            final JMXMonitor jmxMonitor = new JMXMonitor(monitorConfig);
            final CSVFileWriter fileWriter = new CSVFileWriter(monitorConfig.getFileName());
            service.submit(new TimedMonitor(monitorConfig.getOutputFrequencyMillis(), jmxMonitor, fileWriter));
            System.out.println("launched monitor - " + monitorConfig);
        }
    }

}
