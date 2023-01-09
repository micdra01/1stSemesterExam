package GUI.Models;

import BE.Movie;

import BLL.Interfaces.IMovieManager;
import BLL.MovieManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class MovieModel {

    private IMovieManager movieManager;

    private ObservableList<Movie> moviesInList;

    public MovieModel() throws Exception {
        movieManager = new MovieManager();

        //gets all movies in a list
        moviesInList = FXCollections.observableArrayList();
        moviesInList.addAll(movieManager.getAllMovies());
    }

    public ObservableList<Movie> getMoviesInList() {
        return moviesInList;
    }

    public void deleteMovie(Movie movie) throws Exception {
        movieManager.deleteMovie(movie);
    }

    public Movie createMovie(Movie movie) throws Exception {
        return movieManager.createMovie(movie);
    }

    public void updateMovie(Movie movie) throws Exception {
        movieManager.updateMovie(movie);
    }
    public Movie getMovieById(int movieId) throws Exception{
        return movieManager.getMovieFromId(movieId);
    }

    /**
     * Filter the list of movies using a search query
     * @param query, a String to search for.
     */
    public void search(String query) throws Exception {
        List<Movie> searchResults = movieManager.search(query);

        moviesInList.clear();
        moviesInList.addAll(searchResults);
    }

    /**
     * Filter the list of movies using a search query
     * @param query, a String to search for.
     */
    public void searchAdvanced(String query, double minRating, List<String> categories) throws Exception {
        List<Movie> searchResults = movieManager.searchAdvanced(query, minRating, categories);

        moviesInList.clear();
        moviesInList.addAll(searchResults);
    }
}
