package BE;

public class ImdbInfo {

    private String imdbId;

    private String title;

    private String pictureLink;

    public ImdbInfo(String imdbId, String title, String pictureLink){
        this.imdbId = imdbId;
        this.title = title;
        this.pictureLink = pictureLink;
        //prints movie info in console
        System.out.println(imdbId);
        System.out.println(title);
        System.out.println(pictureLink);
    }
}
