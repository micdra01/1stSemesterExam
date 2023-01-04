package GUI.Controllers;

import BE.Movie;
import GUI.Models.MovieModel;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Timestamp;
import java.util.Calendar;
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

            Timestamp t = new Timestamp(Calendar.getInstance().getTimeInMillis());
            Movie m = new Movie("fkeo", 5.22, 3.44, "fmek/dd", "fefe/be", "nfejnfe/d", t );
            System.out.println(movieModel.createMovie(m));
            movieModel.getMoviesInList().get(0).setTitle("nyhehehhehe");
            movieModel.updateMovie(movieModel.getMoviesInList().get(0));
            
            for (int i = 0; movieModel.getMoviesInList().size() > i; i++){
                System.out.println("title:  " + movieModel.getMoviesInList().get(i).getTitle() +
                        "   personalRate:  " + movieModel.getMoviesInList().get(i).getPersonalRating() +
                        "  id:  " + movieModel.getMoviesInList().get(i).getId());

                movieModel.deleteMovie(movieModel.getMoviesInList().get(2));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }




    }
}
