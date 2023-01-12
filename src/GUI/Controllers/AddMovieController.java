package GUI.Controllers;

import BE.ImdbInfo;
import BE.Movie;
import DAL.ImdbApi;
import GUI.Models.ImdbInfoModel;
import GUI.Models.MovieModel;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

public class AddMovieController{
    public TextField textImageFile, textTrailerFile, textIMDBRating, textCategory, textTitle, textMovieFile;
    public Button btnMovieFile, btnTrailerFile, btnImageFile, btnSave;
    public Label lblImageFile, lblTrailerFile, lblIMDBRating, lblCategory, lblMovieFile, lblTitle;

    private MovieModel movieModel;

    private File movieCover, movieFile, trailerFile;

    private ImdbInfoModel imdbInfoModel;


    /**
     * todo write comments for all methods in class
     * todo save name of files from each file chooser as local variable in handle save method, so we can make file links later
     * todo save the movie files and picture files from file chooser in resources folder.
     * todo check if all input fields are filled, before save button is activated.
     * @param event
     */
    public void handleMovieFile(ActionEvent event) {
        Stage stage = (Stage) btnMovieFile.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter movieExtensions = new FileChooser.ExtensionFilter("File types", "*.mp4", "*.mpeg4");
        fileChooser.getExtensionFilters().add(movieExtensions);
        movieFile = fileChooser.showOpenDialog(stage);

        if (movieFile != null) {
            textMovieFile.setText(movieFile.getAbsolutePath());
        }
    }

    public void handleTrailerFile(ActionEvent event) {
        Stage stage = (Stage) btnTrailerFile.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter trailerExtensions = new FileChooser.ExtensionFilter("File types", "*.mp4", "*.mpeg4");
        fileChooser.getExtensionFilters().add(trailerExtensions);
        trailerFile = fileChooser.showOpenDialog(stage);

        if (trailerFile != null) {
            textTrailerFile.setText(trailerFile.getAbsolutePath());
        }
    }

    public void handleImageFile(ActionEvent event) {
        Stage stage = (Stage) btnImageFile.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter imageExtensions = new FileChooser.ExtensionFilter("File types", "*.jpg", "*.jpeg", "*.png");
        fileChooser.getExtensionFilters().add(imageExtensions);
        movieCover = fileChooser.showOpenDialog(stage);

        if (movieCover != null) {
            textImageFile.setText(movieCover.getAbsolutePath());
        }
    }

    public void handleSave(ActionEvent event) throws Exception {

        String title = textTitle.getText();
        double personalRating = -1;
        double imdbRating = Double.parseDouble(textIMDBRating.getText());
        //todo next 3 variables should take the name of the file and send down so it can make the file link in dal
        String movieLink = movieFile != null ? movieFile.getAbsolutePath() : "";
        String coverPath = movieCover != null ? movieCover.getAbsolutePath() : "";//gets the absolute path for the file
        Timestamp lastViewed = new Timestamp(Calendar.getInstance().getTimeInMillis());
        int yearOfRelease = 1950;//todo skal hentes fra imdb api
        String movieDescription = "film beskrivelse hvor der skal stå en masse";

        Movie movie = new Movie(title, personalRating, imdbRating, movieLink, coverPath, lastViewed, yearOfRelease, movieDescription);

        movieModel.createMovie(movie);

    }

    public void setMovieModel(MovieModel movieModel) {
        this.movieModel = movieModel;
    }

    public void handleSearchOnImdb(ActionEvent actionEvent) {


        //todo just a test for checking all methods works from imdb crud
        ArrayList<String> categories;
        String rating ="";
        String description = "";
        ArrayList<ImdbInfo> searchResult;
        try {
            imdbInfoModel = new ImdbInfoModel();
            searchResult = imdbInfoModel.getSearchResultFromApi(textTitle.getText());
            rating = imdbInfoModel.getImdbRatingFromApi("tt0050377");
            categories = imdbInfoModel.getMovieCategoriesFromApi("tt0050377");
            description = imdbInfoModel.getMovieDescriptionFromImdbId("tt0051603");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        for (int i = 0; categories.size() > i; i++){
            System.out.println(categories.get(i));
        }
        System.out.println(rating);
        System.out.println(description);

        System.out.println("  ");
        for (int i = 0; searchResult.size() > i; i++){
            System.out.println(searchResult.get(i).getTitle());
            System.out.println(searchResult.get(i).getImdbId());
            System.out.println(searchResult.get(i).getYearOfRelease());
            System.out.println(searchResult.get(i).getPictureLink());

            for (int j = 0; searchResult.get(i).getCast().size() > j; j++){
                System.out.println(searchResult.get(j).getCast().get(j));
            }
        }

    }
}
