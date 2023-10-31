package app.moz.blogapp.service;

import app.moz.blogapp.Entity.Category;
import app.moz.blogapp.Entity.Post;
import app.moz.blogapp.payloads.CategoryDto;
import app.moz.blogapp.payloads.PostDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface PostService {

   // PostDTO createPost(PostDTO postDTO, int userId, int categoryId);

    PostDTO createPost(PostDTO postDTO, int userId, int categoryId);

    List<PostDTO> getAllPosts();

    PostDTO updatePost(int postId, PostDTO updatedPost);

    void deletePost(int id);

    PostDTO getPostById(int id);

    List<PostDTO> findByCategory(Category category);

    List<PostDTO> getPostsByUserId(int userId);

}
