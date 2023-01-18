package GUI.Controllers;

import BE.Category;
import GUI.Models.CategoryModel;
import GUI.Util.ConfirmDelete;
import GUI.Util.ErrorDisplayer;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class CategoryController {
    
    @FXML
    private ListView<Category> listCategories;
    @FXML
    private TextField textAddCategory;
    private CategoryModel categoryModel;
    private MainController mainController;


    /**
     * Constructor which instantiate CategoryModel
     */
    public CategoryController(){
        try {
            categoryModel = new CategoryModel();
        } catch (Exception e) {
            ErrorDisplayer.displayError(new Exception(e));
        }
    }

    /**
     * Sets CategoryModel
     */
    public void setCategoryModel(CategoryModel categoryModel){
        this.categoryModel = categoryModel;
    }

    /**
     * Creates category if button is clicked by calling CategoryModels createCategory method.
     * Populates the category list and clears text field.
     */
    public void handleSaveCategory() throws Exception{

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
     */
    public void handleDeleteCategory() {
        try {
            Category selectedCategory = listCategories.getSelectionModel().getSelectedItem();

            String header = "Are you sure you want to delete this category?";
            String content = selectedCategory.toString() + " with " + categoryModel.readAllMoviesInCategory(selectedCategory).size() + " movie(s)";
            boolean deleteCategory = ConfirmDelete.confirm(header, content);

            if(deleteCategory) {
                categoryModel.deleteCategory(selectedCategory);
                listCategories.getItems().clear();
                populateCategories();
                mainController.initializeCategoryMenu();
                mainController.initializeCategorySearchMenu();
            }
        } catch (Exception e) {
            ErrorDisplayer.displayError(new Exception(e));
        }
    }

    /**
     * Loops through list of all categories and adds category objects to listview.
     */
    public void populateCategories(){
        try {
                listCategories.getItems().addAll(categoryModel.getAllCategories());
        } catch (Exception e) {
            ErrorDisplayer.displayError(new Exception(e));
        }
    }

    /**
     * Sets MainController to be able to update the category dropdown with changes made in Edit Category
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
