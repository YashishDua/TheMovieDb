package in.codingninjas.cn;

/**
 * Created by bhavya on 29/6/16.
 */
public class SingleMovie {

    private String overview;
    private String original_title;
    private String status;
    private String tagline;
    private Integer popularity;
    private String poster_path;
    private String release_date;
    private Integer revenue;
    private String imdb_id;

    public SingleMovie(String overview, String original_title, String status, String tagline, Integer popularity, String poster_path, String release_date, Integer revenue,String imdb_id) {
        this.overview = overview;
        this.original_title = original_title;
        this.status = status;
        this.tagline = tagline;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.revenue = revenue;
        this.imdb_id = imdb_id;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public Integer getRevenue() {
        return revenue;
    }

    public void setRevenue(Integer revenue) {
        this.revenue = revenue;
    }
}
