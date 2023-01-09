package BLL;

import BE.Category;
import BE.Movie;
import BLL.Interfaces.ICategoryManager;
import DAL.CategoryDAO;
import DAL.Interfaces.ICategoryDAO;

import java.util.List;

public class CategoryManager implements ICategoryManager {

    private ICategoryDAO databaseAccess;

    public CategoryManager() {
        databaseAccess = new CategoryDAO();
    }

    /**
     * Return a list of Category objects.
     * @return A list of all Categories.
     * @throws Exception throws exception if it fails to return a list of categories objects.
     */
    @Override
    public List<Category> getAllCategories() throws Exception {
        return databaseAccess.getAllCategories();
    }

    /**
     * Adds a movie to a category.
     * @param category The playlist to add a movie to.
     * @param movie The song to add to the playlist.
     * @throws Exception If it fails to add the song to the playlist.
     */

    @Override
    public void addMovieToCategory(Category category, Movie movie) throws Exception {
        databaseAccess.addMovieToCategory(category, movie);
    }

    /**
     * removes a category from a movie.
     * @param category last selected playlist
     * @param movie last selected song
     * @throws Exception throws exception if it fails to remove song
     */
    @Override
    public void removeCategoryFromMovie(Category category, Movie movie) throws Exception {
        databaseAccess.removeCategoryFromMovie(category, movie);

    }

    @Override
    public Category createCategory(Category category) throws Exception {
        return databaseAccess.createCategory(category);
    }


    @Override
    public void deleteCategory(Category category) throws Exception {
        databaseAccess.deleteCategory(category);
    }

    @Override
    public List<Movie> readAllMovieInCategory(Category category) throws Exception {
        return null;
    }

    @Override
    public List<Category> readAllCategoriesFromMovie(Movie movie) throws Exception {
        return null;
    }
}
