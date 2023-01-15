package GUI.Controllers;

import BE.ImdbInfo;
import BE.Movie;
import DAL.ImdbApi;
import GUI.Models.ImdbInfoModel;
import GUI.Models.MovieModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

public class AddMovieController{
    public TextField textImageFile, textCategory, textTitle, textMovieFile;
    public Button btnMovieFile, btnImageFile, btnSave;
    public Label lblImageFile, lblCategory, lblMovieFile, lblTitle;
    public GridPane grid;

    private MovieModel movieModel;

    private File movieCover, movieFile;

    private ImdbInfoModel imdbInfoModel;

    private ImdbInfo chosenMovie;

    /**
     * todo write comments for all methods in class
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

        String title = "";
        if(chosenMovie != null) {
            title = chosenMovie.getTitle();
        }else {
            title = textTitle.getText();
        }


        double personalRating = -1;

        double imdbRating;
        if(chosenMovie != null){
            imdbRating = Double.parseDouble(imdbInfoModel.getImdbRatingFromApi(chosenMovie.getImdbId()));
        }else {
            imdbRating = 0;
        }

        String movieLink = movieFile != null ? movieFile.getAbsolutePath() : "";

        String coverPath = "";

        if(chosenMovie.getPictureLink() != null) {
            coverPath = chosenMovie.getPictureLink();
        }else {
            coverPath = movieCover.getAbsolutePath();
        }

        Timestamp lastViewed = new Timestamp(Calendar.getInstance().getTimeInMillis());

        int yearOfRelease;
        if(chosenMovie.getYearOfRelease() != null){
            yearOfRelease = Integer.parseInt(chosenMovie.getYearOfRelease());
        }else {
            yearOfRelease = 0;
        }

        String movieDescription;
        if(chosenMovie != null){
            movieDescription = imdbInfoModel.getMovieDescriptionFromImdbId(chosenMovie.getImdbId());
        }else{
            movieDescription = "der er ingen beskrivelse for denne film";
        }

        Movie movie = new Movie(title, personalRating, imdbRating, movieLink, coverPath, lastViewed, yearOfRelease, movieDescription);
        if(chosenMovie != null){
            movie.setImdbId(chosenMovie.getImdbId());
        }
        movieModel.createMovie(movie);

    }

    public void setMovieModel(MovieModel movieModel) {
        this.movieModel = movieModel;
    }

    public void handleSearchOnImdb(ActionEvent actionEvent) {
        ArrayList<ImdbInfo> searchResult1;

        try {
            imdbInfoModel = new ImdbInfoModel();
            searchResult1 = imdbInfoModel.getSearchResultFromApi(textTitle.getText());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        ObservableList<String> wordsList = FXCollections.observableArrayList();
        for (int i = 0; searchResult1.size()> i; i++){
            ImdbInfo info = searchResult1.get(i);
            wordsList.add(info.getTitle() + "   "+info.getYearOfRelease());
        }
        ListView<String> searchResult = new ListView<>(wordsList);
        searchResult.setMaxSize(300, 350);
        grid.add(searchResult, 1,3);


        searchResult.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //System.out.println(searchResult.getSelectionModel().getSelectedItem());
                chosenMovie = searchResult1.get(searchResult.getSelectionModel().getSelectedIndex());

                Image img = new Image(chosenMovie.getPictureLink());
                ImageView imageView = new ImageView(img);
                imageView.setFitWidth(200);
                imageView.setFitHeight(275);
                grid.add(imageView, 2,3);

                ObservableList<String> categories = FXCollections.observableArrayList();
                ArrayList<String> categoryResult;
                try {
                    categoryResult = imdbInfoModel.getMovieCategoriesFromApi(chosenMovie.getImdbId());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                for (int j = 0; categoryResult.size() > j; j++){
                    categories.add(categoryResult.get(j));
                    //System.out.println(categoryResult.get(j));
                }

                ListView categoryList = new ListView<>(categories);
                grid.add(categoryList, 1, 6);

            }
        });


        /**

        //todo just a test for checking all methods works from imdb crud
        ArrayList<String> categories;
        String rating ="";
        String description = "";
        try {
            searchResult1 = imdbInfoModel.getSearchResultFromApi(textTitle.getText());
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
        for (int i = 0; searchResult1.size() > i; i++){
            System.out.println(searchResult1.get(i).getImdbId());
            System.out.println(searchResult1.get(i).getPictureLink());

            for (int j = 0; searchResult1.get(i).getCast().size() > j; j++){
                System.out.println(searchResult1.get(j).getCast().get(j));
            }
        }

         */
    }

}
