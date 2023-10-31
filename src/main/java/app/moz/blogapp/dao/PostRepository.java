package app.moz.blogapp.dao;

import app.moz.blogapp.Entity.Category;
import app.moz.blogapp.Entity.Post;
import app.moz.blogapp.payloads.CategoryDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    //Optional<Post> findById(Long postId);

    List<Post> findByCategory(Category category);

    List<Post> findByUser_Id(int user_Id);

    //
}
