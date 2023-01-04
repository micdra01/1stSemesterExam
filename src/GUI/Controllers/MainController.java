package GUI.Controllers;

import BLL.MovieManager;
import DAL.Interfaces.IMovieDAO;
import DAL.MovieDAO;
import GUI.Models.MovieModel;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public GridPane grid;

    public AnchorPane contentBoxId;

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
}
