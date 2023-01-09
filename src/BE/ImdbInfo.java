package BE;

public class ImdbInfo {

    private String imdbId;

    private String title;

    private String pictureLink;
    private String yearOfRelease;

    public ImdbInfo(String imdbId, String title, String pictureLink,String yearOfRelease){
        this.imdbId = imdbId;
        this.title = title;
        this.pictureLink = pictureLink;
        this.yearOfRelease = yearOfRelease;
        //prints movie info in console
        System.out.println(imdbId);
        System.out.println(title);
        System.out.println(pictureLink);
        System.out.println(yearOfRelease);
    }
}
