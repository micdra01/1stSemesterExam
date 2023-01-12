package GUI.Controllers;

import BE.Category;
import GUI.Models.CategoryModel;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class CategoryController {

    public Button btbDeleteCategory;
    public ListView listCategories;
    public Button btnSaveCategory;
    public TextField textAddCategory;
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

        Category category = new Category(id, title);

        categoryModel.createCategory(category);
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
    public void handleDeleteCategory(ActionEvent event){
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
            for (Category category : categoryModel.getAllCategories()){
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
