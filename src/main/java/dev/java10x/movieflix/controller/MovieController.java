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
@RequestMapping("movieflix/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<List<MovieResponse>> findAll() {
        List<Movie> movieList = movieService.findAll();
        return ResponseEntity.ok(movieList.stream()
                .map(MovieMapper::toMovieResponse)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> findById(@PathVariable Long id) {
        Optional<Movie> optMovie = movieService.findById(id);
        return optMovie.map(movie -> ResponseEntity.ok(MovieMapper.toMovieResponse(movie))).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<MovieResponse>> findByCategory(@RequestParam Long category){
        List<Movie> categories = movieService.findMovieByCategory(category);
        return ResponseEntity.ok()
                .body(categories.stream().map(MovieMapper::toMovieResponse).toList());
    }

    @PostMapping
    public ResponseEntity<MovieResponse> saveMovie(@RequestBody MovieRequest request){
        Movie savedMovie = movieService.save(MovieMapper.toMovie(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MovieMapper.toMovieResponse(savedMovie));
    }

    @PutMapping("{id}")
    public ResponseEntity<MovieResponse> update(@PathVariable Long id, @RequestBody MovieRequest movieRequest){
        return movieService.update(id, MovieMapper.toMovie(movieRequest))
                .map(movie -> ResponseEntity.ok().body(MovieMapper.toMovieResponse(movie)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovieById(@PathVariable Long id) {
        Optional<Movie> optMovie = movieService.findById(id);
        if (optMovie.isPresent()){
            movieService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
