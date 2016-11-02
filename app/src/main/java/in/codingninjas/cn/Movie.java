package in.codingninjas.cn;

/**
 * Created by asingla on 20/06/16.
 */
public class Movie {

    private String id;
    private String poster_path;
    private String overview;
    private Integer popularity;
    private Integer vote_average;


    public Movie(String id, String poster_path, String overview, Integer popularity, Integer vote_average) {
        this.id = id;
        this.poster_path = poster_path;
        this.overview = overview;
        this.popularity = popularity;
        this.vote_average = vote_average;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    public Integer getVote_average() {
        if(vote_average==null)
            return null;
        else
        return vote_average;
    }

    public void setVote_average(Integer vote_average) {
        this.vote_average = vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }


}
