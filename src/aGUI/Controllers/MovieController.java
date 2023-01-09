package aGUI.Controllers;

import BE.Category;
import aGUI.Models.CategoryModel;
import javafx.event.ActionEvent;
import aGUI.Models.MovieModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaView;

import java.net.URL;
import java.util.ResourceBundle;

public class MovieController implements Initializable {
    public Button btnAddCategory;
    public Button btnSaveCategory;
    public TextField textCategoryName;
    @FXML
    public ListView listCategories;
    @FXML
    private ScrollPane movieView;
    @FXML
    private MediaView mediaView;
    private CategoryModel categoryModel;

    public AnchorPane anchorPane;

    private MovieModel movieModel;


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
        populateCategories();
    }

    public void populateCategories(){

        try {
            for (Category category : categoryModel.getAllCategories()) {

                listCategories.getItems().add(category.getTitle());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void setMovieModel(MovieModel movieModel) {
        this.movieModel = movieModel;
    }

    public void handleSaveCategory(ActionEvent actionEvent) throws Exception {

        int id = -1;
        String title = textCategoryName.getText();

        Category category = new Category(id, title, null);

        categoryModel.createCategory(category);
        listCategories.getItems().clear();
        populateCategories();

    }

    public void setCategoryModel(CategoryModel categoryModel){
        this.categoryModel = categoryModel;
    }
}
