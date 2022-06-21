package application.repository;

import application.model.Comment;
import application.model.Restaurant;
import application.model.enums.RestaurantType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

//    @Query("select c from Comment c where c.restaurant.id  = ?1")
//    List<Comment> findByRestaurant(Restaurant restaurant);

}