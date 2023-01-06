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

import java.io.IOException;

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

    public void openMovieInfo(Movie movie){
        System.out.println(movie.getTitle());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/MovieView.fxml"));
        Parent root = null;

        try {
            root = loader.load();
        } catch (IOException e) {
            new Exception("Failed to show movie info'", e);
        }

        MovieController controller = loader.getController();
        controller.setMovieModel(movieModel);

        borderPane.setCenter(root);

    }



    public void handleHome(ActionEvent actionEvent) throws IOException {
        VBox home = FXMLLoader.load(getClass().getResource("/GUI/Views/HomeView.fxml"));
        borderPane.setCenter(home);
        textSceneTitle.setText("Home");
    }

    public void handleTrending(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/MovieListView.fxml"));
        Parent root = null;

        try {
            root = loader.load();
        } catch (IOException e) {
            new Exception("Failed to open 'trending movies'", e);
        }

        MovieListController controller = loader.getController();
        controller.setMovieModel(movieModel);
        borderPane.setCenter(root);


    }


    public void handlePopular(ActionEvent actionEvent) {
    }

    public void handleFavorites(ActionEvent actionEvent) throws IOException {
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
        borderPane.setCenter(root);

        textSceneTitle.setText("all movies");
    }
}
