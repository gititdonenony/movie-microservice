package com.example.service;

import com.example.dto.MovieCreateResponse;
import com.example.dto.MovieRequest;
import com.example.dto.MovieResponse;

import java.util.List;

public interface MovieService {
    MovieCreateResponse createMovie(MovieRequest movieRequest);

    MovieResponse updateMovie(Long id, MovieRequest movieRequest);

    MovieResponse getMovieById(Long id);

    List<MovieResponse> getAllMovies();

    void deleteMovie(Long id);

}
