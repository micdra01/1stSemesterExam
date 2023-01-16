package GUI.Controllers;

import BE.Category;
import BE.ImdbInfo;
import BE.Movie;
import DAL.ImdbApi;
import GUI.Models.CategoryModel;
import GUI.Models.ImdbInfoModel;
import GUI.Models.MovieModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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



    /**
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
        double imdbRating = imdbInfoModel.getImdbRatingFromApi(chosenMovie.getImdbId())  != null ?  Double.parseDouble(imdbInfoModel.getImdbRatingFromApi(chosenMovie.getImdbId())) : 0.00;
        String movieLink = movieFile != null ? movieFile.getAbsolutePath() : "";
        String coverPath = chosenMovie != null ? chosenMovie.getPictureLink() : movieCover.getAbsolutePath();
        Timestamp lastViewed = new Timestamp(Calendar.getInstance().getTimeInMillis());
        int yearOfRelease = chosenMovie != null ? Integer.parseInt(chosenMovie.getYearOfRelease()) : 0;
        String movieDescription = chosenMovie != null ? imdbInfoModel.getMovieDescriptionFromImdbId(chosenMovie.getImdbId()) :"der er ingen beskrivelse for denne film";

        Movie movie = new Movie(title, personalRating, imdbRating, movieLink, coverPath, lastViewed, yearOfRelease, movieDescription);
        if(chosenMovie != null){
            movie.setImdbId(chosenMovie.getImdbId());

            String topCast = "";
            for(int i = 0; chosenMovie.getCast().size() > i; i++){
                topCast = topCast  + chosenMovie.getCast().get(i)+",";
            }
            movie.setTopCast(topCast);
        }
        movie = movieModel.createMovie(movie); //Create movie in DAO and get the correct ID back

        //Create a list of all movie categories found from IMDB
        ArrayList<String> movieCategories = imdbInfoModel.getMovieCategoriesFromApi(chosenMovie.getImdbId());

        //Loop through all categories, and add the movie.
        //If the category does not exist it will be created through the CategoryModel
        for (int i = 0; i<movieCategories.size(); i++) {
            Category category = categoryModel.createCategoryIfNotExist(movieCategories.get(i));
            categoryModel.addMovieToCategory(category, movie);
        }

        Label savedText = new Label("you did it, you saved the movie in your database ");
        grid.add(savedText,1,8);

        textMovieFile.clear();
        textImageFile.clear();
        textTitle.clear();
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
        }

        ListView categoryList = new ListView<>(categories);
        grid.add(categoryList, 1, 5);
    }
}
