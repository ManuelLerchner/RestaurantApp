package application.model.util;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

@Entity
public class DateTimeSlot extends TimeSlot {

    @Column(name = "date")
    private LocalDate date;

    public DateTimeSlot() {
        super();
    }

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

    public boolean isCollision(DateTimeSlot other) {
        if (!this.getDate().equals(other.getDate())) {
            return false;
        }
        if (this.getEndTime().compareTo(other.getStartTime()) < 0 || this.getStartTime().compareTo(other.getEndTime()) > 0) {
            return false;
        }
        return true;
    }

    public static DateTimeSlot convertToDateTimeSlot(String date, double startTime, double endTime) {
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            return null;
        }

        int startHour = (int) startTime;
        int startMinute = (int) ((startTime - startHour) * 60);

        int endHour = Math.max(0, Math.min(23, (int) endTime));
        int endMinute = Math.max(0, Math.min(59, (int) ((endTime - endHour) * 60)));

        return new DateTimeSlot(LocalTime.of(startHour, startMinute), LocalTime.of(endHour, endMinute), localDate);
    }

    public static void main(String[] args) {
        DateTimeSlot dateTimeSlot = new DateTimeSlot(LocalTime.of(18, 15), LocalTime.of(22,30), LocalDate.of(2022,5,23));
        System.out.println(dateTimeSlot);
    }
}