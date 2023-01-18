package GUI.Controllers;

import BE.Category;
import BE.ImdbInfo;
import BE.Movie;
import GUI.Models.CategoryModel;
import GUI.Models.ImdbInfoModel;
import GUI.Models.MovieModel;
import GUI.Util.ErrorDisplayer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
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
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Dictionary;
import java.util.ResourceBundle;

public class AddMovieController implements Initializable {
    public TextField textImageFile, textCategory, textTitle, textMovieFile;
    public Button btnMovieFile, btnImageFile, btnSave;
    public Label lblImageFile, lblCategory, lblMovieFile, lblTitle;
    public GridPane grid;
    private MovieModel movieModel;
    private CategoryModel categoryModel;
    private File movieCover, movieFile;
    private ImdbInfoModel imdbInfoModel;
    private ImdbInfo chosenMovie;
    private ListView<String> searchResultListView;
    private boolean textFillMovie, textFillPicture, textFillTitle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            categoryModel = new CategoryModel();
            imdbInfoModel = new ImdbInfoModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        btnSave.setDisable(true);
        checkForInputListener();
    }

    private void checkForInputListener() {
        btnSave.setDisable(true);
        textTitle.textProperty().addListener((observable, oldValue, newValue) -> {
            if(textTitle.getText().isEmpty()){
                textFillTitle = false;
            }else{
                textFillTitle = true;
                checkSaveBtn();
            }
        });
        textImageFile.textProperty().addListener((observable, oldValue, newValue) -> {
            if(textImageFile.getText().isEmpty()){
                textFillPicture = false;
            }else{
                textFillPicture = true;
                checkSaveBtn();
            }
        });
        textMovieFile.textProperty().addListener((observable, oldValue, newValue) -> {
            if(textMovieFile.getText().isEmpty()){
                textFillMovie = false;
            }else{
                textFillMovie = true;
                checkSaveBtn();
            }
        });
    }

    private void checkSaveBtn() {
        if(textFillMovie && textFillPicture && textFillTitle){
            btnSave.setDisable(false);
        }
    }

    public void handleMovieFile() {
        Stage stage = (Stage) btnMovieFile.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter movieExtensions = new FileChooser.ExtensionFilter("File types", "*.mp4", "*.mpeg4");
        fileChooser.getExtensionFilters().add(movieExtensions);
        movieFile = fileChooser.showOpenDialog(stage);

        if (movieFile != null) {
            textMovieFile.setText(movieFile.getAbsolutePath());
        }
    }

    public void handleImageFile() {
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
     */
    public void handleSave() {
        try {
            String title = chosenMovie != null ? chosenMovie.getTitle() : lblTitle.getText();

            double personalRating = -1;
            double imdbRating = 0;

            imdbRating = chosenMovie != null ? Double.parseDouble(imdbInfoModel.getImdbRatingFromApi(chosenMovie.getImdbId())) : 0.00;
            String movieLink = movieFile != null ? movieFile.getAbsolutePath() : "";
            String coverPath = chosenMovie != null && !chosenMovie.getPictureLink().isEmpty() ? chosenMovie.getPictureLink() : "images/ImageNotFound.jpg";
            Timestamp lastViewed = new Timestamp(Calendar.getInstance().getTimeInMillis());
            int yearOfRelease = chosenMovie != null ? Integer.parseInt(chosenMovie.getYearOfRelease()) : 0;
            String movieDescription = chosenMovie != null ? imdbInfoModel.getMovieDescriptionFromImdbId(chosenMovie.getImdbId()) : "der er ingen beskrivelse for denne film";

            Movie movie = new Movie(title, personalRating, imdbRating, movieLink, coverPath, lastViewed, yearOfRelease, movieDescription);
            //sets the topcast of the  movie
            if (chosenMovie != null && chosenMovie.getCast() != null) {
                movie.setImdbId(chosenMovie.getImdbId());

                StringBuilder topCast = new StringBuilder();
                for (int i = 0; chosenMovie.getCast().size() > i; i++) {
                    topCast.append(chosenMovie.getCast().get(i)).append(",");
                }
                movie.setTopCast(topCast.toString());
            }

            movieModel.addMovieToList(movie);
            movie = movieModel.createMovie(movie); //Create movie in DAO and get the correct ID back
            addCategoriesFromMovie(movie);
            Label savedText = new Label("you did it, you saved the movie in your database ");
            grid.add(savedText, 1, 8);

            clearImputFileds();
        } catch (Exception e) {
            ErrorDisplayer.displayError(new Exception(e));
        }
    }

    private void clearImputFileds() {
        textMovieFile.clear();
        textImageFile.clear();
        textTitle.clear();
    }

    private void addCategoriesFromMovie(Movie movie) {
        try {
            if (chosenMovie != null) {
                //Create a list of all movie categories found from IMDB
                ArrayList<String> movieCategories = imdbInfoModel.getMovieCategoriesFromApi(chosenMovie.getImdbId());
                //Loop through all categories, and add the movie.
                //If the category does not exist it will be created through the CategoryModel
                for (String movieCategory : movieCategories) {
                    Category category = categoryModel.createCategoryIfNotExist(movieCategory);
                    categoryModel.addMovieToCategory(category, movie);
                }
            }
        } catch (Exception e) {
            ErrorDisplayer.displayError(new Exception(e));
        }
    }

    /**
     * serach on imdb when seach button is hit.
     */
    public void handleSearchOnImdb() {
        ArrayList<ImdbInfo> searchResult;

        //creates the imdb model, so we can call api operations.
        try {
            searchResult = imdbInfoModel.getSearchResultFromApi(textTitle.getText());
            createResultList(searchResult);
        } catch (Exception e) {
            ErrorDisplayer.displayError(new Exception("Failed to retrieve search result from IMDB", e));
        }

    }

    private void createResultList(ArrayList<ImdbInfo> searchResult) {
        //gets the results from the search and list them in an observable list, and ads it to the grid
        ObservableList<String> wordsList = FXCollections.observableArrayList();
        for (ImdbInfo info : searchResult) {
            wordsList.add(info.getTitle() + "   " + info.getYearOfRelease());
        }
        searchResultListView = new ListView<>(wordsList);
        searchResultListView.setMaxSize(300, 350);
        grid.add(searchResultListView, 1,2);

        //listener for when a result is selected on the listVIew
        searchResultListView.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    chosenMovieListener(searchResult);
                } catch (Exception e) {
                    ErrorDisplayer.displayError(new Exception(e));
                }
            }
        });
    }

    /**
     * sets the image and categories for the movie and shows the image and list of categories in grid
     */
    private void chosenMovieListener(ArrayList<ImdbInfo> searchResult) {
        //the selected movie from the ImdbResultListView
        chosenMovie = searchResult.get(searchResultListView.getSelectionModel().getSelectedIndex());

        //sets the image from a url string
        Image img = new Image("images/ImageNotFound.jpg");
        if(chosenMovie != null && !chosenMovie.getPictureLink().isEmpty()){
            img = new Image(chosenMovie.getPictureLink());
        }

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
            categories.addAll(categoryResult);
        } catch (Exception e) {
            ErrorDisplayer.displayError(new Exception(e));
        }

        ListView<String> categoryList = new ListView<>(categories);
        grid.add(categoryList, 1, 5);
    }

    public void setMovieModel(MovieModel movieModel) {
        this.movieModel = movieModel;
    }
}
