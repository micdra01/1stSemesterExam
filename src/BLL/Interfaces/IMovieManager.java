package BLL.Interfaces;

import BE.Movie;

import java.util.List;

public interface IMovieManager {



    /**
     * Creates a Movie.
     * @param movie The movie to create.
     * @return Returns the newly created movie.
     * @throws Exception If it fails to create the movie.
     */
    Movie createMovie(Movie movie) throws Exception;


    /**
     * Deletes a movie.
     * @param movie The movie to delete.
     * @throws Exception If it fails to delete the movie.
     */
    void deleteMovie(Movie movie) throws Exception;


    /**
     * Returns all movies
     * @return A list of all movies.
     * @throws Exception If it fails to retrieve all movies.
     */
    List<Movie> getAllMovies() throws Exception;

    /**
     * Update/Edit a Movie
     * @param movie, the selected movie to update
     * @throws Exception If it fails to update the movie.
     */
    void updateMovie(Movie movie) throws Exception;


    /**
     * Filter the list of movies in library using a search query
     * @param query, the string input used to filter
     * @return a list of movies matching the query in either title or category...
     * @throws Exception If it fails to search.
     */
    List<Movie> search(String query) throws Exception;
}
