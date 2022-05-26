package model.util;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class WeekTimeSlot extends TimeSlot {

    private DayOfWeek dayOfWeek;

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
