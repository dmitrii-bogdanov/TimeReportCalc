package bogdanov;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class FileManager {

    private static final String DIR_TIME = "time";
    private static final String DIR_DONE = "done";
    private static final String REG_EXP_FILENAME = "^\\d\\d\\d\\d-\\d\\d-\\d\\d.txt$";
    private static final String DELIMETER = "\\";
    private static final String EXT = ".txt";

    private String reportDirPath;

    public FileManager(String reportDir) {
        this.reportDirPath = reportDir;
    }

    public List<File> getFilesToCalc() {
        List<File> files = getFiles(DIR_TIME);
        files.removeAll(getFiles(DIR_DONE));
        files.remove(getTodayFile());
        return files;
    }

    private List<File> getFiles(String subDir) {
        File[] files = getSubDir(subDir).listFiles();
        return files == null
                ? Collections.emptyList()
                : Arrays.stream(files).filter(file -> file.getName().matches(REG_EXP_FILENAME)).collect(Collectors.toList());
    }

    private File getSubDir(String subDir) {
        return new File(reportDirPath + DELIMETER + subDir);
    }

    public Map<File, File> getDoneFiles(Collection<File> timeFiles) {
        Map<File, File> tmp = new HashMap<>();
        for (File timeFile : timeFiles) {
            tmp.put(timeFile, new File(getSubDir(DIR_DONE).getAbsolutePath() + DELIMETER + timeFile.getName()));
        }
        return tmp;
    }

    private File getTodayFile() {
        return new File(getSubDir(DIR_DONE).getAbsolutePath() + DELIMETER + LocalDate.now() + EXT);
    }

}
