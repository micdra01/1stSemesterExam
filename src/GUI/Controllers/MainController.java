package GUI.Controllers;

import BLL.MovieManager;
import DAL.Interfaces.IMovieDAO;
import DAL.MovieDAO;
import GUI.Models.MovieModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

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
        //test if the movie is sent through the layers
        try {
            System.out.println(movieModel.getMoviesInList());

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
        GridPane grid = new GridPane();
        trending.setContent(grid);
        Image img = new Image("/Images/play.PNG");
        ImageView imgV1 = new ImageView(img);
        imgV1.setSmooth(true);
        imgV1.setPreserveRatio(true);
        imgV1.setFitWidth(200);
        ImageView imgV2 = new ImageView(img);
        grid.add(imgV1, 0, 0);
        grid.add(new Label(new String("Uhh?")), 0, 1);
        grid.add(new Separator(), 1,0);
        grid.add(imgV2, 2, 0);
        grid.add(new Label(new String("Ahhhh!")), 2, 1);
    }

    public void handlePopular(ActionEvent actionEvent) {
    }

    public void handleFavorites(ActionEvent actionEvent) {
    }

    public void handleAddMovie(ActionEvent actionEvent) {
    }
}
