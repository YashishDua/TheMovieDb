package in.codingninjas.cn;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by asingla on 27/06/16.
 */
public class MovieDownloadTask extends AsyncTask<String, Void, Movie[]>{



    public interface MovieDownloadTaskInterface {
        void processResults(Movie[] data);
    }

    MovieDownloadTaskInterface listener;

    public MovieDownloadTask(MovieDownloadTaskInterface listener) {
        this.listener = listener;
    }

    private Movie[] parseJson(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray Movies = obj.getJSONArray("results");
            Movie[] output = new Movie[Movies.length()];
            for (int i = 0; i < Movies.length(); i++) {
                JSONObject MoviesJSONObject = Movies.getJSONObject(i);
                String id = MoviesJSONObject.getString("id");
                String overview = MoviesJSONObject.getString("overview");
                String poster_path = MoviesJSONObject.getString("poster_path");
                Integer popularity = MoviesJSONObject.getInt("popularity");
                Integer vote_average = MoviesJSONObject.getInt("vote_average");
                output[i] = new Movie(id,poster_path,overview,popularity,vote_average);
            }
            return output;
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    protected Movie[] doInBackground(String... params) {
        if (params.length == 0)
            return null;
        StringBuffer buffer = new StringBuffer();
        try {
            URL url = new URL(params[0]);
            HttpURLConnection urlConnection =
                    (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream =
                    urlConnection.getInputStream();
            if (inputStream == null) {
                return null;
            }
            Scanner s = new Scanner(inputStream);
            while (s.hasNext()) {
                buffer.append(s.nextLine());
            }
            Log.i("jsondata", buffer.toString());
        } catch (MalformedURLException e) {
            return null;
        } catch (ProtocolException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
        return parseJson(buffer.toString());
    }

    @Override
    protected void onPostExecute(Movie[] Movies) {
        listener.processResults(Movies);
    }
}
