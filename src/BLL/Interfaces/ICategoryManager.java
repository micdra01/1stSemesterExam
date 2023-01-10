package BLL.Interfaces;

import BE.Category;
import BE.Movie;

import java.util.ArrayList;
import java.util.List;

public interface ICategoryManager {

    /**
     *
     * @return
     * @throws Exception
     */
    public List<Category> getAllCategories() throws Exception;

    /**
     *
     * @param category
     * @return
     * @throws Exception
     */
    Category createCategory(Category category) throws Exception;

    /**
     *
     * @param category
     * @param movie
     * @throws Exception
     */
    public void addMovieToCategory(Category category, Movie movie) throws Exception;

    /**
     *
     * @param movie
     * @return
     * @throws Exception
     */
    public ArrayList<Category> readAllCategoriesFromMovie(Movie movie) throws Exception;

    /**
     *
     * @param category
     * @return
     * @throws Exception
     */
    public List<Movie> readAllMovieInCategory(Category category) throws Exception;

    /**
     *
     * @param category
     * @throws Exception
     */
    public void deleteCategory(Category category) throws Exception;

    /**
     *
     * @param category
     * @param movie
     * @throws Exception
     */
    public void removeCategoryFromMovie(Category category, Movie movie) throws Exception;



}
