package GUI.Controllers;

import BE.Movie;
import GUI.Models.MovieModel;
import GUI.Util.ErrorDisplayer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class WarningViewController implements Initializable {
    @FXML
    private ScrollPane warningView;
    @FXML
    private Label warningViewLabel26, warningViewLabel2, warningViewLabel6;
    private MovieModel movieModel;
    private MovieCardController movieCardController;
    @FXML
    private ScrollPane listLowRating, listLowAndLast, listLastViewed;
    private double lowRating = 6.0;
    private ObservableList<Movie> allMovies;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        movieCardController = new MovieCardController();
        warningViewLabel2.prefWidthProperty().bind(warningView.widthProperty());
        warningViewLabel6.prefWidthProperty().bind(warningView.widthProperty());
        warningViewLabel26.prefWidthProperty().bind(warningView.widthProperty());
    }

    public void setContent() {
        try {
            allMovies = movieModel.getMoviesInList();
        } catch (Exception e) {
            ErrorDisplayer.displayError(new Exception(e));
        }
        createLowRatedList();
        createLastViewedList();
        createLowAndLastList();
    }

    private void createLastViewedList() {
        //Create a grid in the ScrollPane to hold all movies
        GridPane grid = new GridPane();
        listLastViewed.setContent(grid);


        //used for placing
        int col = 0;
        int row = 0;

        //loop for creating each movieCard and setting movie info
        for (Movie movie : allMovies){
            //If the movie's last view is > 2 years ago ...
            if (movie.getLastViewed().before(Date.valueOf(LocalDate.now().minusYears(2)))){
                //... it creates a movieCard for said movie and adds it to the content grid
                GridPane movieCard = movieCardController.createMovieCard(movie, movieModel);
                grid.add(movieCard, col, row);
                col++;
            }
        }
    }
    private void createLowAndLastList() {
        //Create a grid in the ScrollPane to hold all movies
        GridPane grid = new GridPane();
        listLowAndLast.setContent(grid);

        //used for placing
        int col = 0;
        int row = 0;

        //loop for creating each movieCard and setting movie info
        for (Movie movie : allMovies){
            //If the movie is both low rated & last view is > 2 years ago ...
            if(movie.getPersonalRating() <= lowRating && movie.getLastViewed().before(Date.valueOf(LocalDate.now().minusYears(2)))) {
                //... it creates a movieCard for said movie and adds it to the content grid
                GridPane movieCard = movieCardController.createMovieCard(movie, movieModel);
                grid.add(movieCard, col, row);
                col++;
            }
        }
    }
    private void createLowRatedList() {
        //Create a grid in the ScrollPane to hold all movies
        GridPane grid = new GridPane();
        listLowRating.setContent(grid);

        //used for placing
        int col = 0;
        int row = 0;

        //loop for creating each movieCard and setting movie info
        for (Movie movie : allMovies){
            //If the movie is low rated ...
            if(movie.getPersonalRating() <= lowRating){
                //... it creates a movieCard for said movie and adds it to the content grid
                GridPane movieCard = movieCardController.createMovieCard(movie, movieModel); //creates the movie card
                grid.add(movieCard, col, row); //adds it to the content gridPane
                col++;
            }
        }
    }

    public void setMovieModel(MovieModel movieModel) {
        this.movieModel = movieModel;
        try {
            allMovies = movieModel.getMoviesInList();
        } catch (Exception e) {
            ErrorDisplayer.displayError(new Exception(e));
        }
    }

}
