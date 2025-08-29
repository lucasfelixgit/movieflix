package dev.java10x.movieflix.mapper;

import dev.java10x.movieflix.controller.request.MovieRequest;
import dev.java10x.movieflix.controller.response.CategoryResponse;
import dev.java10x.movieflix.controller.response.MovieResponse;
import dev.java10x.movieflix.controller.response.StreamingResponse;
import dev.java10x.movieflix.entity.Category;
import dev.java10x.movieflix.entity.Movie;
import dev.java10x.movieflix.entity.Streaming;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class MovieMapper {

    public static Movie toMovie(MovieRequest movieRequest){

        List<Category> categoryList = movieRequest.categories().stream()
                .map(category -> Category.builder().id(category).build())
                .toList();

        List<Streaming> streamingList = movieRequest.streamings().stream()
                .map(streaming -> Streaming.builder().id(streaming).build())
                .toList();

        return Movie.builder()
                .title(movieRequest.title())
                .description(movieRequest.description())
                .releaseDate(movieRequest.releaseDate())
                .rating(movieRequest.rating())
                .categories(categoryList)
                .streamings(streamingList)
                .build();

    }


    public static MovieResponse toMovieResponse(Movie movie){

        List<CategoryResponse> categoryList = movie.getCategories().stream()
                .map(CategoryMapper::toCategoryResponse)
                .toList();

        List<StreamingResponse> streamingList = movie.getStreamings().stream()
                .map(StreamingMapper::toStreamingResponse)
                .toList();

        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .releaseDate(movie.getReleaseDate())
                .rating(movie.getRating())
                .categories(categoryList)
                .streamings(streamingList)
                .build();

    }

}
