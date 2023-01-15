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
import java.util.ResourceBundle;

public class HomeViewController implements Initializable {
    private MovieModel movieModel;
    private MovieCardController movieCardController;

    @FXML
    private ScrollPane listAllMovies, listPopular, listRecentlyAdded;

    private double minRatingPopular = 8.2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        movieCardController = new MovieCardController();
        //sets the models
        try {
            movieModel = new MovieModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //createTrendingList();
        createPopularList();
        createAllMoviesList();
        createRecentlyAddedList();
    }

    private void createRecentlyAddedList() {

        GridPane grid = new GridPane();
        listRecentlyAdded.setContent(grid);

        int col = 0;
        int row = 0;

        for (Movie movie : movieModel.getMoviesInList()){
            if (movie.getId() == 10) {
                //movie.getDateAdded().before(Date.valueOf(LocalDate.now().minusWeeks(3)))
                GridPane movieCard = movieCardController.createMovieCard(movie); //creates the movie card
                grid.add(movieCard, col, row); //adds it to the content gridPane

                //makes a space between all movies
                col++;
                grid.add(new Separator(Orientation.HORIZONTAL), col, row);
                col++;
            }
        }

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
        for (Movie movie : movieModel.getMoviesInList()) {
            //If the movie's IMDB Rating is greater than or equal to minRatingPopular variable ...
            if (movie.getImdbRating() >= minRatingPopular) {
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
        for (int i = 0; movieModel.getMoviesInList().size() > i; i++) {
            Movie movie = movieModel.getMoviesInList().get(i);

            //Creates the movieCard and adds it to the content grid
            GridPane movieCard = movieCardController.createMovieCard(movie);
            grid.add(movieCard, col, row);

            //makes a space between all movies
            col++;
            grid.add(new Separator(Orientation.HORIZONTAL), col, row);
            col++;
        }
    }

    public void setMovieModel(MovieModel movieModel) {
        this.movieModel = movieModel;
    }


}
