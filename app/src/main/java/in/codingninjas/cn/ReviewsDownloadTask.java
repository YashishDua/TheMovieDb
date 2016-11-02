package in.codingninjas.cn;

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
 * Created by Lenovo on 03-07-2016.
 */
public class ReviewsDownloadTask extends AsyncTask<String , Void , String> {

    SingleMovieActivity mactivity;

    public interface ReviewsDownloadTaskInterface{
        void processResultsReviews(String review);

    }
    ReviewsDownloadTaskInterface listener;
    public ReviewsDownloadTask(ReviewsDownloadTaskInterface listener){this.listener = listener;}


    @Override
    protected void onPostExecute(String s) {

        listener.processResultsReviews(s);
    }

    @Override
    protected String doInBackground(String... params) {
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
        try {
            return parseJson(buffer.toString());
        } catch (JSONException e) {
            return null;
        }

    }


    private String parseJson(String json) throws JSONException {
        JSONObject obj = new JSONObject(json);
        JSONArray results = obj.getJSONArray("results");
        String content = results.getJSONObject(0).getString("content");
        return content;

    }

}
