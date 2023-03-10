package GUI.Controllers;

import BE.Category;
import BE.Movie;
import GUI.Models.CategoryModel;
import GUI.Models.MovieModel;
import GUI.Util.ErrorDisplayer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainController implements Initializable {
    @FXML
    private MenuItem menuItmTitleAZ, menuItmTitleZA, menuItmIMDBMinMax, menuItmIMDBMaxMin, menuItmPRMaxMin, menuItmPRMinMax ;
    @FXML
    private ImageView imgViewLogo;
    @FXML
    private MenuButton menuBtnCategory, searchMenuBtnCategory, menuBtnSortBy;
    @FXML
    private Slider sliderMinIMDBRating, sliderMaxIMDBRating, sliderMinPersonalRating, sliderMaxPersonalRating;
    @FXML
    private HBox boxAdvancedSearch;
    @FXML
    private TextField textSearch;
    @FXML
    private Button btnSearch, btnClose;
    @FXML
    private Label textSceneTitle, labelMinIMDBRating, labelMaxIMDBRating, labelMinPersonalRating, labelMaxPersonalRating;
    @FXML
    private BorderPane borderPane;

    private final DecimalFormat df = new DecimalFormat("0.00");

    private MovieModel movieModel;
    private MovieListController movieListController;
    private boolean isSimpleSearch = true;
    private CategoryModel categoryModel;
    private int sizeState = 0;

    public MainController(){
        try {
            movieModel = new MovieModel();//sets the movieModel
            categoryModel = new CategoryModel();//sets categoryModel
        } catch (Exception e) {
            ErrorDisplayer.displayError(new Exception(e));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnSearch.setDisable(true);
        addSearchListener();

        //Loads all current categories in the category dropdown (both in sidebar & in search bar).
        handleWarning();
        initializeCategoryMenu();
        initializeCategorySearchMenu();

        Image logo = new Image("images/Tv-Remote-50-Red.png");
        imgViewLogo.setImage(logo);
    }


    public void initializeCategoryMenu() {
        menuBtnCategory.getItems().clear();
        try {
            for (Category category : categoryModel.getAllCategories()) {
                MenuItem menuItem = new MenuItem(category.getTitle());
                menuBtnCategory.getItems().add(menuItem);

                //Adds a listener to open all movies in selected categories
                menuItem.setOnAction(new EventHandler<>() {
                    @Override
                    public void handle(ActionEvent event) {
                        handleCategory(category);
                    }
                });
            }
        } catch (Exception e) {
            ErrorDisplayer.displayError(new Exception(e));
        }

        setSortByContent();
    }

    public void initializeCategorySearchMenu() {
        searchMenuBtnCategory.getItems().clear();
        try {
            for (Category category : categoryModel.getAllCategories()) {
                CheckMenuItem checkMenuItem = new CheckMenuItem(category.getTitle());
                searchMenuBtnCategory.getItems().add(checkMenuItem);
            }
        } catch (Exception e) {
            ErrorDisplayer.displayError(new Exception(e));
        }
    }

    public void handleHome() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/HomeView.fxml"));
        Parent root = null;

        try {
            root = loader.load();
        } catch (Exception e) {
            ErrorDisplayer.displayError(new Exception("Failed to open 'Home'", e));
        }

        HomeViewController controller = loader.getController();
        controller.setMovieModel(movieModel);
        controller.setMainController(this);
        controller.setContent();
        borderPane.setCenter(root);

        textSceneTitle.setText("Home");
    }

    public void handleWarning() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/WarningView.fxml"));
        Parent root = null;

        try {
            root = loader.load();
        } catch (Exception e) {
            ErrorDisplayer.displayError(new Exception("Failed to open 'Warning'", e));
        }

        WarningViewController controller = loader.getController();
        controller.setMovieModel(movieModel);
        controller.setMainController(this);
        controller.setContent();
        borderPane.setCenter(root);

        textSceneTitle.setText("Warning");
    }


    public void handlePopular() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/MovieListView.fxml"));
        Parent root = null;

        try {
            root = loader.load();
        } catch (Exception e) {
            ErrorDisplayer.displayError(new Exception("Failed to open 'Popular movies'", e));
        }

        movieListController = loader.getController();
        movieListController.setMovieModel(movieModel);
        movieListController.setMainController(this);
        movieListController.showPopularMovies();
        borderPane.setCenter(root);

        textSceneTitle.setText("Popular movies");
    }

    public void handleFavorites() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/MovieListView.fxml"));
        Parent root = null;

        try {
            root = loader.load();
        } catch (Exception e) {
            ErrorDisplayer.displayError(new Exception("Failed to open 'Favorite movies'", e));
        }

        movieListController = loader.getController();
        movieListController.setMovieModel(movieModel);
        movieListController.setMainController(this);
        movieListController.showFavoriteMovies();
        borderPane.setCenter(root);

        textSceneTitle.setText("Favorite movies");
    }

    public void handleAllMovies() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/MovieListView.fxml"));
        Parent root = null;

        try {
            root = loader.load();
        } catch (Exception e) {
            ErrorDisplayer.displayError(new Exception("Failed to open 'All movies'", e));
        }

        movieListController = loader.getController();
        movieListController.setMovieModel(movieModel);
        movieListController.setMainController(this);
        movieListController.showAllMovies();
        borderPane.setCenter(root);

        textSceneTitle.setText("All movies");
    }

    public void handleCategory(Category category) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/MovieListView.fxml"));
        Parent root = null;

        try {
            root = loader.load();
        } catch (IOException e) {
            ErrorDisplayer.displayError(new Exception("Failed to open category view", e));
        }

        movieListController = loader.getController();
        movieListController.setMovieModel(movieModel);
        movieListController.setMainController(this);
        try {
            movieListController.showMoviesInCategory(category);
        } catch (Exception e) {
            ErrorDisplayer.displayError(new Exception("Failed to show movies in category" + category + e));
        }
        borderPane.setCenter(root);

        textSceneTitle.setText(category + " Movies");
    }

    public void handleAddMovie() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/AddMovieView.fxml"));
        Parent root = null;

        try {
            root = loader.load();
        } catch (Exception e) {
            ErrorDisplayer.displayError(new Exception("Failed to open 'Add movie'", e));
        }

        AddMovieController controller = loader.getController();
        controller.setMovieModel(movieModel);
        borderPane.setCenter(root);


        textSceneTitle.setText("Add Movie");
    }


    public void handleSearch() throws Exception {
        handleAllMovies();
        try {
            if(isSimpleSearch) {
                if (btnSearch.getText().equals("????")) {
                    movieModel.setSearchActive(true);
                    movieModel.search(textSearch.getText());
                    setSearchNodes(false);
                    addSearchListener();
                } else {
                    movieModel.setSearchActive(false);
                    movieModel.search("");
                    setSearchNodes(true);
                }
            } else {
                if (btnSearch.getText().equals("????")) {
                    movieModel.setSearchActive(true);
                    //Stores selected menu items (categories) in a list
                    List<String> selectedCategories = searchMenuBtnCategory.getItems().stream()
                                    .map(CheckMenuItem.class::cast).filter(CheckMenuItem::isSelected)
                                    .map(CheckMenuItem::getText).collect(Collectors.toList());

                    movieModel.searchAdvanced(textSearch.getText(), sliderMinIMDBRating.getValue(), sliderMaxIMDBRating.getValue(),
                            sliderMinPersonalRating.getValue(), sliderMaxPersonalRating.getValue(), selectedCategories);
                    setSearchNodes(false);
                    addSearchListener();
                } else {
                    movieModel.setSearchActive(false);
                    movieModel.search("");
                    setSearchNodes(true);
                }
            }
            movieListController.showAllMovies();
        } catch (Exception e) {
            e.printStackTrace();
            ErrorDisplayer.displayError(new Exception("Failed to search", e));
        }
    }

    /**
     * Adds a listener to the text field for movie searching.
     */
    private void addSearchListener() {
        textSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if(!textSearch.getText().isEmpty()) {
                setSearchNodes(false);
                btnSearch.setText("????"); //If query is changed again switch from clear search back to search symbol
            } else {
                setSearchNodes(true);
            }
        });

        setSliderListeners(sliderMinIMDBRating, labelMinIMDBRating, sliderMaxIMDBRating, labelMaxIMDBRating);

        setSliderListeners(sliderMinPersonalRating, labelMinPersonalRating, sliderMaxPersonalRating, labelMaxPersonalRating);


        //TODO Add better Listener for changes in selected categories
        searchMenuBtnCategory.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setSearchNodes(false);
                btnSearch.setText("????");
            }
        });
    }

    private void setSliderListeners(Slider sliderMinIMDBRating, Label labelMinIMDBRating, Slider sliderMaxIMDBRating, Label labelMaxIMDBRating) {
        sliderMinIMDBRating.setOnMouseReleased(event -> {
            setSearchNodes(false);
            btnSearch.setText("????");
            labelMinIMDBRating.setText(df.format(sliderMinIMDBRating.getValue()));
        });

        sliderMaxIMDBRating.setOnMouseReleased(event -> {
            setSearchNodes(false);
            btnSearch.setText("????");
            labelMaxIMDBRating.setText(df.format(sliderMaxIMDBRating.getValue()));
        });
    }

    /**
     * Allows for enabling/disabling the button for searching in the movies list
     * and resetting the min. rating & selected categories
     */
    private void setSearchNodes(boolean disable) {
        btnSearch.setDisable(disable);
        if (!disable) {
            btnSearch.setText("???");
        } else {
            btnSearch.setText("????");
            textSearch.setText("");
            if (!isSimpleSearch) {
                sliderMinIMDBRating.setValue(0);
                sliderMaxIMDBRating.setValue(10);
                sliderMinPersonalRating.setValue(0);
                sliderMaxPersonalRating.setValue(10);
                labelMinIMDBRating.setText("0");
                labelMaxIMDBRating.setText("10");
                labelMinPersonalRating.setText("0");
                labelMaxPersonalRating.setText("10");

                try {
                    initializeCategorySearchMenu();
                } catch (Exception e) {
                    ErrorDisplayer.displayError(new Exception(e));
                }
            }
        }
    }

    /**
     * Switch between simple and advanced search
     */
    public void handleSearchSettings() {
        if (isSimpleSearch) {
            boxAdvancedSearch.setOpacity(1);
            isSimpleSearch = false;
            setSearchNodes(true);
        } else {
            boxAdvancedSearch.setOpacity(0);
            isSimpleSearch = true;
        }
    }

    /**
     * Allows searching by pressing Enter (instead of using the ????-button).
     * @param keyEvent, a key-press
     */
    public void handleEnter(KeyEvent keyEvent) throws Exception {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            handleSearch();
        }
    }

    /**
     * Loads AddCategoryView FXML and populates the category listview.
     */
    public void handleAddCategory() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/CategoryView.fxml"));
        Parent root = null;

        try {
            root = loader.load();
        } catch (IOException e) {
            ErrorDisplayer.displayError(new Exception("Failed to open 'Add category'", e));
        }

        CategoryController controller = loader.getController();
        borderPane.setCenter(root);
        controller.setCategoryModel(categoryModel);
        controller.setMainController(this);
        controller.populateCategories();

        textSceneTitle.setText("Edit Category");
    }


    /**
     * Loads FXML, sets MovieListController + MovieModel and calls SortTitle method from MoveListController.
     */
    private void handleSort(Comparator<Movie> com) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/MovieListView.fxml"));
        Parent root = null;

        try {
            root = loader.load();
        } catch (IOException e) {
            new Exception("Failed to open 'open All movies'", e);
        }
        MovieListController controller = loader.getController();
        borderPane.setCenter(root);
        controller.setMovieModel(movieModel);
        controller.sortTitle(com);
    }

    /**
     * sets the listeners for menuItems with comparator.
     */
    private void setSortByContent() {
        menuItmTitleAZ.setOnAction(e -> handleSort(Comparator.comparing(Movie::getTitle)));
        menuItmTitleZA.setOnAction(e -> handleSort(Comparator.comparing(Movie::getTitle).reversed()));

        menuItmIMDBMinMax.setOnAction(e -> handleSort(Comparator.comparing(Movie::getImdbRating)));
        menuItmIMDBMaxMin.setOnAction(e -> handleSort(Comparator.comparing(Movie::getImdbRating).reversed()));

        menuItmPRMaxMin.setOnAction(e -> handleSort(Comparator.comparing(Movie::getPersonalRating)));
        menuItmPRMaxMin.setOnAction(e -> handleSort(Comparator.comparing(Movie::getPersonalRating).reversed()));
    }

    /**
     * Refresh the current view after a movie is deleted
     */
    public void reloadCurrentView() throws Exception {
        String sceneTitle = textSceneTitle.getText();
        if (sceneTitle.contains(" ")) {
            sceneTitle = sceneTitle.substring(0, sceneTitle.indexOf(" "));
        }

        switch (sceneTitle) {
            case "Warning":
                handleWarning();
                break;
            case "Home":
                handleHome();
                break;
            case "All":
                handleAllMovies();
                break;
            case "Popular":
                handlePopular();
                break;
            case "Favorite":
                handleFavorites();
                break;
            default:
                try {
                    Category category = categoryModel.getCategoryFromName(sceneTitle);
                    handleCategory(category);
                } catch (Exception e) {
                    ErrorDisplayer.displayError(new Exception(e));
                }
        }
    }

    public void handleClose() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    public void handleResize() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        if (sizeState == 0) {
            stage.setMaximized(true);
            sizeState = 1;
        } else {
            stage.setMaximized(false);
            sizeState = 0;
        }
    }
}
