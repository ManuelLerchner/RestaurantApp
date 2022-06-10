package application.service;

import application.model.Comment;
import application.model.Restaurant;
import application.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Transactional
    public String createComment(Comment comment) {
        //if (!restaurantRepository.existsById(restaurant.getId())) {
        commentRepository.save(comment);
        return "Comment created successfully";
        //} else {
        //  return "Restaurant already exists";
        //}
    }
}
