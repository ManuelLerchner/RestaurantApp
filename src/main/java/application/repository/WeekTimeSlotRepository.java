package application.repository;

import application.model.util.WeekTimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeekTimeSlotRepository extends JpaRepository<WeekTimeSlot, Long> {
}