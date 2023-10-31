package app.moz.blogapp.service;

import app.moz.blogapp.Entity.Comment;
import app.moz.blogapp.Entity.Post;
import app.moz.blogapp.Entity.User;
import app.moz.blogapp.Exceptions.ResourceNotFound;
import app.moz.blogapp.dao.CommentRepository;
import app.moz.blogapp.dao.PostRepository;
import app.moz.blogapp.dao.UserRepository;
import app.moz.blogapp.payloads.CommentDto;
import app.moz.blogapp.payloads.PostDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService{

    PostRepository postRepository;

    CommentRepository commentRepository;

    UserRepository userRepository;

    ModelMapper modelMapper;

    public CommentServiceImpl(PostRepository postRepository1, CommentRepository commentRepository1, UserRepository userRepository1, ModelMapper modelMapper1){
        postRepository = postRepository1;

        commentRepository = commentRepository1;

        userRepository  = userRepository1;

        modelMapper = modelMapper1;
    }
    @Override
    public CommentDto createComment(CommentDto commentDto, int postId, int userId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {throw new ResourceNotFound("Post Not Found");}


        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {throw new ResourceNotFound("User Not Found");}

        Comment comment = modelMapper.map(commentDto, Comment.class);

        comment.setPost(post.get());
        comment.setUser(user.get());

        Comment savedComment = commentRepository.save(comment);

        return modelMapper.map(savedComment, CommentDto.class);

    }

    @Override
    public void deleteComment(int commentId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new ResourceNotFound("Comment Not found"));

        commentRepository.delete(comment);

    }

    @Override
    public List<CommentDto> getAllComments() {
        List<Comment> comments= commentRepository.findAll();

      return   comments.stream()
                .map(comment-> modelMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Set<CommentDto> getAllCommentsForAPost(int postId) {

        Optional<Post> postOptional = postRepository.findById(postId);

        if (postOptional.isEmpty()) {throw new ResourceNotFound("Not Found");}


        Set<Comment> commentList = postOptional.get().getComments();


        Set<CommentDto> commentDtoSet = commentList.stream()
                .map((comment -> modelMapper.map(comment, CommentDto.class)))
                .collect(Collectors.toSet());


        return commentDtoSet;
    }



}
