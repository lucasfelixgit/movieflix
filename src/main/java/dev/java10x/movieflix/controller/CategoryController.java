package dev.java10x.movieflix.controller;

import dev.java10x.movieflix.controller.request.CategoryRequest;
import dev.java10x.movieflix.controller.response.CategoryResponse;
import dev.java10x.movieflix.entity.Category;
import dev.java10x.movieflix.mapper.CategoryMapper;
import dev.java10x.movieflix.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movieflix/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> categories = categoryService.findAll().stream()
                .map(CategoryMapper::toCategoryResponse)
                .toList();
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest) {
        Category savedCategory = categoryService.saveCategory(CategoryMapper.toCategory(categoryRequest));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CategoryMapper.toCategoryResponse(savedCategory));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
       return categoryService.findById(id)
               .map(category -> ResponseEntity.ok().body(CategoryMapper.toCategoryResponse(category)))
               .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable Long id) {
        Optional<Category> optionalCategory = categoryService.findById(id);
        if (optionalCategory.isPresent()){
            categoryService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
