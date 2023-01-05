package GUI.Controllers;

import BE.Movie;
import GUI.Models.MovieModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

public class MainController implements Initializable {

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        testcrud();
    }





    private void testcrud() {
        //test if the movie is sent through the layers
        try {

            //creates a movie
            Timestamp t = new Timestamp(Calendar.getInstance().getTimeInMillis());
            System.out.println(t);
            Movie m = new Movie("fkeo", 5.22, 3.44, "fmek/dd", "fefe/be", "nfejnfe/d", t );
            System.out.println(movieModel.createMovie(m));

            movieModel.createMovie(m);

            //updates movie title
            movieModel.getMoviesInList().get(0).setTitle("nyhehehhehe");
            movieModel.updateMovie(movieModel.getMoviesInList().get(0));

            //get movie from id test
            Movie mff = movieModel.getMoviesInList().get(0);
            System.out.println("your chosen movie from id:  " + mff.getTitle());

            //gets all movies from db
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

    public void handleFavorites(ActionEvent actionEvent) {
    }

    public void handleAddMovie(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/AddMovieView.fxml"));
        GridPane addmovie = FXMLLoader.load(getClass().getResource("/GUI/Views/AddMovieView.fxml"));
        borderPane.setCenter(addmovie);
        textSceneTitle.setText("Add Movie");
    }
}
