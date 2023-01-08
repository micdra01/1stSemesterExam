package GUI.Controllers;

import BE.Category;
import BE.Movie;
import GUI.Models.CategoryModel;
import GUI.Models.MovieModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class MainController {

    @FXML
    private Label textSceneTitle;
    public TextField textCategoryName;
    @FXML
    private BorderPane borderPane;

    public MovieModel getMovieModel() {
        return movieModel;
    }

    private MovieModel movieModel;
    private CategoryModel categoryModel;


    public MainController(){
        try {
            movieModel = new MovieModel();//sets the movieModel
            categoryModel = new CategoryModel();//sets categoryModel

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }








    public void handleHome(ActionEvent actionEvent) throws IOException {
        VBox home = FXMLLoader.load(getClass().getResource("/GUI/Views/HomeView.fxml"));
        borderPane.setCenter(home);
        textSceneTitle.setText("Home");
    }

    public void handleTrending(ActionEvent actionEvent) throws IOException {
        ScrollPane trending = FXMLLoader.load(getClass().getResource("/GUI/Views/TrendingView.fxml"));
        borderPane.setCenter(trending);
        textSceneTitle.setText("Trending");
    }


    public void handlePopular(ActionEvent actionEvent) {
    }

    public void handleFavorites(ActionEvent actionEvent) throws IOException {
        //TODO this is just testing, to be removed!!
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/MovieView.fxml"));
        Parent root = null;

        try {
            root = loader.load();
        } catch (IOException e) {
            new Exception("Failed to open 'Add movie'", e);
        }

        AddMovieController controller = loader.getController();
        controller.setMovieModel(movieModel);
        borderPane.setCenter(root);

        textSceneTitle.setText("Add Movie");
    }

    public void handleAddMovie(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/AddMovieView.fxml"));
        Parent root = null;

        try {
            root = loader.load();
        } catch (IOException e) {
            new Exception("Failed to open 'Add movie'", e);
        }

        AddMovieController controller = loader.getController();
        controller.setMovieModel(movieModel);
        borderPane.setCenter(root);

        textSceneTitle.setText("Add Movie");
    }

    public void handleAddCategory(ActionEvent event) {
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
