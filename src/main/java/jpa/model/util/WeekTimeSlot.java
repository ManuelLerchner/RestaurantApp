package jpa.model.util;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Enumerated;

@Embeddable
public class WeekTimeSlot extends TimeSlot {
    @Enumerated
    @Column(name = "day_of_week")
    private DayOfWeek dayOfWeek;

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public String toString() {
        return "WeekTimeSlot{" +
                "dayOfWeek=" + dayOfWeek +
                '}';
    }
}