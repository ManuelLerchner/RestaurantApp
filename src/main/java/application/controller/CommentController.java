package application.controller;

import application.model.Comment;
import application.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * This method updates the comment in the repository with the given comment
     *
     * @body Updated Comment
     * @return String with affirmative or negative result
     */
    @RequestMapping(value = "updateComment", method = RequestMethod.PUT)
    public String updateComment(@RequestBody Comment comment) {
        return commentService.updateComment(comment);
    }

    /**
     * This method creates a new comment in the repository with the given comment
     *
     * @body New Comment
     * @return String with affirmative or negative result
     */
    @RequestMapping(value = "createComment", method = RequestMethod.POST)
    public String createComment(@RequestBody Comment comment) {
        return commentService.createComment(comment);
    }

    /**
     * This method creates multiple new comments in the repository with the given commentlist
     *
     * @body List of comments
     * @return String with affirmative or negative result
     */
    @RequestMapping(value = "createComments", method = RequestMethod.POST)
    public String createComments(@RequestBody List<Comment> comments) {
        return commentService.createComments(comments);
    }

    /**
     * This method deletes a comment from the repository with the given id
     *
     * @param id Id of the comment to delete
     * @return String with affirmative or negative result
     */
    @RequestMapping(value = "deleteComment", method = RequestMethod.DELETE)
    public String deleteComment(@RequestParam Long id) {
        return commentService.deleteComment(id);
    }

    /**
     * This method returns a list of all comments in the repository
     *
     * @return List of all comments
     */
    @RequestMapping(value = "readComments", method = RequestMethod.GET)
    public List<Comment> readComments() {
        return commentService.readAllComments();
    }

    /**
     * This method returns the comment in the repository with the given id
     *
     * @return Comment with the given id
     */
    @RequestMapping(value = "readComment", method = RequestMethod.GET)
    public ResponseEntity<Comment> readComment(@RequestParam Long id) {
        Comment comment = commentService.readComment(id);
        if (comment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comment);
    }
}
