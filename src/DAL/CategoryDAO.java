package DAL;

import BE.Category;
import BE.Movie;
import DAL.Interfaces.ICategoryDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO implements ICategoryDAO {

    private DatabaseConnector databaseConnector;

    public CategoryDAO() {
        databaseConnector = new DatabaseConnector();
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

        try(Connection connection = databaseConnector.getConnection();
            Statement statement = connection.createStatement()){
            //creates and executes the sql string
            String sql = "SELECT * FROM Category;";
            ResultSet rs = statement.executeQuery(sql);

            //loop that takes all information from category table in database and creates category objects from it
            while(rs.next()) {
                int id = rs.getInt("Id");
                String title =rs.getString("CategoryName");

                //creates the movie and add it to the list allMovies
                Category category = new Category(id, title);
                allCategories.add(category);
            }
        }catch (Exception e){
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
                //ArrayList<Movie> movieList = category.getMovieList();

                statement.setString(1, title);
                //statement.setArray(2, (Array) movieList);

                statement.executeUpdate();

                int id = 0;
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    id = resultSet.getInt(1);
                }

            Category generatedCategory = new Category(id, title);
            return generatedCategory;

            }catch (SQLException e) {
                e.printStackTrace();
                throw new Exception("Failed to create category", e);
            }


        }


        @Override
        public void addMovieToCategory (Category category, Movie movie) throws Exception {

            String sql = "INSERT INTO CatMovie (MovieId, CategoryId) VALUES (?,?);";
            try (Connection connection = databaseConnector.getConnection())
            {
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setInt(1, movie.getId());
                stmt.setInt(2, category.getId());
                stmt.executeUpdate();

            }catch (SQLException e) {
                e.printStackTrace();
                throw new Exception("Failed to add movie to category", e);
            }
        }

        @Override
        public void removeCategoryFromMovie (Category category, Movie movie) throws Exception {
            String sql = "DELETE FROM CatMovie WHERE MovieID = ? AND CategoryId = ?;";

            try (Connection connection = databaseConnector.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {
                // Bind parameters
                statement.setInt(1, movie.getId());
                statement.setInt(2, category.getId());

                // Run the specified SQL Statement
                statement.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
                throw new Exception("Failed to delete category from movie", e);
            }
        }

        @Override
        public void deleteCategory (Category category) throws Exception {
            String sql1 = "DELETE FROM CatMovie WHERE CategoryId = ?;"; //First delete category from all movies
            String sql2 = "DELETE FROM Category WHERE Id = ?;"; //Then delete the category itself

            try (Connection connection = databaseConnector.getConnection();
                 PreparedStatement statement1 = connection.prepareStatement(sql1);
                 PreparedStatement statement2 = connection.prepareStatement(sql2)) {

                statement1.setInt(1, category.getId());
                statement1.executeUpdate();

                // Bind parameters
                statement2.setInt(1, category.getId());

                // Run the specified SQL Statement
                statement2.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
                throw new Exception("Failed to delete category", e);
            }
        }


        @Override
        public List<Movie> readAllMoviesInCategory(Category category) throws Exception {
            return null;
        }


        @Override
        public ArrayList<Category> readAllCategoriesFromMovie (Movie movie) throws Exception {
            ArrayList<Category> allCategoriesFromMovie = new ArrayList<>();

            try(Connection connection = databaseConnector.getConnection();
                Statement statement1 = connection.createStatement();
                Statement statement2 = connection.createStatement()){
                //creates and executes the first sql string
                String sql1 = "SELECT * FROM CatMovie WHERE MovieId =" + movie.getId() + ";";
                ResultSet rs1 = statement1.executeQuery(sql1);

                //loop that takes all information from CatMovie table in database and finds category info from it
                while(rs1.next()) {
                    int categoryId = rs1.getInt("CategoryId");

                    //finds the category and adds it to the list allCategoriesFromMovie
                    String sql2 = "SELECT * FROM Category WHERE Id = " + categoryId + ";";
                    ResultSet rs2 = statement2.executeQuery(sql2);

                    while(rs2.next()) {
                        Category category = new Category(rs2.getInt("Id"), rs2.getString("CategoryName"));
                        allCategoriesFromMovie.add(category);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                throw new Exception("Failed to retrieve categories", e);
            }
            //returns a list of all movies
            return allCategoriesFromMovie;
        }
    }

