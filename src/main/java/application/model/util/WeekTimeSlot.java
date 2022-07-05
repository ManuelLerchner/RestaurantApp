package application.model.util;

import application.model.Restaurant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.time.DayOfWeek;

@Entity
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
                "startTime=" + getStartTime() +
                '}';
    }
}