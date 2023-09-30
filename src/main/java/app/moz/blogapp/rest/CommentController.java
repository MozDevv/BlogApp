package app.moz.blogapp.rest;

import app.moz.blogapp.payloads.CommentDto;
import app.moz.blogapp.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService1) {
        commentService = commentService1;
    }

   // @GetMapping("/comments")

    @PostMapping("/comment/user/{userId}/post/{postId}")
    public ResponseEntity<CommentDto> createComment (@PathVariable int postId,@PathVariable int userId, @RequestBody CommentDto commentDto) {

        CommentDto theCommentDto = commentService.createComment(commentDto, postId, userId);


        return  new ResponseEntity<>(theCommentDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/{id}")
    public void deleteComment (@PathVariable int id){
        commentService.deleteComment(id);
    }


    @GetMapping("/comments/posts/{postId}")
    public Set<CommentDto> getCommentsForSinglePost(@PathVariable int postId){

        Set<CommentDto> commentDtoSet = commentService.getAllCommentsForAPost(postId);

        return commentDtoSet;

    }


}
