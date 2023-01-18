package GUI.Controllers;

import BE.Category;
import GUI.Models.CategoryModel;
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
            throw new RuntimeException(e);
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
            Category selectedCategory = (Category) listCategories.getSelectionModel().getSelectedItem();
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
                listCategories.getItems().addAll(categoryModel.getAllCategories());
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
