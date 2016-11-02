package in.codingninjas.cn;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class SingleMovieActivity extends AppCompatActivity implements SingleMovieDownloadTask.SingleMovieDownloadTaskInterface, VideoUrlDownloadTask.VideoUrlDownloadTaskInterface , ReviewsDownloadTask.ReviewsDownloadTaskInterface {

    public ProgressBar progressBar;
    String movieId;
    ImageView singleMovieImageView;
    public static final String POSTER_BASE_PATH = "https://image.tmdb.org/t/p/w500_and_h281_bestv2";
    Button imdb;
    String imdb_id;
    ImageView wallpaperimageview;
    TextView overview,popularity , revenue;
    TextView reviews_scrollview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_movie);

        overview = (TextView)findViewById(R.id.textview_overview);
        wallpaperimageview = (ImageView) findViewById(R.id.single_movie_activity_imageview_Wallpaper);
        singleMovieImageView = (ImageView) findViewById(R.id.single_movie_activity_imageview);
        reviews_scrollview = (TextView) findViewById(R.id.reviews_scrollview);
        imdb = (Button) findViewById(R.id.button_imdb);
        Intent i = getIntent();
        movieId = i.getStringExtra(IntentConstants.SINGLE_MOVIE_ID);
        overview.setText(i.getStringExtra(IntentConstants.MOVIE_OVERVIEW));


        Log.i("Movie",movieId);
        String urlString = "http://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + MainActivity.APIKEY;
        SingleMovieDownloadTask task = new SingleMovieDownloadTask(this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, urlString);

        String reviewURL = "https://api.themoviedb.org/3/movie/"+movieId+"/reviews?api_key=062216a71e30eabddccb5172187e11a5";
        ReviewsDownloadTask reviewtask = new ReviewsDownloadTask(this);
        reviewtask.execute(reviewURL);


        String videourlString = "https://api.themoviedb.org/3/movie/" + movieId + "/videos?api_key=062216a71e30eabddccb5172187e11a5";
        VideoUrlDownloadTask task2 = new VideoUrlDownloadTask(this);
        task2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, videourlString);


        imdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.imdb.com/title/" + imdb_id + "/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        wallpaperimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    public void processResultsVideo(String key) {
        Log.i("Movie", "Got the url for video");
        //ImageView imageView = (ImageView) findViewById(R.id.single_movie_activity_imageview_Wallpaper);
        String trailerURL = " http://img.youtube.com/vi/" + key + "/hqdefault.jpg";
        Picasso.with(this).load(trailerURL).fit().into(wallpaperimageview);
        Log.i("Movie", "Trailor Image Placed!");

    }

    public void processResults(SingleMovie movie) {
        Log.i("Movie",movie.getTagline());
        popularity = (TextView)findViewById(R.id.textview_popularity);
        popularity.setText("Popularity:"+String.valueOf(movie.getPopularity()));
       revenue = (TextView)findViewById(R.id.textview_revenue);
        revenue.setText("Revenue:"+String.valueOf(movie.getRevenue()));

        String posterUrl = POSTER_BASE_PATH + movie.getPoster_path();
        Picasso.with(this).load(posterUrl).into(singleMovieImageView);
        imdb_id = movie.getImdb_id();
/*

        revenue.setText(movie.getRevenue());
        TextView overview = (TextView)findViewById(R.id.textview_overview);
        assert overview != null;
        overview.setText(movie.getOverview());
   */


    }

    @Override
    public void processResultsReviews(String review) {
        reviews_scrollview.setText(review);
    }
}