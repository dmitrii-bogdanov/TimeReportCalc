package bogdanov;

import java.util.ArrayList;
import java.util.List;

public class Report {

    public final String ticket;
    private int timeMinutes;
    private List<String> records = new ArrayList<>();
    private boolean hasAlreadyAdded = false;

    public Report(String ticket) {
        this.ticket = ticket;
    }

    public void addStartTime(int hours, int minutes) {
        timeMinutes -= getMinutes(hours, minutes);
        hasAlreadyAdded = true;
    }

    public void addEndTime(int hours, int minutes) {
        timeMinutes += getMinutes(hours, minutes);
        hasAlreadyAdded = false;
    }

    private int getMinutes(int hours, int minutes) {
        return hours * 60 + minutes;
    }

    public List<String> getRecords() {
        return records;
    }

    public void addRecord(String record) {
        records.add(record);
    }

    public String getHoursString() {
        int hoursLong = 100 * timeMinutes / 60;
        int realPart;
        return (hoursLong / 100) + "." + ((realPart = hoursLong % 100) < 10 ? "0" + realPart : realPart);
    }

}
