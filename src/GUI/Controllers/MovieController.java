package GUI.Controllers;

import GUI.Models.MovieModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.net.URL;
import java.util.ResourceBundle;

public class MovieController implements Initializable {
    @FXML
    private ScrollPane movieView;
    @FXML
    private MediaView mediaView;

    private MovieModel movieModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //mediaView.setMediaPlayer(new MediaPlayer(new Media("/Movies/mp4 sample.mp4")));

    }


    public void setMovieModel(MovieModel movieModel) {
        this.movieModel = movieModel;
    }
}
