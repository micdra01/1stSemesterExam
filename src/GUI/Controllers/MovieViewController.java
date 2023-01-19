package GUI.Controllers;

import BE.Category;
import BE.Movie;
import GUI.Models.CategoryModel;
import GUI.Models.MovieModel;
import GUI.Util.ConfirmDelete;
import GUI.Util.ErrorDisplayer;
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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MovieViewController implements Initializable {

    @FXML
    private Slider sliderSetPR;

    @FXML
    private FlowPane flowPaneCategories;

    @FXML
    private MenuButton menuBtnAddCategory, btnSetPR;

    @FXML
    private Label labelTitle, labelYear, labelIMDBRating, labelPersonalRating, labelLastViewed,labelDescription,labelCast;
    @FXML
    private ImageView imageMoviePoster;
    private CategoryModel categoryModel;
    private MovieModel movieModel;
    private MainController mainController;
    private Movie movie;

    public MovieViewController() {
        try {
            categoryModel = new CategoryModel();
            movieModel = new MovieModel();
        } catch (Exception e) {
            ErrorDisplayer.displayError(e);
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
        HBox.setMargin(container, new Insets(0, 5, 5, 0));
        flowPaneCategories.getChildren().add(container);

        //Adds listener to the remove button to be able to remove category from movie
        btnRemove.setOnAction(event -> {
            try {
                //Removes category from movie, then removes label
                categoryModel.removeCategoryFromMovie(category, movie);
                flowPaneCategories.getChildren().remove(container);
            } catch (Exception e) {
                ErrorDisplayer.displayError(e);
            }
        });
    }

    /**
     * Shows all the categories from the movie
     */
    private void showCategories() {
        ArrayList<Category> movieCategories;
        try {
            movieCategories = categoryModel.readAllCategoriesFromMovie(movie);
            for (Category category : movieCategories) {
                createCategoryTag(category);
            }
        } catch (Exception e) {
            ErrorDisplayer.displayError(e);
        }
    }

    /**
     * Loads all available categories into the 'Add Category' dropdown
     * and creates a label & remove button for each category added to the movie
     */
    private void initializeCategoryDropdown() {
        try {
            //Loads all available categories in as menuitems in the dropdown
            for (Category category: categoryModel.getAllCategories()) {
                MenuItem menuItem = new MenuItem(category.getTitle());
                menuBtnAddCategory.getItems().add(menuItem);

                //Adds listener to check if category is selected
                menuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            //Adds movie to the selected category
                            categoryModel.addMovieToCategory(category, movie);
                            createCategoryTag(category);
                        } catch (Exception e) {
                            ErrorDisplayer.displayError(e);
                        }
                    }
                });
            }
        } catch (Exception e) {
            ErrorDisplayer.displayError(e);
        }
    }

    public void setMovieModel(MovieModel movieModel) {
        this.movieModel = movieModel;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
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
        if(movie.getTopCast() != null && movie.getTopCast().contains(",")){
            labelCast.setText(movie.getTopCast().replaceAll(",", "\n"));
        }
    }

    public void handlePlayMovie() {
        String moviePath = new File(movie.getMovieFileLink()).getAbsolutePath();
        try {
            Desktop.getDesktop().open(new File(moviePath));
        } catch (Exception e) {
            ErrorDisplayer.displayError(new Exception("Failed to play movie"));
        }

        movie.setLastViewed(new Timestamp(System.currentTimeMillis()));
        try {
            movieModel.updateMovie(movie);
        } catch (Exception e) {
            ErrorDisplayer.displayError(e);
        }
        labelLastViewed.setText(String.valueOf(movie.getLastViewed()));

    }

    public void handleSetPR() throws Exception {

        double pr = sliderSetPR.getValue();
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        System.out.println(df.format(pr));
        movie.setPersonalRating(pr);
        movieModel.updateMovie(movie);

        labelPersonalRating.setText(String.valueOf(movie.getPersonalRating()));
    }


    public void deleteMovie() {
        try {
            //Check to make sure user meant to delete
            String header = "Are you sure you want to delete this movie?";
            String content = movie.getTitle();
            boolean deleteMovie = ConfirmDelete.confirm(header, content);

            if(deleteMovie) {
                movieModel.removeMovieFromList(movie);
                movieModel.deleteMovie(movie);
                Stage stage = (Stage) btnSetPR.getScene().getWindow();
                stage.close();
               mainController.reloadCurrentView();
            }
        } catch (Exception e) {
            ErrorDisplayer.displayError(e);
        }
    }

    public void handleClose() {
        Stage stage = (Stage) btnSetPR.getScene().getWindow();
        stage.close();
    }
}


