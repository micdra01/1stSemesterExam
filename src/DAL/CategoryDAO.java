package DAL;

import BE.Category;
import BE.Movie;
import DAL.Interfaces.ICategoryDAO;

import java.util.List;

public class CategoryDAO implements ICategoryDAO {

    /**
     * Return a list of Category objects.
     * @return A list of all Categories.
     * @throws Exception throws exception if it fails to return a list of categories objects.
     */
    @Override
    public List<Category> getAllCategories() throws Exception {
        return null;
    }

    /**
     * Adds a movie to a category.
     * @param category The playlist to add a movie to.
     * @param movie The song to add to the playlist.
     * @throws Exception If it fails to add the song to the playlist.
     */
    @Override
    public void addMovieToCategory(Category category, Movie movie) throws Exception {

    }

    /**
     * removes a category from a movie.
     * @param category last selected playlist
     * @param movie last selected song
     * @throws Exception throws exception if it fails to remove song
     */
    @Override
    public void removeCategoryFromMovie(Category category, Movie movie) throws Exception {

    }

    /**
     * creates a new category.
     * @param category the new category.
     * @throws Exception if it fails to create new category
     */
    @Override
    public Category createCategory(Category category) throws Exception {
        return null;
    }

    /**
     * deletes a category from the database
     * @param category selected category.
     * @throws Exception if it fails to delete category.
     */
    @Override
    public void deleteCategory(Category category) throws Exception {

    }

    /**
     * gets all Movies in a specific category
     * @param category the category we want movies from
     * @return a list of all movies with a specific category
     */
    @Override
    public List<Movie> readAllMovieInCategory(Category category) throws Exception {
        return null;
    }

    /**
     * gets all categories from a specific movie
     * @param movie the movie we want categories from
     * @return a list of all categories on the specific movie
     */
    @Override
    public List<Category> readAllCategoriesFromMovie(Movie movie) throws Exception {
        return null;
    }
}
