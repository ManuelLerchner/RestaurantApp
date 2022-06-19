package application.model.util;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.time.DayOfWeek;

@JsonSerialize
@Entity
@Table(name = "week_time_slot")
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

}