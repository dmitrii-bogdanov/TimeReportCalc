package bogdanov;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class Writer {

    private static final String END_LINE = "\n";

    private final Map<File, File> links;
    private final Map<File, Map<String, Report>> reports;

    public Writer(Map<File, File> links, Map<File, Map<String, Report>> reports) {
        this.links = links;
        this.reports = reports;
    }

    public void write() {
        for (File file : reports.keySet()) {
            try (FileWriter fileWriter = new FileWriter(links.get(file))) {

                for (Report report : reports.get(file).values()) {
                    fileWriter.write(report.ticket + END_LINE);
                    fileWriter.write(report.getHoursString() + END_LINE);
                    fileWriter.write(END_LINE);

                    for (String record : report.getRecords()) {
                        fileWriter.write(record + END_LINE);
                    }

                    fileWriter.write(END_LINE);
                }

            } catch (IOException e) {
                throw new RuntimeException(e.getClass().getSimpleName() + " : " + e.getMessage());
            }
        }
    }

}
