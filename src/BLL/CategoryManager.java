package BLL;

import BE.Category;
import BE.Movie;
import DAL.Interfaces.ICategoryDAO;

import java.util.List;

public class CategoryManager implements ICategoryDAO {

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

    @Override
    public Category createCategory(Category category) throws Exception {
        return null;
    }

    @Override
    public void deleteCategory(Category category) throws Exception {

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
