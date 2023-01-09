package GUI.Models;

import BE.Category;
import BE.Movie;
import BLL.CategoryManager;
import BLL.Interfaces.ICategoryManager;

public class CategoryModel {

    private ICategoryManager categoryManager;


    public CategoryModel() throws  Exception {
        categoryManager = (ICategoryManager) new CategoryManager();
    }

    public Category createCategory(Category category) throws Exception {
        return categoryManager.createCategory(category);
    }

    public void deleteCategory(Category category) throws Exception {
        categoryManager.deleteCategory(category);
    }

    public void addMovieToCategory(Category category, Movie movie) throws Exception {

        //Movie mMovie = categoryManager.addMovieToCategory(category, movie);
        //System.out.println(mMovie);


    }
}


