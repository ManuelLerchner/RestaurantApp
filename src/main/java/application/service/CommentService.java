package application.service;

import application.model.Comment;
import application.model.Restaurant;
import application.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;


    @Transactional
    public String createComment(Comment comment) {
        if (comment.getId() == null) {
            commentRepository.save(comment);
            return "Comment created successfully";
        }
        return "Comment exists already";
    }

    @Transactional
    public String createComments(List<Comment> comments) {
        for (Comment comment : comments) {
            if (comment.getId() == null) {
                commentRepository.save(comment);
            }
        }
        return "created comments";
    }

    @Transactional
    public String deleteComment(Long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.delete(commentRepository.getById(id));
            return "Comment deleted successfully";
        }
        return "Comment does not exist";
    }

    @Transactional
    public String updateComment(Comment updatedComment) {
        if (commentRepository.existsById(updatedComment.getId())) {
            commentRepository.save(updatedComment);
            return "Comment updated successfully";
        }
        return "Comment does not exist and cannot be updated";
    }

    @Transactional
    public List<Comment> readAllComments() {
        return commentRepository.findAll();
    }

    @Transactional
    public Comment readComment(Long id) {
        if (commentRepository.existsById(id)) {
            return commentRepository.getById(id);
        }
        return null;
    }

}