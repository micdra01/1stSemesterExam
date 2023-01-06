package GUI.Controllers;

import BE.Movie;
import GUI.Models.MovieModel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.ResourceBundle;

public class AddMovieController{
    public TextField textImageFile, textTrailerFile, textIMDBRating, textCategory, textTitle, textMovieFile;
    public Button btnMovieFile, btnTrailerFile, btnImageFile, btnSave;
    public Label lblImageFile, lblTrailerFile, lblIMDBRating, lblCategory, lblMovieFile, lblTitle;

    private MovieModel movieModel;


    public void handleMovieFile(ActionEvent event) {
        Stage stage = (Stage) btnMovieFile.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter movieExtensions = new FileChooser.ExtensionFilter("File types", "*.mp4", "*.mpeg4");
        fileChooser.getExtensionFilters().add(movieExtensions);
        fileChooser.showOpenDialog(stage);
    }

    public void handleTrailerFile(ActionEvent event) {
        Stage stage = (Stage) btnTrailerFile.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter trailerExtensions = new FileChooser.ExtensionFilter("File types", "*.mp4", "*.mpeg4");
        fileChooser.getExtensionFilters().add(trailerExtensions);
        fileChooser.showOpenDialog(stage);
    }

    public void handleImageFile(ActionEvent event) {
        Stage stage = (Stage) btnImageFile.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter imageExtensions = new FileChooser.ExtensionFilter("File types", "*.jpg", "*.jpeg", "*.png");
        fileChooser.getExtensionFilters().add(imageExtensions);
        fileChooser.showOpenDialog(stage);
    }

    public void handleSave(ActionEvent event) throws Exception {

        String title = textTitle.getText();
        double personalRating = -1;
        double imdbRating = Double.parseDouble(textIMDBRating.getText());
        String movieLink = textMovieFile.getText();
        String pictureLink = textImageFile.getText();
        String trailerLink = textTrailerFile.getText();
        Timestamp lastViewed = new Timestamp(Calendar.getInstance().getTimeInMillis());

        Movie movie = new Movie(title, personalRating, imdbRating, movieLink, pictureLink, trailerLink, lastViewed);
        movieModel.createMovie(movie);

    }

    public void setMovieModel(MovieModel movieModel) {
        this.movieModel = movieModel;
    }

}
