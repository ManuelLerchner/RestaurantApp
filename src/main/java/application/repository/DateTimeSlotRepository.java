package application.repository;

import application.model.util.DateTimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DateTimeSlotRepository extends JpaRepository<DateTimeSlot, Long> {
}