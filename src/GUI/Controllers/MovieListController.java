package GUI.Controllers;

import BE.Category;
import BE.Movie;
import GUI.Models.CategoryModel;
import GUI.Models.MovieModel;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import GUI.Util.ErrorDisplayer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;

public class MovieListController implements Initializable {
    @FXML
    private ScrollPane movieListView;
    private MovieModel movieModel;
    private CategoryModel categoryModel;
    private MovieCardController movieCardController;
    private FlowPane pane = new FlowPane();
    private ObservableList<Movie> allMovies;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        movieCardController = new MovieCardController();
        //sets the models
        try {
            categoryModel = new CategoryModel();

            //Binds the width of the flow pane to the size of the BorderPane Center
            pane.prefWidthProperty().bind(movieListView.widthProperty());
            pane.prefHeightProperty().bind(movieListView.heightProperty());
            movieListView.setContent(pane);
        } catch (Exception e) {
            ErrorDisplayer.displayError(new Exception(e));
        }
    }

    /**
     * Creates the gridPane for movieContent
     * fills it with all movies
     */
    public void showAllMovies() {
        pane.getChildren().clear();
        //loop for creating each movieCard and setting movie info
        for (Movie movie : allMovies) {
            //... it creates a movieCard for said movie and adds it to the Flow Pane
            GridPane movieCard = movieCardController.createMovieCard(movie, movieModel);
            pane.getChildren().add(movieCard);
        }
    }

    /**
     * Creates a gridPane for all popular movies
     * @minRatingPopular is set to 7.5 as final instance variable
     */
    public void showPopularMovies() {
        //loop for creating each movieCard and setting movie info
        for (Movie movie : allMovies) {
            //If the movie's IMDB Rating is greater than or equal to minRatingPopular variable ...
            double minRatingPopular = 7.5;
            if (movie.getImdbRating() >= minRatingPopular) {
                //... it creates a movieCard for said movie and adds it to the Flow Pane
                GridPane movieCard = movieCardController.createMovieCard(movie, movieModel);
                pane.getChildren().add(movieCard);
            }
        }
    }

    /**
     * Creates a gridPane for all popular movies
     * @minRatingFavorite is set to 7.5 as final instance variable
     */
    public void showFavoriteMovies() {
        //loop for creating each movieCard and setting movie info
        for (Movie movie : allMovies) {
            //If the movie's IMDB Rating is greater than or equal to minRatingPopular variable ...
            double minRatingFavorite = 7.5;
            if (movie.getPersonalRating() >= minRatingFavorite) {
                //... it creates a movieCard for said movie and adds it to the Flow Pane
                GridPane movieCard = movieCardController.createMovieCard(movie, movieModel);
                pane.getChildren().add(movieCard);
            }
        }
    }

    /**
     * Creates a gridPane for all movies in the selected Category
     * @param category, the selected category from sidebar dropdown
     */
    public void showMoviesInCategory(Category category) throws Exception {
        if(categoryModel.readAllMoviesInCategory(category) != null) {
            //loop for creating each movieCard and setting movie info
            for (Movie movie : categoryModel.readAllMoviesInCategory(category)) {
                //... it creates a movieCard for said movie and adds it to the Flow Pane
                GridPane movieCard = movieCardController.createMovieCard(movie, movieModel);
                pane.getChildren().add(movieCard);
            }
        }
    }

    public void sortTitle(Comparator<Movie> com) {
        GridPane grid = new GridPane();
        movieListView.setContent(grid);

        //used for placing
        int col = 0;
        int row = 0;

        Comparator<Movie> movieComparator = com;
        ObservableList<Movie> getMoviesInList = null;
        try {
            getMoviesInList = movieModel.getMoviesInList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Collections.sort(getMoviesInList, movieComparator);
        SortedList<Movie> sortedMovie = new SortedList<>(getMoviesInList, movieComparator);


        for(Movie movie : sortedMovie) {
            GridPane movieCard = movieCardController.createMovieCard(movie, movieModel);
            grid.add(movieCard, col, row);

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
        try {
            allMovies = movieModel.getMoviesInList();
        } catch (Exception e) {
            ErrorDisplayer.displayError(new Exception(e));
        }
    }
}
