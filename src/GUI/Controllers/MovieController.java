package GUI.Controllers;

import BE.Category;
import BE.Movie;
import GUI.Models.CategoryModel;
import GUI.Models.MovieModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MovieController implements Initializable {
    @FXML
    private VBox vBoxCategories;
    @FXML
    private MenuButton menuBtnAddCategory;

    public Label lblTittle;
    private CategoryModel categoryModel;
    private MovieModel movieModel;
    private Movie movie;
    ArrayList<Category> movieCategories;

    public MovieController() {
        try {
            categoryModel = new CategoryModel();
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

    public void setMovie(Movie movie) {
        this.movie = movie;
        lblTittle.setText(movie.getTitle());
        showCategories();
    }

}


