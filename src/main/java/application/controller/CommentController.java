package application.controller;

import application.model.Comment;
import application.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "createComment", method = RequestMethod.POST)
    public String createRestaurant(@RequestBody Comment comment) {
        System.out.println(comment.getId());
        return commentService.createComment(comment);
    }
}
