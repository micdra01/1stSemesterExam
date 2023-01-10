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


    public CategoryController(){
        try {
            categoryModel = new CategoryModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setCategoryModel(CategoryModel categoryModel){
        this.categoryModel = categoryModel;
    }

    public void handleSaveCategory(ActionEvent actionEvent) throws Exception {

        int id = -1;
        String title = textAddCategory.getText();

        Category category = new Category(id, title);

        categoryModel.createCategory(category);
        listCategories.getItems().clear();
        populateCategories();
        textAddCategory.clear();
    }

    public void handleDeleteCategory(ActionEvent event) {
        try {
            selectedCategory = (Category) listCategories.getSelectionModel().getSelectedItem();
            categoryModel.deleteCategory(selectedCategory);
            listCategories.getItems().clear();
            populateCategories();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loops through getAllCategories list and adds category objects to listview.
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

}
