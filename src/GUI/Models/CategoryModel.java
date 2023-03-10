package GUI.Models;

import BE.Category;
import BE.Movie;
import BLL.CategoryManager;
import BLL.Interfaces.ICategoryManager;

import java.util.ArrayList;
import java.util.List;

public class CategoryModel {

    private final ICategoryManager categoryManager;

    public CategoryModel() throws Exception {
        categoryManager = new CategoryManager();
    }

    public void deleteCategory(Category category) throws Exception {
        categoryManager.deleteCategory(category);
    }

    public void addMovieToCategory(Category category, Movie movie) throws Exception {
        categoryManager.addMovieToCategory(category, movie);
    }

    public void removeCategoryFromMovie(Category category, Movie movie) throws Exception {
        categoryManager.removeCategoryFromMovie(category, movie);
    }

    /**
     * Return a list of Category objects.
     * @return A list of all Categories.
     * @throws Exception throws exception if it fails to return a list of categories objects.
     */
    public List<Category> getAllCategories() throws Exception {
        return categoryManager.getAllCategories();
    }

    public ArrayList<Category> readAllCategoriesFromMovie(Movie movie) throws Exception {
        return categoryManager.readAllCategoriesFromMovie(movie);
    }

    public List<Movie> readAllMoviesInCategory(Category category) throws Exception {
        return categoryManager.readAllMoviesInCategory(category);
    }

    /**
     * checks if the category exists, if it does the category will not be created
     */
    public Category createCategoryIfNotExist(String categoryName) throws Exception {
        if (categoryManager.getCategoryFromName(categoryName) == null) {
            categoryManager.createCategory(new Category(-1, categoryName));
        }
        return categoryManager.getCategoryFromName(categoryName);
    }

    public Category getCategoryFromName(String categoryName) throws Exception {
        return categoryManager.getCategoryFromName(categoryName);
    }
}


