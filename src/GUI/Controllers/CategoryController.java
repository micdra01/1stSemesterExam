package GUI.Controllers;

import BE.Category;
import BE.Movie;
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
    private ListView listCategories;
    @FXML
    private TextField textAddCategory;
    private CategoryModel categoryModel;
    private Category selectedCategory;
    private MainController mainController;


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

    /**
     * Creates category if button is clicked by calling CategoryModels createCategory method.
     * Populates the category list and clears text field.
     * @param actionEvent
     * @throws Exception
     */
    public void handleSaveCategory(ActionEvent actionEvent) throws Exception{

        int id = -1;
        String title = textAddCategory.getText();

        categoryModel.createCategoryIfNotExist(title);

        listCategories.getItems().clear();
        populateCategories();
        mainController.initializeCategoryMenu();
        mainController.initializeCategorySearchMenu();
        textAddCategory.clear();

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
            mainController.initializeCategoryMenu();
            mainController.initializeCategorySearchMenu();
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

    /**
     * Sets MainController to be able to update the category dropdown with changes made in Edit Category
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
