package GUI.Controllers;

import BE.Category;
import BE.Movie;
import GUI.Models.CategoryModel;
import GUI.Models.MovieModel;
import GUI.Util.ConfirmDelete;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MovieViewController implements Initializable {


    public Slider sliderSetPR;
    public Label labelDescription;
    public Label labelCast;
    @FXML
    private VBox vBoxCategories;
    @FXML
    private MenuButton menuBtnAddCategory, btnSetPR;

    @FXML
    private Label labelTitle, labelYear, labelIMDBRating, labelPersonalRating, labelLastViewed;
    @FXML
    private ImageView imageMoviePoster;
    private CategoryModel categoryModel;
    private MovieModel movieModel;
    private Movie movie;
    ArrayList<Category> movieCategories;

    public MovieViewController() {
        try {
            categoryModel = new CategoryModel();
            movieModel = new MovieModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeCategoryDropdown();
    }

    /**
     * Creates a removable category tag for each category from the movie
     * @param category, the category that needs a tag
     */
    private void createCategoryTag(Category category) {
        //Creates label & button showing the category
        Label categoryName = new Label(category.toString());
        categoryName.setPadding(new Insets(5));
        Button btnRemove = new Button("x");

        //Creates a container for the label & button and adds this to the correct container in the view
        HBox container = new HBox(categoryName, btnRemove);
        vBoxCategories.getChildren().add(container);

        //Adds listener to the remove button to be able to remove category from movie
        btnRemove.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    //Removes category from movie, then removes label
                    categoryModel.removeCategoryFromMovie(category, movie);
                    vBoxCategories.getChildren().remove(container);
                } catch (Exception e) {
                    new Exception("Failed to remove category from movie", e);
                }
            }
        });
    }

    /**
     * Shows all the categories from the movie
     */
    private void showCategories() {
        try {
            movieCategories = categoryModel.readAllCategoriesFromMovie(movie);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        for (Category category : movieCategories) {
            createCategoryTag(category);
        }
    }

    /**
     * Loads all available categories into the 'Add Category' dropdown
     * and creates a label & remove button for each category added to the movie
     */
    private void initializeCategoryDropdown() {
        try {
            //Loads all available categories in as menuitems in the dropdown
            for (int i = 0; i < categoryModel.getAllCategories().size() ; i++) {
                MenuItem menuItem = new MenuItem(categoryModel.getAllCategories().get(i).toString());
                menuBtnAddCategory.getItems().add(menuItem);

                //Adds listener to check if category is selected
                Category category = categoryModel.getAllCategories().get(i);
                menuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            //Adds movie to the selected category
                            categoryModel.addMovieToCategory(category, movie);
                            createCategoryTag(category);
                        } catch (Exception e) {
                            new Exception("Failed to add movie to category", e);
                        }
                    }
                });
            }

        } catch (Exception e) {
            new Exception("Failed to get all categories", e);
        }
    }

    public void setMovieModel(MovieModel movieModel) {
        this.movieModel = movieModel;
    }

    public void setMovieContent(Movie movie) {
        this.movie = movie;
        showCategories();
        labelTitle.setText(movie.getTitle());
        labelYear.setText(String.valueOf(movie.getYearOfRelease()));
        labelIMDBRating.setText(String.valueOf(movie.getImdbRating()));
        labelPersonalRating.setText(String.valueOf(movie.getPersonalRating()));
        labelLastViewed.setText(String.valueOf(movie.getLastViewed()));
        imageMoviePoster.setImage(new Image(movie.getPictureFileLink()));
        labelDescription.setText(movie.getMovieDescription());
        //TODO check if null. if null set "", else run below line
        labelCast.setText(movie.getTopCast().replaceAll(",", "\n"));
    }

    public void handlePlayMovie(ActionEvent actionEvent) {
        try {
            String moviePath = new File(movie.getMovieFileLink()).getAbsolutePath();
            Desktop.getDesktop().open(new File(moviePath));

            movie.setLastViewed(new Timestamp(System.currentTimeMillis()));
            movieModel.updateMovie(movie);
            labelLastViewed.setText(String.valueOf(movie.getLastViewed()));
        } catch (IOException e) {
            new Exception("Failed to play movie"+e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void handleSetPR(ActionEvent actionEvent) throws Exception {

        double pr = sliderSetPR.getValue();
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        System.out.println(df.format(pr));
        movie.setPersonalRating(pr);
        movieModel.updateMovie(movie);

        labelPersonalRating.setText(String.valueOf(movie.getPersonalRating()));
    }


    public void deleteMovie(ActionEvent actionEvent) {
        try {
            //Check to make sure user meant to delete
            String header = "Are you sure you want to delete this movie?";
            String content = movie.getTitle();
            boolean deleteMovie = ConfirmDelete.confirm(header, content);

            if(deleteMovie) {
                movieModel.deleteMovie(movie);
                Stage stage = (Stage) btnSetPR.getScene().getWindow();
                stage.close();
                //TODO re-load previous stage, so removed movie is gone
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


