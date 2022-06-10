package application.repository;

import application.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @Transactional
    @Modifying
    @Query("update Restaurant r set r.name = ?1 where r.id = ?2")
    void updateNameById(String name, long id);


}
