package GUI.Controllers;

import BE.Category;
import BE.Movie;
import GUI.Models.CategoryModel;
import javafx.event.ActionEvent;
import GUI.Models.MovieModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MovieController implements Initializable {
    public Button btnAddCategory;
    public Button btnSaveCategory;
    public TextField textCategoryName;
    @FXML
    private ScrollPane movieView;
    @FXML
    private MediaView mediaView;
    private CategoryModel categoryModel;

    public AnchorPane anchorPane;

    private MovieModel movieModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //mediaView.setMediaPlayer(new MediaPlayer(new Media("/Movies/mp4 sample.mp4")));

    }


    public void setMovieModel(MovieModel movieModel) {
        this.movieModel = movieModel;
    }

    public void handleSaveCategory(ActionEvent actionEvent) throws Exception {

        int id = -1;
        String title = textCategoryName.getText();

        Category category = new Category(id, title, null);

        categoryModel.createCategory(category);
        System.out.println(categoryModel.getAllCategories());
    }

    public void setCategoryModel(CategoryModel categoryModel){
        this.categoryModel = categoryModel;
    }
}
