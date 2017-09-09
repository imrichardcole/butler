package uk.co.imrichardcole.butler;

import java.io.FileWriter;
import java.io.Flushable;
import java.util.Map;

import static uk.co.imrichardcole.butler.utils.Sleeper.sleep;

public class CSVFileWriter {

    private final String fileName;
    private final JMXMonitor monitor;

    public CSVFileWriter(String fileName, JMXMonitor monitor) {
        this.fileName = fileName;
        this.monitor = monitor;
    }

    public void start(long durationMillis) {
        final FileWriter fileWriter = getFileWriter();
        long startTime = System.currentTimeMillis();
        while((System.currentTimeMillis() - startTime) < durationMillis) {
            try {
                final Map<String, Object> attributeValues = monitor.getAttributeValues();
                for (String key : attributeValues.keySet()) {
                    final Object object = attributeValues.get(key);
                    fileWriter.write(object.toString());
                }
            } catch (Exception e) {
                throw new RuntimeException("error whilst writing to file " + fileName, e);
            } finally {
                closeQuietly(fileWriter);
            }
            writeNewLine(fileWriter);
            sleep(1000);
        }
    }

    private void writeNewLine(FileWriter fileWriter) {
        try {
            fileWriter.write("\r\n");
        } catch (Exception e) {
            throw new RuntimeException("error whilst writing new line to file " + fileName, e);
        } finally {
            closeQuietly(fileWriter);
        }
    }

    private void closeQuietly(Flushable flushable) {
        try {
            if (flushable != null) {
                flushable.flush();
            }
        } catch (Exception e) {
            System.out.println("exception during Flushable.flush() - " + e);
        }
    }

    private FileWriter getFileWriter() {
        try {
            return new FileWriter(fileName);
        } catch (Exception e) {
            throw new RuntimeException("unable to create filewriter " + fileName, e);
        }
    }

}
