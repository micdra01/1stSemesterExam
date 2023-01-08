package BLL.Util;

import BE.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieSearcher {
    /**
     * Filter the list of movies using a search query
     * @param searchBase, the list of all movies
     * @param query, the string input used to filter
     * @return a list of movies matching the query in title
     */
    public List<Movie> search(List<Movie> searchBase, String query) {
        List<Movie> searchResult = new ArrayList<>();

        for (Movie movie: searchBase) {
            if(compareToTitle(query, movie)) {
                searchResult.add(movie);
            }
        }

        return searchResult;
    }

    /**
     * Filter the list of movies using a search query
     * @param searchBase, the list of all movies
     * @param query, the string input used to filter
     * @param minRating, the min. rating
     * @return a list of movies matching the query in title
     */
    public List<Movie> searchAdvanced(List<Movie> searchBase, String query, double minRating, List<String> categories) {
        List<Movie> searchResult = new ArrayList<>();

        for (Movie movie: searchBase) {
            if(compareToTitle(query, movie) && compareToRating(minRating, movie) && (categories.isEmpty() || compareToCategory(categories, movie))) {
                searchResult.add(movie);
            }
        }

        return searchResult;
    }

    /**
     * Helper method to check if the movie title matches the search query
     * @param query, the string input to search for
     * @param movie, the movie to check
     * @return true if there is a match, false if not.
     */
    private boolean compareToTitle(String query, Movie movie) {
        return movie.getTitle().toLowerCase().contains(query.toLowerCase());
    }

    /**
     * Helper method to check if the movie rating is higher than the min. rating
     * @param query, the double input to search for
     * @param movie, the movie to check
     * @return true if there is a match, false if not.
     */
    private boolean compareToRating(double query, Movie movie) {
        return movie.getImdbRating() > query;
    }

    /**
     * Helper method to check if the movie category matches the search query
     * @param categories, the list of categories to search for
     * @param movie, the movie to check
     * @return true if there is a match, false if not.
     */
    private boolean compareToCategory(List<String> categories, Movie movie) {
        if (categories.get(0) == null) return true; //If no categories are selected, do not filter for this part.
        if (movie.getCategory() == null) return false; //If movie does not have a category, do not include it.
        return movie.getCategory().matches(categories.get(0)) || movie.getCategory().matches(categories.get(1));
    }
}
