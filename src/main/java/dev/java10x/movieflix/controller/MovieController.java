package dev.java10x.movieflix.controller;

import dev.java10x.movieflix.controller.request.MovieRequest;
import dev.java10x.movieflix.controller.response.MovieResponse;
import dev.java10x.movieflix.entity.Movie;
import dev.java10x.movieflix.mapper.MovieMapper;
import dev.java10x.movieflix.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movieflix/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<List<MovieResponse>> findAllMovies() {
        List<MovieResponse> movies = movieService.findAll().stream()
                .map(MovieMapper::toMovieResponse)
                .toList();
        return ResponseEntity.ok(movies);
    }

    @PostMapping
    public ResponseEntity<MovieResponse> saveMovie(@RequestBody MovieRequest movieRequest) {
        Movie savedMovie = movieService.save(MovieMapper.toMovie(movieRequest));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MovieMapper.toMovieResponse(savedMovie));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> findMovieById(@PathVariable Long id) {
        Optional<Movie> optionalMovie = movieService.findById(id);
        return optionalMovie
                .map(movie -> ResponseEntity.ok(MovieMapper.toMovieResponse(movie)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovieById(@PathVariable Long id) {
        Optional<Movie> optionalMovie = movieService.findById(id);
        if (optionalMovie.isPresent()){
            movieService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> updateMovie (@PathVariable Long id, @RequestBody MovieRequest movieRequest) {
        Optional<Movie> optionalMovie = movieService.update(id, MovieMapper.toMovie(movieRequest));
        return optionalMovie
                .map(movie -> ResponseEntity.ok(MovieMapper.toMovieResponse(movie)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<MovieResponse>> findByCategory(@RequestParam Long category) {
        List<MovieResponse> movies = movieService.findByCategory(category).stream()
                .map(MovieMapper::toMovieResponse)
                .toList();
        return ResponseEntity.ok(movies);
    }

}
