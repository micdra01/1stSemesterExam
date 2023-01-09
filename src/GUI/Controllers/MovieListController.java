package GUI.Controllers;

import BE.Movie;
import GUI.Models.MovieModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * todo make a instance variable for movieModel.moviesInList so we can chose the specific list we want shown in our content window (categories, favourite mm)
 */


public class MovieListController implements Initializable {

    public ScrollPane movieListView, homeView, listAllMovies, listPopular, listTrending;
    @FXML
    private MovieModel movieModel;

    MainController mainController;
    private double minRatingPopular = 8.2;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //sets the models
        try {
            movieModel = new MovieModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (movieListView != null) {
            createContentGrid();
        }
        if (homeView != null) {
            //createTrendingList();
            createPopularList();
            createAllMoviesList();
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

            GridPane movieCard = createMovieCard(movie);//creates the movie card
            grid.add(movieCard, col, row);//adds it to the content gridPane

            //makes a space between all movies
            col++;
            grid.add(new Separator(Orientation.HORIZONTAL), col, row);
            col++;
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
            if (movie.getImdbRating() > minRatingPopular) {
                GridPane movieCard = createMovieCard(movie);//creates the movie card
                grid.add(movieCard, col, row);//adds it to the content gridPane

                //makes a space between all movies
                col++;
                grid.add(new Separator(Orientation.HORIZONTAL), col, row);
                col++;
            }
        }
    }

    /**
     * creates the gridPane for movieContent
     * fills it with movies from list
     */
    public void createContentGrid() {
        //Create a grid in the ScrollPane to hold all movies
        GridPane grid = new GridPane();
        movieListView.setContent(grid);

        //used for placing
        int col = 0;
        int row = 0;
        //loop for creating each movieCard and setting movie info
        for (int i = 0; movieModel.getMoviesInList().size() > i; i++) {
            Movie movie = movieModel.getMoviesInList().get(i);

            GridPane movieCard = createMovieCard(movie);//creates the movie card

            grid.add(movieCard, col, row);//adds it to the content gridPane

            //makes a space between all movies
            col++;
            grid.add(new Separator(Orientation.HORIZONTAL), col, row);

            //loop for positioning movieCards in grid
            if(col < 6 ){
                col++;
            }else {
                col = 0;
                row++;
                grid.add(new Separator(Orientation.VERTICAL), col, row);
                row++;
            }

        }
    }

    /**
     * create the movieCard gridPane and fills it with info from chosen movie
     * picture, title and rating
     * @param movie, the specific movie in the list
     * @return
     */
    private GridPane createMovieCard(Movie movie) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/MovieCard.fxml"));
        Parent root = null;

        try {

            root = loader.load();
        } catch (IOException e) {
            new Exception("Failed to make MovieCard'", e);
        }

        MovieCardController controller = loader.getController();
        GridPane movieCard = controller.createMovieCard(movie);
        return  movieCard;


    }

    public void setMovieModel(MovieModel movieModel) {
        this.movieModel = movieModel;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
