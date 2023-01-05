package BLL;

import BE.Movie;
import BLL.Interfaces.IMovieManager;
import DAL.Interfaces.IMovieDAO;
import DAL.MovieDAO;

import java.util.List;

public class MovieManager implements IMovieManager {

    private IMovieDAO databaseAccess;

    public MovieManager() {databaseAccess = new MovieDAO();
    }

    /**
     * Creates a Movie.
     * @param movie The movie to create.
     * @return Returns the newly created movie.
     * @throws Exception If it fails to create the movie.
     */
    @Override
    public Movie createMovie(Movie movie) throws Exception {
        return databaseAccess.createMovie(movie);
    }

    /**
     * Deletes a movie.
     * @param movie The movie to delete.
     * @throws Exception If it fails to delete the movie.
     */
    @Override
    public void deleteMovie(Movie movie) throws Exception {
        databaseAccess.deleteMovie(movie);
    }

    /**
     * Returns all movies
     * @return A list of all movies.
     * @throws Exception If it fails to retrieve all movies.
     */
    @Override
    public List<Movie> getAllMovies() throws Exception {
        return databaseAccess.getAllMovies();
    }

    /**
     * Update/Edit a Movie
     * @param movie, the selected movie to update
     * @throws Exception If it fails to update the movie.
     */
    @Override
    public void updateMovie(Movie movie) throws Exception {
        databaseAccess.updateMovie(movie);
    }

    /**
     * Filter the list of movies in library using a search query
     * @param query, the string input used to filter
     * @return a list of movies matching the query in either title or category...
     * @throws Exception If it fails to search.
     */
    @Override
    public List<Movie> search(String query) throws Exception {
        return null;
    }

    /**
     * gets a movie from the database by its id
     * @param movieId the movie id for the movie
     * @return returns a movie object
     * @throws Exception
     */
    @Override
    public Movie getMovieFromId(int movieId) throws Exception {
        return null;
    }
}
