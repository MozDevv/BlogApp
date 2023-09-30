package app.moz.blogapp.rest;

import app.moz.blogapp.payloads.CategoryDto;
import app.moz.blogapp.service.CategoryService;
import com.github.javafaker.Cat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    CategoryService categoryService;

    public CategoryController(CategoryService theCategoryService){
        categoryService = theCategoryService;
    }

    @GetMapping("/categories")
    public List<CategoryDto> getAll() {

        return categoryService.getAllCategories();
    }
    @PostMapping("/categories")
    public ResponseEntity<CategoryDto> createCategory (@RequestBody CategoryDto categoryDto) {

        CategoryDto creatcategoryDto = categoryService.createCategory(categoryDto);

        return new ResponseEntity<>(creatcategoryDto, HttpStatus.CREATED);
    }
    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryDto> getOne(@PathVariable int id) {
        CategoryDto theCategory = categoryService.getCategoryById(id);

        return new ResponseEntity<>(theCategory, HttpStatus.OK);

    }
    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryDto> update(@PathVariable int id, @RequestBody CategoryDto categoryDto) {
        return new ResponseEntity<>(categoryService.updateCategory(id, categoryDto), HttpStatus.OK);

    }
    @DeleteMapping("/categories/{id}")
    public  void deleteCategory (@PathVariable int id) {

        categoryService.deleteCategory(id);

    }

}
