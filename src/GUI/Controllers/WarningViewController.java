package GUI.Controllers;

import BE.Movie;
import GUI.Models.MovieModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class WarningViewController implements Initializable {
    private MovieModel movieModel;
    private MovieCardController movieCardController;
    @FXML
    private ScrollPane listLowRating, listLowAndLast, listLastViewed;
    private double lowRating = 6.0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        movieCardController = new MovieCardController();
        //sets the models
        try {
            movieModel = new MovieModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
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
        for (Movie movie : movieModel.getMoviesInList()){
            //If the movie's last view is > 2 years ago ...
            if (movie.getLastViewed().before(Date.valueOf(LocalDate.now().minusYears(2)))){
                //... it creates a movieCard for said movie and adds it to the content grid
                GridPane movieCard = movieCardController.createMovieCard(movie);
                grid.add(movieCard, col, row);

                //makes a space between all movies
                col++;
                grid.add(new Separator(Orientation.HORIZONTAL), col, row);
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
        for (Movie movie : movieModel.getMoviesInList()){
            //If the movie is both low rated & last view is > 2 years ago ...
            if(movie.getPersonalRating() <= lowRating && movie.getLastViewed().before(Date.valueOf(LocalDate.now().minusYears(2)))) {

                //... it creates a movieCard for said movie and adds it to the content grid
                GridPane movieCard = movieCardController.createMovieCard(movie);

                //makes a space between all movies
                grid.add(movieCard, col, row);
                col++;
                grid.add(new Separator(Orientation.HORIZONTAL), col, row);
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
        for (Movie movie : movieModel.getMoviesInList()){
            //If the movie is low rated ...
            if(movie.getPersonalRating() <= lowRating){
                //... it creates a movieCard for said movie and adds it to the content grid
                GridPane movieCard = movieCardController.createMovieCard(movie); //creates the movie card
                grid.add(movieCard, col, row); //adds it to the content gridPane

                //makes a space between all movies
                col++;
                grid.add(new Separator(Orientation.HORIZONTAL), col, row);
                col++;
            }
        }
    }

    public void setMovieModel(MovieModel movieModel) {
        this.movieModel = movieModel;
    }

}
