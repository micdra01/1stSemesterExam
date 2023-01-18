package BLL;

import BE.ImdbInfo;
import BLL.Interfaces.IImdbManager;
import DAL.ImdbApi;
import DAL.Interfaces.IImdbAPI;

import java.util.ArrayList;

public class ImdbManager implements IImdbManager {

    private final IImdbAPI imdbAPI;
    public ImdbManager() throws Exception {
        imdbAPI = new ImdbApi();
    }

    @Override
    public ArrayList<ImdbInfo> getSearchResultFromApi(String searchWord) throws Exception {
        return imdbAPI.getSearchResultFromApi(searchWord);
    }

    @Override
    public ArrayList<String> getMovieCategoriesFromApi(String imdbId) throws Exception {
        return imdbAPI.getMovieCategoriesFromApi(imdbId);
    }

    @Override
    public String getMovieDescriptionFromImdbId(String imdbId) throws Exception {
        return imdbAPI.getMovieDescriptionFromImdbId(imdbId);
    }

    @Override
    public String getImdbRatingFromApi(String imdbId) throws Exception {
        return imdbAPI.getImdbRatingFromApi(imdbId);
    }
}
