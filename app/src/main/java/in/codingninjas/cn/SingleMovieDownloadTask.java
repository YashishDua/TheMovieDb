package in.codingninjas.cn;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

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
 * Created by bhavya on 29/6/16.
 */
public class SingleMovieDownloadTask extends AsyncTask<String,Void,SingleMovie> {

    public interface SingleMovieDownloadTaskInterface{
        void processResults(SingleMovie singleMovie);
    }
    SingleMovieDownloadTaskInterface listener;
    public SingleMovieDownloadTask(SingleMovieDownloadTaskInterface listener){this.listener = listener;}

    @Override
    protected SingleMovie doInBackground(String... params) {
        if (params.length == 0)
            return null;

        String movieId = params[0];
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

    private SingleMovie parseJson(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            String overview = obj.getString("overview");
            String original_title = obj.getString("original_title");
            String status = obj.getString("status");
            String tagline = obj.getString("tagline");
            Integer popularity = obj.getInt("popularity");
            String poster_path = obj.getString("poster_path");
            String release_date = obj.getString("release_date");
            Integer revenue = obj.getInt("revenue");
            String imdb_id = obj.getString("imdb_id");

            SingleMovie movie = new SingleMovie(overview,original_title,status,tagline,popularity,poster_path,release_date,revenue,imdb_id);
            return movie;
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(SingleMovie singleMovie) {
        listener.processResults(singleMovie);
    }
}
