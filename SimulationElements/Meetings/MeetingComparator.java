package SimulationElements.Meetings;

import java.util.Comparator;

public class MeetingComparator implements Comparator<Meeting> {

    public int compare(Meeting m1, Meeting m2) {
        if (m1.day == m2.day) {
            return Integer.compare(m1.ordinal, m2.ordinal);
        }
        return Integer.compare(m1.day, m2.day);
    }
}
