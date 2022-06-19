package application.controller;

import application.model.Comment;
import application.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "updateComment", method = RequestMethod.PUT)
    public String updateComment(@RequestBody Comment comment) {
        return commentService.updateComment(comment);
    }

    @RequestMapping(value = "createComment", method = RequestMethod.POST)
    public String createComment(@RequestBody Comment comment) {
        return commentService.createComment(comment);
    }

    @RequestMapping(value = "createComments", method = RequestMethod.POST)
    public String createComments(@RequestBody List<Comment> comments) {
        return commentService.createComments(comments);
    }

    @RequestMapping(value = "deleteComment", method = RequestMethod.DELETE)
    public String deleteComment(@RequestParam Long id) {
        return commentService.deleteComment(id);
    }

    @RequestMapping(value = "readComments", method = RequestMethod.GET)
    public List<Comment> readComments() {
        return commentService.readAllComments();
    }

    @RequestMapping(value = "readComment", method = RequestMethod.GET)
    public ResponseEntity<Comment> readComment(@RequestParam Long id) {
        Comment comment = commentService.readComment(id);
        if(comment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comment);
    }
}
