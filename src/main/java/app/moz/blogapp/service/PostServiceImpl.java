package app.moz.blogapp.service;

import app.moz.blogapp.Entity.Category;
import app.moz.blogapp.Entity.Post;
import app.moz.blogapp.Entity.User;
import app.moz.blogapp.Exceptions.ResourceNotFound;
import app.moz.blogapp.dao.CategoryRepository;
import app.moz.blogapp.dao.PostRepository;
import app.moz.blogapp.dao.UserRepository;
import app.moz.blogapp.payloads.PostDTO;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {


    private ModelMapper modelMapper;

    private UserRepository userRepository;

    private CategoryRepository categoryRepository;

    private PostRepository postRepository;

    public  PostServiceImpl (CategoryRepository categoryRepository1,UserRepository userRepository1 ,PostRepository thePostRepository, ModelMapper theModelMapper){
        postRepository = thePostRepository;
        modelMapper = theModelMapper;
        userRepository = userRepository1;
        categoryRepository = categoryRepository1;
    }




    @Override
    public PostDTO createPost(PostDTO postDTO, int userId, int categoryId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("Not Found at all! frm service post"));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new EntityNotFoundException("Category Not Found"));

        Post post = modelMapper.map(postDTO, Post.class);


      post.setDateCreated(new Date());

      post.setUser(user);

      post.setCategory(category);

        Post newPost = postRepository.save(post);

        return modelMapper.map(newPost, PostDTO.class);
    }


    @Override
    public List<PostDTO> getAllPosts() {

        List<Post> posts = postRepository.findAll();



        return posts.stream()
                .map(post -> modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO updatePost(int postId, PostDTO updatedPost) {


        Optional<Post> post_1 = postRepository.findById(postId);

        if (post_1.isEmpty())
        {
            throw new RuntimeException("Post with id " + postId + "not found");

        }
        Post existing_Post = post_1.get();

        existing_Post.setContent(updatedPost.getContent());

        existing_Post.setImage(updatedPost.getImage());

        existing_Post.setTitle(updatedPost.getTitle());

      //  existing_Post.setDateCreated(updatedPost.getDateCreated());

        Post updated = postRepository.save(existing_Post);

        return modelMapper.map(updated, PostDTO.class);
    }

    @Override
    public void deletePost(int id) {

        Optional<Post> expected = postRepository.findById(id);

        if (expected.isEmpty()){
            throw new RuntimeException("Post Not Found");

        }

        postRepository.delete(expected.get());

    }

    @Override
    public PostDTO getPostById(int id) {

        Optional<Post> post = postRepository.findById(id);

        if (post.isEmpty()){
            throw new RuntimeException("Not Found");
        }



        return modelMapper.map(post.get(), PostDTO.class);
    }

    @Override
    public List<PostDTO> findByCategory(Category category) {

             List<Post> posts =   postRepository.findByCategory(category);

             List<PostDTO> postDTOList = posts.stream()
                     .map((post -> modelMapper.map(post, PostDTO.class)))
                     .collect(Collectors.toList());

        return postDTOList;
    }

    @Override
    public List<PostDTO> getPostsByUserId(int userId) {

        List<Post> posts = postRepository.findByUser_Id(userId);

        List<PostDTO> postDTOS = posts.stream()
                .map(post -> modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());

        return postDTOS;
    }



}
