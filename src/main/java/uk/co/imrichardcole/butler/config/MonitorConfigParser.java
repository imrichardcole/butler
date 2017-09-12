package uk.co.imrichardcole.butler.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MonitorConfigParser {

    public Map<String, MonitorConfig> parse(final String configFileName) {
        final Config rawConfig = ConfigFactory.load(configFileName);
        return rawConfig.getConfigList("monitors")
                .stream()
                .map((Function<Config, MonitorConfig>) config -> {
                    final String monitorName = config.getString("name");
                    final String fileName = config.getString("file.name");
                    final long outputFrequencyMillis = config.getLong("output.frequency");
                    final String objectName = config.getString("object.name");
                    return new MonitorConfig(monitorName, fileName, outputFrequencyMillis, objectName);
                }).collect(Collectors.toMap(MonitorConfig::getMonitorName, Function.identity()));
    }

}
