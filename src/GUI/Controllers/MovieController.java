package GUI.Controllers;

import BE.Movie;
import GUI.Models.MovieModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class MovieController implements Initializable {

    public Label lblTittle;
    @FXML
    private ScrollPane movieView;
    @FXML
    private MediaView mediaView;

    private MovieModel movieModel;

    private Movie movie;


    public void setMovie(Movie movie) {
        this.movie = movie;
        lblTittle.setText(movie.getTitle());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //mediaView.setMediaPlayer(new MediaPlayer(new Media("/Movies/mp4 sample.mp4")));

        System.out.println("ff");
    }



    public void setMovieModel(MovieModel movieModel) {
        this.movieModel = movieModel;
    }
}
