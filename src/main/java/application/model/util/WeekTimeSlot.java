package application.model.util;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.DayOfWeek;
import java.time.LocalTime;

//@Embeddable
public class WeekTimeSlot extends TimeSlot {

//    @Column(name = "dayOfWeek")
    private DayOfWeek dayOfWeek;
    public WeekTimeSlot() {
        super(LocalTime.of(12, 0), LocalTime.of(23, 59));
    }

    public WeekTimeSlot(LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek) {
        super(startTime, endTime);
        this.dayOfWeek = dayOfWeek;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public String toString() {
        return "WeekTimeSlot{" +
                "startTime=" + getStartTime() +
                ", endTime=" + getEndTime() +
                ", dayOfWeek=" + dayOfWeek +
                '}';
    }

    public static void main(String[] args) {
        WeekTimeSlot weekTimeSlot = new WeekTimeSlot(LocalTime.of(18, 15), LocalTime.of(22,30), DayOfWeek.MONDAY);
        System.out.println(weekTimeSlot);
    }

}