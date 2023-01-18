package GUI.Controllers;

import BE.Movie;
import GUI.Models.MovieModel;
import GUI.Util.ErrorDisplayer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeViewController implements Initializable {
    private MovieModel movieModel;
    private MovieCardController movieCardController;

    @FXML
    private ScrollPane listAllMovies, listPopular, listLastAdded;
    private ObservableList<Movie> allMovies;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        movieCardController = new MovieCardController();
    }

    public void setContent(){
        try {
            allMovies = movieModel.getMoviesInList();
        } catch (Exception e) {
            ErrorDisplayer.displayError(new Exception(e));
        }
        createPopularList();
        createAllMoviesList();
        createLastAddedList();
    }

    /**
     * creates the gridPane for the Popular list
     * fills it with movies from list
     */
    private void createPopularList() {
        //Create a grid in the ScrollPane to hold all movies
        GridPane grid = new GridPane();
        listPopular.setContent(grid);

        //used for placing
        int col = 0;
        int row = 0;

        //loop for creating each movieCard and setting movie info
        for (Movie movie : allMovies) {
            //If the movie's IMDB Rating is greater than or equal to minRatingPopular variable ...
            double minRatingPopular = 7.5;
            if (movie.getImdbRating() >= minRatingPopular) {
                //... it creates a movieCard for said movie and adds it to the content grid
                GridPane movieCard = movieCardController.createMovieCard(movie, movieModel);
                grid.add(movieCard, col, row);
                col++;
            }
        }
    }

    /**
     * creates the gridPane for the All Movies list
     * fills it with movies from list
     */
    private void createAllMoviesList() {
        //Create a grid in the ScrollPane to hold all movies
        GridPane grid = new GridPane();
        listAllMovies.setContent(grid);

        //used for placing
        int col = 0;
        int row = 0;

        //loop for creating each movieCard and setting movie info
        for(Movie movie : allMovies) {
            //Creates the movieCard and adds it to the content grid
            GridPane movieCard = movieCardController.createMovieCard(movie, movieModel);
            grid.add(movieCard, col, row);
            col++;
        }
    }

    private void createLastAddedList() {
        //Create a grid in the ScrollPane to hold all movies
        GridPane grid = new GridPane();
        listLastAdded.setContent(grid);

        //used for placing
        int col = 0;
        int row = 0;

        //loop for getting the last six added movies
        for (int i = allMovies.size() -1; i > allMovies.size() -7; i--) {
            Movie movie = allMovies.get(i);

            //Creates the movieCard and adds it to the content grid
            GridPane movieCard = movieCardController.createMovieCard(movie, movieModel); //creates the movie card
            grid.add(movieCard, col, row); //adds it to the content gridPane
            col++;
        }
    }

    public void setMovieModel(MovieModel movieModel) {
        this.movieModel = movieModel;
    }
}
