package GUI.Controllers;

import BE.Movie;
import GUI.Models.MovieModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.ResourceBundle;

public class MainController {

    @FXML
    private Label textSceneTitle;
    @FXML
    private BorderPane borderPane;

    public MovieModel getMovieModel() {
        return movieModel;
    }

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

    public void handleTrending(ActionEvent actionEvent) throws IOException {
        ScrollPane trending = FXMLLoader.load(getClass().getResource("/GUI/Views/TrendingView.fxml"));
        borderPane.setCenter(trending);
        textSceneTitle.setText("Trending");
    }


    public void handlePopular(ActionEvent actionEvent) {
    }

    public void handleFavorites(ActionEvent actionEvent) throws IOException {
        //TODO this is just testing, to be removed!!
        ScrollPane movieView = FXMLLoader.load(getClass().getResource("/GUI/Views/MovieView.fxml"));
        borderPane.setCenter(movieView);
        textSceneTitle.setText("Information");
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
}
