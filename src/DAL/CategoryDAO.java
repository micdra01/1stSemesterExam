package DAL;

import BE.Category;
import BE.Movie;
import DAL.Interfaces.ICategoryDAO;
import DAL.Interfaces.IMovieDAO;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO implements ICategoryDAO {

    private DatabaseConnector databaseConnector;
    private IMovieDAO movieDAO;

    public CategoryDAO() throws IOException {
        databaseConnector = new DatabaseConnector();
        movieDAO = new MovieDAO();
    }


    /**
     * Return a list of Category objects.
     *
     * @return A list of all Categories.
     * @throws Exception throws exception if it fails to return a list of categories objects.
     */
    @Override
    public List<Category> getAllCategories() throws Exception {
        ArrayList<Category> allCategories = new ArrayList<>();

        try (Connection connection = databaseConnector.getConnection();
             Statement statement = connection.createStatement()) {
            //creates and executes the sql string
            String sql = "SELECT * FROM Category;";
            ResultSet rs = statement.executeQuery(sql);

            //loop that takes all information from category table in database and creates category objects from it
            while (rs.next()) {
                int id = rs.getInt("Id");
                String title = rs.getString("CategoryName");

                //creates the movie and add it to the list allMovies
                Category category = new Category(id, title);
                allCategories.add(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to retrieve categories", e);
        }
        //returns a list of all movies
        return allCategories;
    }

    @Override
    public Category createCategory(Category category) throws Exception {

        String sql = "INSERT INTO Category (CategoryName) VALUES (?) ;";

        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            //gets all variables from category and saves
            String title = category.getTitle();

            statement.setString(1, title);

            statement.executeUpdate();

            int id = 0;
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }

            Category generatedCategory = new Category(id, title);
            return generatedCategory;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to create category", e);
        }


    }


    @Override
    public void addMovieToCategory(Category category, Movie movie) throws Exception {

        String sql = "INSERT INTO CatMovie (MovieId, CategoryId) VALUES (?,?);";
        try (Connection connection = databaseConnector.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, movie.getId());
            stmt.setInt(2, category.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to add movie to category", e);
        }
    }

    @Override
    public void removeCategoryFromMovie(Category category, Movie movie) throws Exception {
        String sql = "DELETE FROM CatMovie WHERE MovieID = ? AND CategoryId = ?;";

        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            // Bind parameters
            statement.setInt(1, movie.getId());
            statement.setInt(2, category.getId());

            // Run the specified SQL Statement
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to delete category from movie", e);
        }
    }

    @Override
    public void deleteCategory(Category category) throws Exception {
        String sql = "DELETE FROM Category WHERE Id = ?;"; //Then delete the category itself

        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Bind parameters
            statement.setInt(1, category.getId());

            // Run the specified SQL Statement
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to delete category", e);
        }
    }


    @Override
    public List<Movie> readAllMoviesInCategory(Category category) throws Exception {
        ArrayList<Movie> allMoviesInCategory = new ArrayList<>();
        String sql = "SELECT * FROM CatMovie WHERE CategoryId = ?;";

        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            //creates and executes the first sql string
            statement.setInt(1, category.getId());
            ResultSet rs = statement.executeQuery();


            //loop that takes all information from CatMovie table in database and finds category info from it
            while (rs.next()) {
                int movieId = rs.getInt("MovieId");
                Movie movie = movieDAO.getMovieFromId(movieId);

                allMoviesInCategory.add(movie);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to retrieve categories", e);
        }
        return allMoviesInCategory;
    }


    @Override
    public ArrayList<Category> readAllCategoriesFromMovie(Movie movie) throws Exception {
        ArrayList<Category> allCategoriesFromMovie = new ArrayList<>();
        String sql = "SELECT * FROM CatMovie WHERE MovieId = ?;";

        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, movie.getId());

            //creates and executes the first sql string
            ResultSet rs = statement.executeQuery();

            //loop that takes all information from CatMovie table in database and finds category info from it
            while (rs.next()) {
                int categoryId = rs.getInt("CategoryId");
                Category category = getCategoryFromId(categoryId);
                allCategoriesFromMovie.add(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to retrieve categories", e);
        }
        //returns a list of all categories
        return allCategoriesFromMovie;
    }

    /**
     * gets a category from the id
     *
     * @param categoryId, the category id
     * @return the found category object from id.
     * @throws Exception
     */
    @Override
    public Category getCategoryFromId(int categoryId) throws Exception {
        //SELECT * FROM Movies WHERE [condition]
        String sql = " SELECT * FROM Category WHERE Id=?;";

        Category category = null; //category that is returned from db

        //get connection with database
        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, categoryId);//bind the category id to sql statement
            ResultSet rs = statement.executeQuery();//execute the statement and get category result

            while (rs.next()) {
                //get all variables from result set
                int id = rs.getInt("Id");
                String name = rs.getString("CategoryName");

                //creates the category object
                category = new Category(id, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to find category", e);
        }
        return category;
    }
}

