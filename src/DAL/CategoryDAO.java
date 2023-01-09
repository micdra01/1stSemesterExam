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
        ArrayList<Movie> movieList = new ArrayList<>();

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

                Category category = new Category(id, title, movieList);
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

            Category generatedCategory = new Category(id, title, null);
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

        }





        @Override
        public void deleteCategory (Category category) throws Exception {
            String sql = "DELETE FROM Category WHERE Id = ?;";

            try (Connection connection = databaseConnector.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {
                // Bind parameters
                statement.setInt(1, category.getId());

                // Run the specified SQL Statement
                statement.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
                throw new Exception("Failed to delete category", e);
            }
        }


        @Override
        public List<Movie> readAllMovieInCategory (Category category) throws Exception {
            return null;
        }


        @Override
        public List<Category> readAllCategoriesFromMovie (Movie movie) throws Exception {
            return null;
        }
    }

