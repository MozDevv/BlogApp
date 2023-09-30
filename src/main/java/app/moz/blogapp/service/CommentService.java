package app.moz.blogapp.service;

import app.moz.blogapp.payloads.CommentDto;

import java.util.List;
import java.util.Set;

public interface CommentService {

    CommentDto createComment ( CommentDto commentDto, int postId, int userId);

    void deleteComment( int commentId);

    List<CommentDto> getAllComments();

    Set<CommentDto> getAllCommentsForAPost(int postId);


}
