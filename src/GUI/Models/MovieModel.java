package GUI.Models;

import BE.Movie;
import BLL.Interfaces.IMovieManager;
import BLL.MovieManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
}
