package dev.java10x.movieflix.service;

import dev.java10x.movieflix.entity.Category;
import dev.java10x.movieflix.entity.Movie;
import dev.java10x.movieflix.entity.Streaming;
import dev.java10x.movieflix.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final CategoryService categoryService;
    private final StreamingService streamingService;

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Movie save(Movie movie) {
        movie.setCategories(this.findCategories(movie.getCategories()));
        movie.setStreamings(this.findStreamings(movie.getStreamings()));
        return movieRepository.save(movie);
    }

    public Optional<Movie> findById(Long id) {
        return movieRepository.findById(id);
    }

    public void deleteById(Long id) {
        movieRepository.deleteById(id);
    }

    public Optional<Movie> update(Long id, Movie updateMovie) {
        Optional<Movie> optionalMovie = findById(id);

        if (optionalMovie.isPresent()){
            Movie movie = optionalMovie.get();

            List<Category> categories = this.findCategories(updateMovie.getCategories());
            List<Streaming> streamings = this.findStreamings(updateMovie.getStreamings());

            movie.setTitle(updateMovie.getTitle());
            movie.setDescription(updateMovie.getDescription());
            movie.setReleaseDate(updateMovie.getReleaseDate());
            movie.setRating(updateMovie.getRating());

            movie.getCategories().clear();
            movie.getCategories().addAll(categories);

            movie.getStreamings().clear();
            movie.getStreamings().addAll(streamings);

            movieRepository.save(movie);

            return Optional.of(movie);
        }
            return Optional.empty();
    }

    public List<Movie> findByCategory (Long categoryId){
        return movieRepository.findMovieByCategories(List.of(Category.builder().id(categoryId).build()));
    }

    private List<Category> findCategories(List<Category> categories) {
        List<Category> categoriesFound = new ArrayList<>();

        categories.forEach(category -> {
           categoryService.findById(category.getId()).ifPresent(categoriesFound::add);
        });

        return categoriesFound;
    }

    private List<Streaming> findStreamings(List<Streaming> streamings) {
        List<Streaming> streamingsFound = new ArrayList<>();

        streamings.forEach(streaming -> {
            streamingService.findById(streaming.getId()).ifPresent(streamingsFound::add);
        });

        return streamingsFound;
    }
}
