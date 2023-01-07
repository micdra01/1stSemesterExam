package GUI.Controllers;

import BE.Movie;
import GUI.Models.MovieModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * todo write comments for all methods
 */
public class MainController implements Initializable {
    @FXML
    private TextField textSearch;
    @FXML
    private Button btnSearch;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnSearch.setDisable(true);
        addSearchListener();
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

        borderPane.setCenter(root);

    }

    /**
     * Adds a listener to the text field for movie searching.
     */
    private void addSearchListener() {
        textSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if(!textSearch.getText().isEmpty()) {
                btnSearch.setDisable(false);
            } else {
                btnSearch.setDisable(true);
            }
        });
    }

    public void handleSearch(ActionEvent actionEvent) {
        if (btnSearch.getText().equals("🔍")) {
            btnSearch.setText("✖");
            //TODO: Search functionality here
            //movieModel.search(textSearch.getText());
        } else {
            btnSearch.setText("🔍");
            textSearch.setText("");
            //TODO: Clear search functionality here
            //movieModel.search("");
        }
        //todo: Add search functionality

    }
}
