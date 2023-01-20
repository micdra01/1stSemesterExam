package BLL;

import BE.Movie;
import BLL.Interfaces.IMovieManager;
import BLL.Util.MovieSearcher;
import DAL.Interfaces.IMovieDAO;
import DAL.MovieDAO;

import java.io.IOException;
import java.util.List;

public class MovieManager implements IMovieManager {

    private final IMovieDAO databaseAccess;
    private final MovieSearcher movieSearcher;

    public MovieManager() throws IOException {
        databaseAccess = new MovieDAO();
        movieSearcher = new MovieSearcher();
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
     * @return a list of movies matching the query in title
     * @throws Exception If it fails to search.
     */
    @Override
    public List<Movie> search(String query, List<Movie> movieInList) throws Exception {
        List<Movie> allMovies = movieInList;

        return movieSearcher.search(allMovies, query);
    }

    /**
     * Filter the list of movies in library using a search query, min. rating & category selection
     * @param query, the string input used to filter
     * @param minIMDBRating, the min. rating
     * @return a list of movies matching the query in title & the min. rating
     * @throws Exception If it fails to search.
     */
    public List<Movie> searchAdvanced(String query, double minIMDBRating, double maxIMDBRating,
                                      double minPersonalRating, double maxPersonalRating, List<String> categories) throws Exception {

        List<Movie> allMovies = getAllMovies();

        return movieSearcher.searchAdvanced(allMovies, query, minIMDBRating, maxIMDBRating,
                minPersonalRating, maxPersonalRating, categories);
    }
}
