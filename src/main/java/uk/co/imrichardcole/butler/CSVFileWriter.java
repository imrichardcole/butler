package uk.co.imrichardcole.butler;

import java.io.FileWriter;
import java.io.Flushable;
import java.util.Map;

public class CSVFileWriter {

    private final String fileName;

    public CSVFileWriter(String fileName) {
        this.fileName = fileName;
    }

    public void write(final Map<String, Object> values) {
        try {
            final FileWriter fileWriter = getFileWriter();
            final int size = values.size();
            int columnCounter = 1;
            for (String key : values.keySet()) {
                final Object object = values.get(key);
                fileWriter.write(object.toString());
                if(size != columnCounter) {
                    fileWriter.write(",");
                }
                columnCounter++;
            }
            writeNewLine(fileWriter);
        } catch (Exception e) {
            throw new RuntimeException("exception whilst writing out file", e);
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
            return new FileWriter(fileName, true);
        } catch (Exception e) {
            throw new RuntimeException("unable to create filewriter " + fileName, e);
        }
    }

}
