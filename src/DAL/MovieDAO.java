package DAL;

import BE.Movie;
import DAL.Interfaces.IMovieDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO implements IMovieDAO {


    private DatabaseConnector databaseConnector;

    public MovieDAO() {
        databaseConnector = new DatabaseConnector();
    }


    /**
     * Creates a Movie.
     * @param movie The movie to create.
     * @return Returns the newly created movie.
     * @throws Exception If it fails to create the movie.
     */
    @Override
    public Movie createMovie(Movie movie) throws Exception {

        String sql = "INSERT INTO Movies (Title, PersonalRating, ImdbRating, MovieFileLink, PictureFileLink, TrailerFileLink, LastView) VALUES (?,?,?,?,?,?,?) ;";
        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            //gets all variables from movie and saves
            String title = movie.getTitle();
            double pR = movie.getPersonalRating();
            double iR = movie.getImdbRating();
            String movieLink = movie.getMovieFileLink();
            String pictureLink = movie.getPictureFileLink();
            String trailerLink = movie.getTrailerFileLink();
            Timestamp tS = movie.getLastViewed();

            statement.setString(1, title);
            statement.setDouble(2, pR);
            statement.setDouble(3, iR);
            statement.setString(4, movieLink);
            statement.setString(5, pictureLink);
            statement.setString(6, trailerLink);
            statement.setTimestamp(7, tS);

            statement.executeUpdate();

            int id = 0;
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }

            Movie generatedMovie =new Movie(id, title, pR, iR, movieLink, pictureLink, trailerLink, tS);
            return generatedMovie;

        }catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to create movie", e);
        }
    }

    /**
     * Deletes a movie.
     * @param movie The movie to delete.
     * @throws Exception If it fails to delete the movie.
     */
    @Override
    public void deleteMovie(Movie movie) throws Exception {
        String sql = "DELETE FROM Movies WHERE Id = ?;";

        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            // Bind parameters
            statement.setInt(1, movie.getId());

            // Run the specified SQL Statement
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to delete movie", e);
        }

    }

    /**
     * Returns all movies
     * @return A list of all movies.
     * @throws Exception If it fails to retrieve all movies.
     */
    @Override
    public List<Movie> getAllMovies() throws Exception {

        ArrayList<Movie> allMovies = new ArrayList<>(); //list of all movies

        try(Connection connection = databaseConnector.getConnection();
        Statement statement = connection.createStatement()){
            //creates and executes the sql string
            String sql = "SELECT * FROM Movies;";
            ResultSet rs = statement.executeQuery(sql);

            //loop that takes all information from movie table in database and creates movie objects from it
            while(rs.next()) {
                int id = rs.getInt("Id");
                String title =rs.getString("Title");
                double personalRating = rs.getDouble("PersonalRating");
                double imdbRating = rs.getDouble("ImdbRating");
                String movieFileLink = rs.getString("MovieFileLink");
                String pictureFileLink = rs.getString("PictureFileLink");
                String trailerFileLink = rs.getString("TrailerFileLink");
                Timestamp lastView = rs.getTimestamp("LastView");

                //creates the movie and add it to the list allMovies
                Movie movie = new Movie(id, title,personalRating,imdbRating,movieFileLink,pictureFileLink,trailerFileLink, lastView);
                allMovies.add(movie);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("Failed to retrieve movies", e);
        }
        //returns a list of all movies
        return allMovies;
    }

    /**
     * Update/Edit a Movie
     * @param movie, the selected movie to update
     * @throws Exception If it fails to update the movie.
     */
    @Override
    public void updateMovie(Movie movie) throws Exception {

        String sql = "UPDATE Movies SET  Title=?, PersonalRating=?, ImdbRating=?, MovieFileLink=?, PictureFileLink=?, TrailerFileLink=?, LastView=? WHERE Id=?;";

        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {


            statement.setString(1, movie.getTitle());
            statement.setDouble(2, movie.getPersonalRating());
            statement.setDouble(3, movie.getImdbRating());
            statement.setString(4, movie.getMovieFileLink());
            statement.setString(5, movie.getPictureFileLink());
            statement.setString(6, movie.getTrailerFileLink());
            statement.setTimestamp(7, movie.getLastViewed());
            statement.setInt(8,movie.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to edit the movie", e);
        }
    }

    /**
     * gets a movie from the id
     * @param movieId the movie id
     * @return the found movie object from id.
     * @throws Exception
     */
    @Override
    public Movie getMovieFromId(int movieId) throws Exception {
        //SELECT * FROM Movies WHERE [condition]

        String sql = " SELECT * FROM Movies WHERE Id=?;";
        Movie movie;

        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1,movieId);
            ResultSet rs = statement.executeQuery(sql);

            int id = rs.getInt("Id");
            String title =rs.getString("Title");
            double personalRating = rs.getDouble("PersonalRating");
            double imdbRating = rs.getDouble("ImdbRating");
            String movieFileLink = rs.getString("MovieFileLink");
            String pictureFileLink = rs.getString("PictureFileLink");
            String trailerFileLink = rs.getString("TrailerFileLink");
            Timestamp lastView = rs.getTimestamp("LastView");

            //creates the movie and add it to the list allMovies
            movie = new Movie(id, title,personalRating,imdbRating,movieFileLink,pictureFileLink,trailerFileLink, lastView);

        }
        return  movie;
    }

}
