package GUI.Controllers;

import BE.Movie;
import GUI.Models.CategoryModel;
import GUI.Models.MovieModel;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;

public class MovieController implements Initializable {

    public Label lblTittle;
    private CategoryModel categoryModel;
    private MovieModel movieModel;
    private Movie movie;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //mediaView.setMediaPlayer(new MediaPlayer(new Media("/Movies/mp4 sample.mp4")));
        //populateCategories();
    }

    public void setMovieModel(MovieModel movieModel) {
        this.movieModel = movieModel;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
        lblTittle.setText(movie.getTitle());
    }

}


