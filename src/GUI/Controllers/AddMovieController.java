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
    private ListView<String> searchResultListView;

    /**
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

    /**
     * creates a movie from either the inputfields if they are selected or the imdb info if it is created
     *
     * @param event
     * @throws Exception
     */
    public void handleSave(ActionEvent event) throws Exception {

        String title = "";
        if(chosenMovie != null) {
            title = chosenMovie.getTitle();
        }else {
            title = textTitle.getText();
        }

        double personalRating = -1;
        double imdbRating =  chosenMovie != null ?  Double.parseDouble(imdbInfoModel.getImdbRatingFromApi(chosenMovie.getImdbId())) : 0;
        String movieLink = movieFile != null ? movieFile.getAbsolutePath() : "";
        String coverPath = chosenMovie != null ? chosenMovie.getPictureLink() : movieCover.getAbsolutePath();
        Timestamp lastViewed = new Timestamp(Calendar.getInstance().getTimeInMillis());
        int yearOfRelease = chosenMovie != null ? Integer.parseInt(chosenMovie.getYearOfRelease()) : 0;
        String movieDescription = chosenMovie != null ? imdbInfoModel.getMovieDescriptionFromImdbId(chosenMovie.getImdbId()) :"der er ingen beskrivelse for denne film";

        Movie movie = new Movie(title, personalRating, imdbRating, movieLink, coverPath, lastViewed, yearOfRelease, movieDescription);
        if(chosenMovie != null){
            movie.setImdbId(chosenMovie.getImdbId());
        }
        movieModel.createMovie(movie);

        //todo should set the categories to the movie in database
        //todo should have an get category by name crud method
    }

    public void setMovieModel(MovieModel movieModel) {
        this.movieModel = movieModel;
    }

    /**
     * serach on imdb when seach button is hit.
     * @param actionEvent
     */
    public void handleSearchOnImdb(ActionEvent actionEvent) {
        ArrayList<ImdbInfo> searchResult;

        //creates the imdb model, so we can call api operations.
        try {
            imdbInfoModel = new ImdbInfoModel();
            searchResult = imdbInfoModel.getSearchResultFromApi(textTitle.getText());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //gets the results from the search and list them in an observable list, and ads it to the grid
        ObservableList<String> wordsList = FXCollections.observableArrayList();
        for (int i = 0; searchResult.size()> i; i++){
            ImdbInfo info = searchResult.get(i);
            wordsList.add(info.getTitle() + "   "+info.getYearOfRelease());
        }
        searchResultListView = new ListView<>(wordsList);
        searchResultListView.setMaxSize(300, 350);
        grid.add(searchResultListView, 1,2);


        //listener for when a result is selected on the listVIew
        searchResultListView.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                chosenMovieListener(searchResult);
            }
        });
    }

    /**
     * sets the image and categories for the movie and shows the image and list of categories in grid
     * @param searchResult
     */
    private void chosenMovieListener(ArrayList<ImdbInfo> searchResult) {
        //the selected movie from the ImdbResultListView
        chosenMovie = searchResult.get(searchResultListView.getSelectionModel().getSelectedIndex());

        //sets the image from a url string
        Image img = new Image(chosenMovie.getPictureLink());
        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(200);
        imageView.setFitHeight(275);
        grid.add(imageView, 2,2);
        textImageFile.setText(img.getUrl());

        //gets the results from the category search on the movie, and ads it to the grid
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
        grid.add(categoryList, 1, 5);


    }

}
