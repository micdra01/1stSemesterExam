package BLL.Interfaces;

import BE.ImdbInfo;

import java.io.IOException;
import java.util.ArrayList;

public interface IImdbManager {
    /**
     * gets the search results from the search word.
     * @param searchWord
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    ArrayList<ImdbInfo> getSearchResultFromApi(String searchWord) throws Exception;


    /**
     *
     * @param imdbId
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    ArrayList<String> getMovieCategoriesFromApi(String imdbId) throws Exception;

    /**
     *
     * @param imdbId
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    String getMovieDescriptionFromImdbId(String imdbId) throws Exception;


    /**
     * gets the rating from imdb api
     * @param imdbId
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    String getImdbRatingFromApi(String imdbId) throws Exception;


}
