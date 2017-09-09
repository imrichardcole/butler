package uk.co.imrichardcole.butler;

public class MonitorConfig {

    private final String monitorName;
    private final String fileName;
    private final long outputFrequencyMillis;
    private final String objectName;

    MonitorConfig(String monitorName, String fileName, long outputFrequencyMillis, String objectName) {
        this.monitorName = monitorName;
        this.fileName = fileName;
        this.outputFrequencyMillis = outputFrequencyMillis;
        this.objectName = objectName;
    }

    public String getMonitorName() {
        return monitorName;
    }

    public String getFileName() {
        return fileName;
    }

    public long getOutputFrequencyMillis() {
        return outputFrequencyMillis;
    }

    public String getObjectName() {
        return objectName;
    }

    @Override
    public String toString() {
        return "MonitorConfig{" +
                "monitorName='" + monitorName + '\'' +
                ", fileName='" + fileName + '\'' +
                ", outputFrequencyMillis=" + outputFrequencyMillis +
                ", objectName='" + objectName + '\'' +
                '}';
    }

}

