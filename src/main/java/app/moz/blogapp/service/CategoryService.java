package app.moz.blogapp.service;

import app.moz.blogapp.payloads.CategoryDto;
import com.github.javafaker.Cat;

import java.util.List;

public interface CategoryService {

    public CategoryDto createCategory(CategoryDto categoryDto);

    public List<CategoryDto> getAllCategories();

    public CategoryDto getCategoryById(int id);

    public CategoryDto updateCategory (int id, CategoryDto categoryDto);

    public void deleteCategory(int id);


}
