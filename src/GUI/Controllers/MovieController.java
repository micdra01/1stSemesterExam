package GUI.Controllers;

import BE.Category;
import BE.Movie;
import GUI.Models.CategoryModel;
import javafx.event.ActionEvent;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //mediaView.setMediaPlayer(new MediaPlayer(new Media("/Movies/mp4 sample.mp4")));

    }

    public void handleAddCategory(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/AddCategoryView.fxml"));
        Parent root = null;

        try {
            root = loader.load();
        } catch (IOException e) {
            new Exception("Failed to open 'Add category'", e);
        }

        Stage stage = new Stage();
        stage.setTitle("Add Category");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

        MovieController controller = loader.getController();
        controller.setCategoryModel(categoryModel);
        System.out.println(categoryModel);

    }

    public void handleSaveCategory(ActionEvent actionEvent) throws Exception {

        int id = -1;
        String title = textCategoryName.getText();
        ArrayList<Movie> movieList = null;

        Category category = new Category(id, title, movieList);

        categoryModel.createCategory(category);
    }

    public void setCategoryModel(CategoryModel categoryModel){
        this.categoryModel = categoryModel;
    }
}
