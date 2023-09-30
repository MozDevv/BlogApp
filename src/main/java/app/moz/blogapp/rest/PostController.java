package app.moz.blogapp.rest;


import app.moz.blogapp.Entity.Category;
import app.moz.blogapp.Entity.Post;
import app.moz.blogapp.Exceptions.ResourceNotFound;
import app.moz.blogapp.payloads.CategoryDto;
import app.moz.blogapp.payloads.PostDTO;
import app.moz.blogapp.service.CategoryService;
import app.moz.blogapp.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    PostService postService;
    CategoryService categoryService;

    ModelMapper modelMapper;



    public PostController(ModelMapper theModelMapper,PostService thePostService, CategoryService thecategoryService) {
        postService = thePostService;
        categoryService = thecategoryService;
        modelMapper = theModelMapper;


    }

    //Get All
    @GetMapping("/posts")
    public List<PostDTO> getAllPosts () {

        return postService.getAllPosts();
    }

    //Create Post
    @PostMapping("/posts/user/{userid}/category/{categoryId}")
    public ResponseEntity<PostDTO> save(@RequestBody PostDTO postDTO,@PathVariable int userid, @PathVariable int categoryId) {

      PostDTO createdPost =  postService.createPost(postDTO, userid, categoryId);

        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }
    //Get One

    @GetMapping("/posts/{postID}")
    public ResponseEntity<PostDTO> getOnePost ( @PathVariable int postID ) {

        PostDTO thePost = postService.getPostById(postID);

        return new  ResponseEntity<>(thePost, HttpStatus.OK);

    }

    @GetMapping("/posts/category/{categoryId}")
    public List<PostDTO> getPostByCategory(@PathVariable int categoryId) {
        CategoryDto categoryDto = categoryService.getCategoryById(categoryId);

        Category category = modelMapper.map(categoryDto, Category.class);

        if (category == null) {
            throw new ResourceNotFound("Category Not Found");
        }
       // List<Post> posts =

        return postService.findByCategory(category);

    }
    //Update Post

    @PutMapping("/posts/{postID}")
    public ResponseEntity<PostDTO> update (@PathVariable int postID, @RequestBody PostDTO postDTO) {

      PostDTO updatedPost =  postService.updatePost(postID, postDTO);

    //  updatedPost.setDateCreated(new Date());

      return new ResponseEntity<PostDTO>(updatedPost, HttpStatus.CREATED);
    }

    //delete Post
    @DeleteMapping("/posts/{postID}")
    public void deletePost(@PathVariable int postID) {

        postService.deletePost(postID);
    }


}
