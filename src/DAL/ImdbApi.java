package DAL;

import BE.ImdbInfo;
import DAL.Interfaces.IImdbAPI;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ImdbApi implements IImdbAPI {

   public ArrayList<ImdbInfo> getSearchResultFromApi(String searchWord) throws IOException, InterruptedException {
       String searchWordString = searchWord.replace(" ", "%20");

       HttpRequest request = HttpRequest.newBuilder()
               .uri(URI.create("https://imdb8.p.rapidapi.com/title/find?q=" + searchWordString))
               .header("X-RapidAPI-Key", "758854346fmshb2e7f684695dca5p1c89b6jsn2aa78bdfb8af")
               .header("X-RapidAPI-Host", "imdb8.p.rapidapi.com")
               .method("GET", HttpRequest.BodyPublishers.noBody())
               .build();

       HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
       ArrayList<ImdbInfo> result =getInfoFromResultString(response);

       return result;
   }

    /**
     * todo implement method
     * @param imdbId
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public List<String> getMovieCategoriesFromApi(String imdbId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://imdb8.p.rapidapi.com/title/get-genres?tconst=tt0944947"))
                .header("X-RapidAPI-Key", "758854346fmshb2e7f684695dca5p1c89b6jsn2aa78bdfb8af")
                .header("X-RapidAPI-Host", "imdb8.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        //System.out.println(response.body());

        ArrayList<String> categoriesToMovie = getCategoryFromApiString(response);

        for (int i = 0; categoriesToMovie.size() > i; i++){
            System.out.println(categoriesToMovie.get(i));
        }

        return null;

    }

    /**
     * todo implement method
     * @param imdbId
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public String getMovieDescriptionFromImdbId(String imdbId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://imdb8.p.rapidapi.com/title/get-overview-details?tconst=tt12750922&currentCountry=US"))
                .header("X-RapidAPI-Key", "758854346fmshb2e7f684695dca5p1c89b6jsn2aa78bdfb8af")
                .header("X-RapidAPI-Host", "imdb8.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        return null;
    }

    /**
     * todo implement method
     * @param imdbId
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public String getImdbRatingFromApi(String imdbId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://imdb8.p.rapidapi.com/title/get-ratings?tconst=tt0944947"))
                .header("X-RapidAPI-Key", "758854346fmshb2e7f684695dca5p1c89b6jsn2aa78bdfb8af")
                .header("X-RapidAPI-Host", "imdb8.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        return null;
    }

    private ArrayList<String> getCategoryFromApiString(HttpResponse<String> response){
        String categoryRaw = response.body();
        String categoryWithoutStart = categoryRaw.substring(categoryRaw.lastIndexOf("[\"") + 2);
        String categoryWithoutEnd = categoryWithoutStart.substring(0, categoryWithoutStart.indexOf("\"]"));

        ArrayList<String> categories = new ArrayList<>();

        String segments[] = categoryWithoutEnd.split("\",\"");
        for (int i = 0; segments.length > i; i++){
            categories.add(segments[i]);
        }
        return categories;
    }

    /**
     * todo get the year of release from string
     * todo get actor list and names from string
     * todo check if link is real before creating object
     * @param response
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    private ArrayList<ImdbInfo> getInfoFromResultString(HttpResponse<String> response) throws IOException, InterruptedException {
        String segments[] = response.body().split("\"id\":\"/title/");
        ArrayList<ImdbInfo> resultList = new ArrayList<>();

        for (int i = 0; segments.length > i; i++){
            //gets a string for each object on the list
            String httpResult = segments[i];

            if(httpResult.contains("\"titleType\":\"movie\"")){//only selects movies from the list
                //movie id in api database
                String movieIdRaw = httpResult.substring(httpResult.lastIndexOf("\"id\":\"/title/") + 1);
                String movieId = movieIdRaw.substring(0, movieIdRaw.indexOf("/"));
                //string title
                String titleRaw = httpResult.substring(httpResult.lastIndexOf("title\":\"") + 8);
                String title = titleRaw.substring(0, titleRaw.indexOf("\","));
                //picture link
                String pictureLinkRaw = httpResult.substring(httpResult.lastIndexOf("url\":\"") + 6);
                String picture = pictureLinkRaw.substring(0, pictureLinkRaw.indexOf("\","));
                //year of release
                String yearOfReleaseRaw = httpResult.substring(httpResult.lastIndexOf("\",\"year\":") + 9);
                String yearOfRelease = yearOfReleaseRaw.substring(0, yearOfReleaseRaw.indexOf(",\""));

                //makes imdbInfo object from response info
                ImdbInfo imdbInfo = new ImdbInfo(movieId, title, picture, yearOfRelease);
                resultList.add(imdbInfo);
            }
        }
        return resultList;
    }
}
