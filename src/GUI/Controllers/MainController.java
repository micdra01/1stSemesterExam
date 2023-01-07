package GUI.Controllers;

import BE.Movie;
import GUI.Models.MovieModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * todo write comments for all methods
 */
public class MainController {
    @FXML
    private Label textSceneTitle;
    @FXML
    private BorderPane borderPane;

    private MovieModel movieModel;

    public MainController(){
        try {
            movieModel = new MovieModel();//sets the movieModel
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void handleHome(ActionEvent actionEvent) throws IOException {
        VBox home = FXMLLoader.load(getClass().getResource("/GUI/Views/HomeView.fxml"));
        borderPane.setCenter(home);
        textSceneTitle.setText("Home");
    }


    public void handlePopular(ActionEvent actionEvent) {
    }

    public void handleFavorites(ActionEvent actionEvent) throws IOException {
    }

    public void handleAllMovies(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/MovieListView.fxml"));
        Parent root = null;

        try {
            root = loader.load();
        } catch (IOException e) {
            new Exception("Failed to open 'open all movies'", e);
        }

        MovieListController controller = loader.getController();
        controller.setMovieModel(movieModel);
        controller.setMainController(this);
        borderPane.setCenter(root);

        textSceneTitle.setText("all movies");
    }

    public void handleAddMovie(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/AddMovieView.fxml"));
        Parent root = null;

        try {
            root = loader.load();
        } catch (IOException e) {
            new Exception("Failed to open 'Add movie'", e);
        }

        AddMovieController controller = loader.getController();
        controller.setMovieModel(movieModel);
        borderPane.setCenter(root);


        textSceneTitle.setText("Add Movie");
    }

    public void openMovieInfo(Movie movie){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/MovieView.fxml"));
        Parent root = null;

        try {
            root = loader.load();
        } catch (IOException e) {
            new Exception("Failed to show movie info'", e);
        }

        MovieController controller = loader.getController();
        controller.setMovieModel(movieModel);
        controller.setMovie(movie);
        borderPane.setCenter(root);

    }
}
