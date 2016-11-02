package in.codingninjas.cn;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by asingla on 20/06/16.
 */
public class MovieAdapter extends ArrayAdapter<Movie>{

    public static final String POSTER_BASE_PATH = "https://image.tmdb.org/t/p/w500_and_h281_bestv2";
    ArrayList<Movie> mData;
    Context context;
//    public static final int INSTRUCTOR_VIEW = 0;
//    public static final int Movie_VIEW = 1;

    public MovieAdapter(Context context, ArrayList<Movie> objects) {
        super(context, 0, objects);
        mData = objects;
        this.context = context;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

//    @Override
//    public int getItemViewType(int position) {
//        if (position % 3 == 0) {
//            return INSTRUCTOR_VIEW;
//        } else {
//            return Movie_VIEW;
//        }
//    }

    @Override
    public int getCount() {

//        HashMap<String, ArrayList<Task>> map = new HashMap<>();
//        for (String key : map.keySet()) {
//
//        }

        return (mData.size());
    }

//    private static class MovieHolder {
//        TextView MovieNameTextView;
//        TextView instructorNameTextView;
//        TextView numStudentsTextView;
//
//        public MovieHolder(TextView MovieNameTextView, TextView instructorNameTextView, TextView numStudentsTextView) {
//            this.MovieNameTextView = MovieNameTextView;
//            this.instructorNameTextView = instructorNameTextView;
//            this.numStudentsTextView = numStudentsTextView;
//        }
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        View v = convertView;
        v = LayoutInflater.from(context).inflate(R.layout.movielist_item,parent,false);
        ImageView movieImageView = (ImageView) v.findViewById(R.id.imageView);
        Movie currentMovie = mData.get(position);
        String posterUrl = POSTER_BASE_PATH+currentMovie.getPoster_path();
        Picasso.with(context).load(posterUrl).into(movieImageView);

//            MovieHolder holder = (MovieHolder) v.getTag();
//            int numInstructorViews = ((position/3) + 1);
//            Movie currentMovie = mData.get(position - numInstructorViews);
//            holder.MovieNameTextView.setText(currentMovie.getName());
//            holder.instructorNameTextView.setText(currentMovie.getInstructorName());
//            holder.numStudentsTextView.setText(currentMovie.getNumStudents() + "");

        return v;
    }
}
