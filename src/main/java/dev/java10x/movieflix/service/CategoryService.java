package dev.java10x.movieflix.service;

import dev.java10x.movieflix.controller.response.CategoryResponse;
import dev.java10x.movieflix.entity.Category;
import dev.java10x.movieflix.mapper.CategoryMapper;
import dev.java10x.movieflix.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public CategoryResponse findById(Long id) {
        Optional<Category> optCategory = categoryRepository.findById(id);

        return optCategory.map(CategoryMapper::toCategoryResponse).orElse(null);
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

}
