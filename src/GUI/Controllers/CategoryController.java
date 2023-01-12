package GUI.Controllers;

import BE.Category;
import GUI.Models.CategoryModel;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaView;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CategoryController {

    @FXML
    private ImageView imageMoviePoster;
    @FXML
    private Label labelTitle, labelYear, labelIMDBRating, labelPersonalRating, labelLastViewed;
    public Button btnAddCategory;
    public Button btnSaveCategory;

    @FXML
    public Button btbDeleteCategory;
    public ListView listCategories;
    public Button btnSaveCategory;
    public TextField textAddCategory;
    private CategoryModel categoryModel;
    private Category selectedCategory;


    /**
     * Constructor which instantiate CategoryModel
     */
    public CategoryController(){
        try {
            categoryModel = new CategoryModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets CategoryModel
     * @param categoryModel
     */
    public void setCategoryModel(CategoryModel categoryModel){
        this.categoryModel = categoryModel;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
        labelTitle.setText(movie.getTitle());
        labelYear.setText(String.valueOf(movie.getYearOfRelease()));
        labelIMDBRating.setText(String.valueOf(movie.getImdbRating()));
        labelPersonalRating.setText(String.valueOf(movie.getPersonalRating()));
        labelLastViewed.setText(String.valueOf(movie.getLastViewed()));
        imageMoviePoster.setImage(new Image(movie.getPictureFileLink()));
    }

    public void handleSaveCategory(ActionEvent actionEvent) throws Exception {
    /**
     * Creates category if button is clicked by calling CategoryModels createCategory method.
     * Populates the category list and clears text field.
     * @param actionEvent
     * @throws Exception
     */
    public void handleSaveCategory(ActionEvent actionEvent) throws Exception{

        int id = -1;
        String title = textAddCategory.getText();

        Category category = new Category(id, title);

        categoryModel.createCategory(category);
        listCategories.getItems().clear();
        populateCategories();
        textAddCategory.clear();

    }

    public void setCategoryModel(CategoryModel categoryModel){
        this.categoryModel = categoryModel;
    }

    /**
     * Delete selected category if button is clicked by calling CategoryModels deleteCategory method.
     * @param event
     */
    public void handleDeleteCategory(ActionEvent event) {
        try {
            selectedCategory = (Category) listCategories.getSelectionModel().getSelectedItem();
            categoryModel.deleteCategory(selectedCategory);
            listCategories.getItems().clear();
            populateCategories();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loops through list of all categories and adds category objects to listview.
     */
    public void populateCategories(){
        try {
            for (Category category : categoryModel.getAllCategories()) {
                listCategories.getItems().add(category);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void handlePlayMovie(ActionEvent actionEvent) {
        try {
            String moviePath = new File(movie.getMovieFileLink()).getAbsolutePath();
            Desktop.getDesktop().open(new File(moviePath));
        } catch (IOException e) {
            new Exception("Failed to play movie"+e);
        }
    }
}
