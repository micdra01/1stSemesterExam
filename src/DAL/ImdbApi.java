package DAL;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ImdbApi {

    public ImdbApi() throws IOException, InterruptedException {

    }

    /**
     * todo should return a object of each search result
     * @param searchWord
     * @throws IOException
     * @throws InterruptedException
     */
    public void searchInAPI(String searchWord) throws IOException, InterruptedException {

        String searchWordString = searchWord.replace(" ", "%20");
        HttpResponse<String> response = getSearchResultStringFromApi(searchWordString);
        getInfoFromSearchString(response);

    }

    private void getInfoFromSearchString(HttpResponse<String> response) throws IOException, InterruptedException {
//saves the result from the api call


        //test string so i can see full response
        System.out.println(response.body());

        String segments[] = response.body().split("\"id\":\"/title/");

        for (int i = 0; segments.length > i; i++){
            String httpResult = segments[i].substring(segments[i].lastIndexOf("/title/") + 1);


            if(httpResult.contains("\"titleType\":\"movie\"")){

                //movie id in api database
                String movieIdRaw = httpResult.substring(httpResult.lastIndexOf("\"id\":\"/title/") + 1);
                String movieId = movieIdRaw.substring(0, movieIdRaw.indexOf("/"));


                //string title
                String titleRaw = httpResult.substring(httpResult.lastIndexOf("title\":\"") + 8);
                String title = titleRaw.substring(0, titleRaw.indexOf("\","));

                //picture link
                String pictureLinkRaw = httpResult.substring(httpResult.lastIndexOf("url\":\"") + 6);
                String picture = pictureLinkRaw.substring(0, pictureLinkRaw.indexOf("\","));


                /**
                 * todo lav dem om til objekter og send til gui
                 */
                //prints movie info in console
                System.out.println(movieId);
                System.out.println(title);
                System.out.println(picture);
            }
        }
    }


   private HttpResponse getSearchResultStringFromApi(String searchWord) throws IOException, InterruptedException {
       HttpRequest request = HttpRequest.newBuilder()
               .uri(URI.create("https://imdb8.p.rapidapi.com/title/find?q=" + searchWord))
               .header("X-RapidAPI-Key", "758854346fmshb2e7f684695dca5p1c89b6jsn2aa78bdfb8af")
               .header("X-RapidAPI-Host", "imdb8.p.rapidapi.com")
               .method("GET", HttpRequest.BodyPublishers.noBody())
               .build();
       HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

       return response;

   }


}
