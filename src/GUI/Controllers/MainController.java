package GUI.Controllers;

import BE.Movie;
import GUI.Models.CategoryModel;
import GUI.Models.MovieModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * todo write comments for all methods
 */
public class MainController implements Initializable {
    @FXML
    private MenuButton menuBtnCategory, searchMenuBtnCategory;
    @FXML
    private TextField textCategoryName;
    @FXML
    private Slider sliderRating;
    @FXML
    private HBox boxAdvancedSearch;
    @FXML
    private TextField textSearch;
    @FXML
    private Button btnSearch;
    @FXML
    private Label textSceneTitle, labelMinRating;


    @FXML
    private BorderPane borderPane;

    public MovieModel getMovieModel() {
        return movieModel;
    }

    private MovieModel movieModel;
    private MovieListController movieListController;
    private boolean isSimpleSearch = true;
    private CategoryModel categoryModel;

    public MainController(){
        try {
            movieModel = new MovieModel();//sets the movieModel
            categoryModel = new CategoryModel();//sets categoryModel

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnSearch.setDisable(true);
        addSearchListener();

        //Loads all current categories in the category dropdown (both in sidebar & in search bar).
        //Perhaps this should only happen when each of them is clicked to improve load time?
        try {
            initializeCategoryMenu();
        } catch (Exception e) {
            new Exception(e);
        }
    }


    private void initializeCategoryMenu() throws Exception {
        searchMenuBtnCategory.getItems().clear();
        //TODO Add list of categories to the menuBtnCategory, something like this
        for (int i = 0; i < categoryModel.getAllCategories().size() ; i++) {
            CheckMenuItem checkMenuItem = new CheckMenuItem(categoryModel.getAllCategories().get(i).getTitle());
            searchMenuBtnCategory.getItems().add(checkMenuItem);

            MenuItem menuItem = new MenuItem(categoryModel.getAllCategories().get(i).getTitle());
            menuBtnCategory.getItems().add(menuItem);
        }
    }

    public void handleHome(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/HomeView.fxml"));
        Parent root = null;

        try {
            root = loader.load();
        } catch (IOException e) {
            new Exception("Failed to open 'Home'", e);
        }

        MovieListController controller = loader.getController();
        controller.setMovieModel(movieModel);
        borderPane.setCenter(root);

        textSceneTitle.setText("Home");
    }


    public void handlePopular(ActionEvent actionEvent) {
    }

    public void handleFavorites(ActionEvent actionEvent) throws IOException {
    }

    public void handleAllMovies(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/MovieListView.fxml"));
        Parent root = null;

        try {
            root = loader.load();
        } catch (IOException e) {
            new Exception("Failed to open 'open all movies'", e);
        }


        movieListController = loader.getController();
        movieListController.setMovieModel(movieModel);
        borderPane.setCenter(root);

        textSceneTitle.setText("all movies");
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


    public void handleSearch() {
        try {
            if(isSimpleSearch) {
                if (btnSearch.getText().equals("🔍")) {
                    movieModel.search(textSearch.getText());
                    setSearchNodes(false);
                } else {
                    movieModel.search("");
                    setSearchNodes(true);
                }
            } else {
                if (btnSearch.getText().equals("🔍")) {
                    //Stores selected menu items (categories) in a list
                    List<String> selectedCategories = searchMenuBtnCategory.getItems().stream()
                                    .map(CheckMenuItem.class::cast).filter(CheckMenuItem::isSelected)
                                    .map(CheckMenuItem::getText).collect(Collectors.toList());

                    movieModel.searchAdvanced(textSearch.getText(), sliderRating.getValue(), selectedCategories);
                    setSearchNodes(false);
                } else {
                    movieModel.search("");
                    setSearchNodes(true);
                }
            }
            movieListController.createContentGrid();
        } catch (Exception e) {
            new Exception("Failed to search", e);
        }
    }

    /**
     * Adds a listener to the text field for movie searching.
     */
    private void addSearchListener() {
        textSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if(!textSearch.getText().isEmpty()) {
                setSearchNodes(false);
                btnSearch.setText("🔍"); //If query is changed again switch from clear search back to search symbol
            } else {
                setSearchNodes(true);
            }
        });

        sliderRating.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setSearchNodes(false);
                btnSearch.setText("🔍");
                double minRating = sliderRating.getValue();
                DecimalFormat df = new DecimalFormat("0.00");
                labelMinRating.setText("Min. Rating: " + df.format(minRating));
            }
        });
        //TODO Add better Listener for changes in selected categories
        searchMenuBtnCategory.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setSearchNodes(false);
                btnSearch.setText("🔍");
            }
        });
    }

    /**
     * Allows for enabling/disabling the button for searching in the movies list
     * and resetting the min. rating & selected categories
     */
    private void setSearchNodes(boolean disable) {
        btnSearch.setDisable(disable);
        if (!disable) {
            btnSearch.setText("✖");
            addSearchListener();
        } else {
            btnSearch.setText("🔍");
            textSearch.setText("");
            sliderRating.setValue(0);
            labelMinRating.setText("Min. Rating");
            //initializeCategoryMenu();
        }
    }

    /**
     * Switch between simple and advanced search
     */
    public void handleSearchSettings() {
        if (isSimpleSearch) {
            boxAdvancedSearch.setOpacity(1);
            isSimpleSearch = false;
        } else {
            boxAdvancedSearch.setOpacity(0);
            isSimpleSearch = true;
        }
    }

    /**
     * Allows searching by pressing Enter (instead of using the 🔍-button).
     * @param keyEvent, a key-press
     */
    public void handleEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            handleSearch();
        }
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
    }

    public void openMovieInfo(Movie movie){
        FXMLLoader loader = new FXMLLoader(MainController.class.getResource("/GUI/Views/MovieView.fxml"));
        Parent root = null;

        try {
            root = loader.load();

        } catch (IOException e) {
            new Exception("Failed to show 'movie info'", e);
        }

        MovieController controller = loader.getController();

        //borderPane.setCenter(root);
        controller.setMovieModel(movieModel);
        controller.setMovie(movie);


    }
    public BorderPane getBorderPane(){
        return borderPane;
    }

}
