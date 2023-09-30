package app.moz.blogapp.service;

import app.moz.blogapp.Entity.Category;
import app.moz.blogapp.Entity.Post;
import app.moz.blogapp.Entity.User;
import app.moz.blogapp.Exceptions.ResourceNotFound;
import app.moz.blogapp.dao.CategoryRepository;
import app.moz.blogapp.dao.PostRepository;
import app.moz.blogapp.payloads.CategoryDto;
import app.moz.blogapp.payloads.PostDTO;
import com.github.javafaker.Cat;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;
    ModelMapper modelMapper;

    PostRepository postRepository;

    public CategoryServiceImpl(CategoryRepository theCategoryRepository, PostRepository thePostRepository, ModelMapper theModelMapper) {
        categoryRepository = theCategoryRepository;
        modelMapper = theModelMapper;
        postRepository = thePostRepository;

    }


    @Override
    public CategoryDto createCategory(CategoryDto categoryDto ) {

        Category category = new Category();
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());

        // Create and associate posts
        List<Post> posts = categoryDto.getPosts().stream()
                .map(postDto -> {
                    Post post = new Post();
                    post.setTitle(postDto.getTitle());
                    post.setContent(postDto.getContent());
                    post.setImage(postDto.getImage());
                    post.setDateCreated(postDto.getDateCreated());
                    post.setCategory(category);
                    return post;
                })
                .collect(Collectors.toList());

        category.setPosts(posts);

        Category savedCategory = categoryRepository.save(category);

        CategoryDto savedCategoryDto = modelMapper.map(savedCategory, CategoryDto.class);

       List<PostDTO> postDTOS = category.getPosts().stream()
               .map((post -> modelMapper.map(post, PostDTO.class)))
               .collect(Collectors.toList());

       savedCategoryDto.setPosts(postDTOS);

       return savedCategoryDto;
    }
    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        List<CategoryDto> categoryDtos = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());

        return categoryDtos;
    }

    @Override
    public CategoryDto getCategoryById(int id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()){
            throw new ResourceNotFound("Category not Found");
        }
        Category category1 = category.get();
        return modelMapper.map(category1, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(int id, CategoryDto categoryDto) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isEmpty()) {
            throw new ResourceNotFound("Category not Found");
        }

        Category existingCategory = categoryOptional.get();

        // Update category properties
        existingCategory.setTitle(categoryDto.getTitle());
        existingCategory.setDescription(categoryDto.getDescription());

        // Map the PostDto list to Post entities and set them in the existing category
        List<Post> updatedPosts = categoryDto.getPosts().stream()
                .map(postDto -> {
                    Post post = modelMapper.map(postDto, Post.class);
                    post.setCategory(existingCategory);
                    return post;
                })
                .collect(Collectors.toList());

        existingCategory.setPosts(updatedPosts);

        // Save the updated category
        Category updatedCategory = categoryRepository.save(existingCategory);

        // Map the updated category to a CategoryDto
        return modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategory(int id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()){
            throw new ResourceNotFound("Category not Found");
        }

        categoryRepository.deleteById(id);


    }
}
