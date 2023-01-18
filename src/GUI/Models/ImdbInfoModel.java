package GUI.Models;

import BE.ImdbInfo;
import BLL.ImdbManager;
import BLL.Interfaces.IImdbManager;

import java.io.IOException;
import java.util.ArrayList;

public class ImdbInfoModel {

    private IImdbManager imdbManager;

    public ImdbInfoModel() throws Exception {
        imdbManager = new ImdbManager();
    }

    public ArrayList<ImdbInfo> getSearchResultFromApi(String searchWord) throws Exception {
        return imdbManager.getSearchResultFromApi(searchWord);
    }

    public ArrayList<String> getMovieCategoriesFromApi(String imdbId) throws Exception {
        return imdbManager.getMovieCategoriesFromApi(imdbId);
    }

    public String getMovieDescriptionFromImdbId(String imdbId) throws Exception {
        return imdbManager.getMovieDescriptionFromImdbId(imdbId);
    }

    public String getImdbRatingFromApi(String imdbId) throws Exception {
        return imdbManager.getImdbRatingFromApi(imdbId);
    }
}
