package model.util;

import java.time.LocalDate;
import java.time.LocalTime;

public class DateTimeSlot extends TimeSlot{

    private LocalDate date;

    public DateTimeSlot(LocalTime startTime, LocalTime endTime, LocalDate date) {
        super(startTime, endTime);
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "DateTimeSlot{" +
                "startTime=" + getStartTime() +
                ", endTime=" + getEndTime() +
                ", date=" + date +
                '}';
    }

    public static void main(String[] args) {
        DateTimeSlot dateTimeSlot = new DateTimeSlot(LocalTime.of(18, 15), LocalTime.of(22,30), LocalDate.of(2022,5,23));
        System.out.println(dateTimeSlot);
    }
}