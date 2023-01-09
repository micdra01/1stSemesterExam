package DAL.Interfaces;

import BE.Category;
import BE.Movie;

import java.util.List;

public interface ICategoryDAO {

    /**
     * Return a list of Category objects.
     * @return A list of all Categories.
     * @throws Exception throws exception if it fails to return a list of categories objects.
     */
    List<Category> getAllCategories() throws Exception;

    /**
     * Adds a movie to a category.
     * @param category The playlist to add a movie to.
     * @param movie The song to add to the playlist.
     * @throws Exception If it fails to add the song to the playlist.
     */
    void addMovieToCategory(Category category, Movie movie) throws Exception;


    /**
     * removes a category from a movie.
     * @param category last selected playlist
     * @param movie last selected song
     * @throws Exception throws exception if it fails to remove song
     */
    void removeCategoryFromMovie(Category category, Movie movie) throws Exception;


    /**
     * creates a new category.
     * @param category the new category.
     * @throws Exception if it fails to create new category
     */
    public Category createCategory(Category category) throws Exception;

    /**
     * deletes a category from the database
     * @param category selected category.
     * @throws Exception if it fails to delete category.
     */
    public void deleteCategory(Category category) throws Exception;


    /**
     * gets all Movies in a specific category
     * @param category the category we want movies from
     * @return a list of all movies with a specific category
     */
    public List<Movie> readAllMoviesInCategory(Category category) throws Exception;

    /**
     * gets all categories from a specific movie
     * @param movie the movie we want categories from
     * @return a list of all categories on the specific movie
     */
    public List<Category> readAllCategoriesFromMovie(Movie movie) throws Exception;

}
