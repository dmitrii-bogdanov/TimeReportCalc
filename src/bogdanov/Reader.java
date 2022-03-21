package bogdanov;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Reader {

    private static final String TICKET = "^rm.*";
    private static final String IN_OUT = "//";
    private static final String TIME = "^\\d\\d?\t\\d\\d?$";
    private static final String RECORD = "^\t-.*";

    private List<File> files;
    private Map<File, Map<String, Report>> reports = new HashMap<>();

    public Reader(List<File> files) {
        this.files = files;

        for (File file : files) {
            reports.put(file, new HashMap<>());
        }

    }

    public Map<File, Map<String, Report>> getReports() {
        return reports;
    }

    public void read() {
        for (File file : files) {

            Map<String, Report> tickets = new HashMap<>();

            try (Scanner scanner = new Scanner(file, "UTF-8")) {

                String ticket;
                Report report = new Report(null);
                boolean isInside = false;
                String str;
                String[] tmp;
                Integer hours = 0;
                Integer minutes = 0;
                boolean hasExited = true;
                int line = 0;
                while (scanner.hasNextLine()) {
                    line++;
                    str = scanner.nextLine();

                    if (str.isEmpty()) {
                        continue;
                    }

                    if (str.matches(TICKET)) {
                        ticket = str.toLowerCase(Locale.ROOT).trim();
                        tickets.putIfAbsent(ticket, new Report(ticket));
                        report = tickets.get(ticket);
                        continue;
                    }

                    if (str.equals(IN_OUT)) {
                        isInside = !isInside;
                        continue;
                    }

                    if (str.matches(TIME)) {

                        tmp = str.trim().split("\t");
                        try {
                            hours = Integer.parseInt(tmp[0]);
                            minutes = Integer.parseInt(tmp[1]);
                        } catch (NumberFormatException e) {
                            throw new RuntimeException("Line " + line + " : " + e.getClass().getSimpleName());
                        }

                        if (hasExited) {
                            report.addStartTime(hours, minutes);
                            hasExited = false;
                        } else {
                            report.addEndTime(hours, minutes);
                            hasExited = true;
                        }

                        continue;
                    }

                    if (isInside) {
                        report.addRecord(str);
                    }

                }

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e.getClass().getSimpleName() + " : " + e.getMessage());
            }

            reports.put(file, tickets);

        }
    }


}
