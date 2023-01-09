package GUI.Controllers;

import BE.Category;
import BE.Movie;
import GUI.Models.CategoryModel;
import GUI.Models.MovieModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaView;

import java.net.URL;
import java.util.ResourceBundle;

public class MovieController implements Initializable {

    public Label lblTittle;
    public Button btnAddCategory;
    public Button btnSaveCategory;

    @FXML
    public ListView listCategories;
    public TextField textAddCategory;
    public Button btbDeleteCategory;
    @FXML
    private ScrollPane movieView;
    @FXML
    private MediaView mediaView;
    private CategoryModel categoryModel;

    public AnchorPane anchorPane;

    private MovieModel movieModel;

    private Movie movie;


    private Category selectedCategory;



    public MovieController(){

        try {
            categoryModel = new CategoryModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //mediaView.setMediaPlayer(new MediaPlayer(new Media("/Movies/mp4 sample.mp4")));
       // populateCategories();
    }

    public void setMovieModel(MovieModel movieModel) {
        this.movieModel = movieModel;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
        lblTittle.setText(movie.getTitle());
    }

    public void handleSaveCategory(ActionEvent actionEvent) throws Exception {

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

    public void populateCategories(){
        try {
            for (Category category : categoryModel.getAllCategories()) {
                listCategories.getItems().add(category);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}


