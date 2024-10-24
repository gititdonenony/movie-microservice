package com.example.service;

import com.example.dto.MovieCreateResponse;
import com.example.dto.MovieRequest;
import com.example.dto.MovieResponse;
import com.example.model.Movie;
import com.example.repository.MovieRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public MovieCreateResponse createMovie(MovieRequest movieRequest) {
        Movie movie = modelMapper.map(movieRequest, Movie.class);
        Movie savedMovie = movieRepository.save(movie);
        return new MovieCreateResponse(savedMovie.getId(), "Movie created successfully!");
    }

    @Override
    public MovieResponse updateMovie(Long id, MovieRequest movieRequest) {
        // Find the existing movie by ID
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();

            // Update the movie fields with the new data from the DTO
            modelMapper.map(movieRequest, movie);

            // Save the updated movie to the database
            Movie updatedMovie = movieRepository.save(movie);

            // Return the updated movie as a DTO
            return modelMapper.map(updatedMovie, MovieResponse.class);
        } else {
            throw new RuntimeException("Movie not found with id " + id);
        }
    }

    @Override
    public MovieResponse getMovieById(Long id) {
        // Find movie by ID
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id " + id));

        // Map entity to DTO
        return modelMapper.map(movie, MovieResponse.class);
    }

    @Override
    public List<MovieResponse> getAllMovies() {
        // Fetch all movies from the database
        List<Movie> movies = movieRepository.findAll();

        // Map list of Movie entities to list of MovieResponseDTOs
        return movies.stream()
                .map(movie -> modelMapper.map(movie, MovieResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMovie(Long id) {
        // Find and delete movie by ID
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id " + id));

        movieRepository.delete(movie);
    }

}

