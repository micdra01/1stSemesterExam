package GUI.Models;

import BE.Category;
import BE.Movie;
import BLL.Interfaces.ICategoryManager;
import BLL.Interfaces.IMovieManager;
import BLL.MovieManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MovieModel {

    private IMovieManager movieManager;
    private ICategoryManager categoryManager;
    private ObservableList<Movie> moviesInList;
    private ObservableList<Category> categoriesInList;

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

    public Category createCategory(Category category) throws Exception {
        return categoryManager.createCategory(category);
    }
}
