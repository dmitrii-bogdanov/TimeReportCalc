package bogdanov;

public class ReportConverter {

    public static void convert(String reportDir) {
        FileManager fileManager = new FileManager(reportDir);
        Reader reader = new Reader(fileManager.getFilesToCalc());
        reader.read();
        Writer writer = new Writer(fileManager.getDoneFiles(reader.getReports().keySet()), reader.getReports());
        writer.write();
    }

}
