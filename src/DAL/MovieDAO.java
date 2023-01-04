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
    @Override
    public Movie createMovie(Movie movie) throws Exception {
        return null;
    }

    @Override
    public void deleteMovie(Movie movie) throws Exception {

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

    @Override
    public void updateMovie(Movie movie) throws Exception {

    }
}
