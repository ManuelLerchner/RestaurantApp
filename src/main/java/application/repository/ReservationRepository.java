package application.repository;

import application.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {


    @Override
    void deleteById(Long aLong);

    @Transactional
    @Modifying
    @Query("update Reservation r set r.confirmed = ?1 where r.id = ?2")
    int updateConfirmedById(Boolean confirmed, Long id);


}