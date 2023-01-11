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

/**
 * todo make a instance variable for movieModel.moviesInList so we can chose the specific list we want shown in our content window (categories, favourite mm)
 */


public class MovieListController implements Initializable {
    @FXML
    private ScrollPane movieListView;
    private MovieModel movieModel;
    private MovieCardController movieCardController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        movieCardController = new MovieCardController();
        //sets the models
        try {
            movieModel = new MovieModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        createContentGrid();
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

            GridPane movieCard = movieCardController.createMovieCard(movie);//creates the movie card
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

    public void setMovieModel(MovieModel movieModel) {
        this.movieModel = movieModel;
    }

}
