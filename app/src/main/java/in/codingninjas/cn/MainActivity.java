package in.codingninjas.cn;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieDownloadTask.MovieDownloadTaskInterface {

    public static final String APIKEY = "06a52fde9a75e3708dcc808df80ab6d1";
    GridView mMovieGridView;
    Button mAddButton;
    ArrayList<Movie> data;
    MovieAdapter adapter;
    ImageView constantimage1, constantimage2;
    TextView sortedinformation ;
    String Url1 = "http://media.creativebloq.futurecdn.net/sites/creativebloq.com/files/images/2013/11/marvelnew.jpg";
    String Url2 = "http://wallpoper.com/images/00/38/43/33/dc-comics_00384333.jpg";




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sortedinformation = (TextView)findViewById(R.id.sortinformation_textview);
        mMovieGridView = (GridView)findViewById(R.id.main_activity_movieGridView);
        constantimage1 = (ImageView)findViewById(R.id.constantimage1);
        constantimage2 = (ImageView)findViewById(R.id.constantimage2);

        Picasso.with(MainActivity.this).load(Url1).into(constantimage1);
        Picasso.with(MainActivity.this).load(Url2).into(constantimage2);

        String urlString  = "http://api.themoviedb.org/3/discover/movie?api_key="+APIKEY;
        MovieDownloadTask task = new MovieDownloadTask(this);
        task.execute(urlString);

        data = new ArrayList<>();
    /*    MovieDbOpenHelper helper = new MovieDbOpenHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] coloumns = { "_ID" , "OVERVIEW" , "POSTER_PATH" , "POPULARITY" , "VOTE_AVERAGE"};
        Cursor c = db.query("Movie",coloumns,null,null,null,null,null);

        while (c.moveToNext()){
            Integer MOVIE_ID = c.getInt(c.getColumnIndex("_ID"));
            String OVERVIEW = c.getString(c.getColumnIndex("OVERVIEW"));
            String POSTER_PATH = c.getString(c.getColumnIndex("POSTER_PATH"));
            Integer POPULARITY = c.getInt(c.getColumnIndex("POPULARITY"));
            Long VOTE_AVERAGE = c.getLong(c.getColumnIndex("VOTE_AVERAGE"));
            Movie object = new Movie(String.valueOf(MOVIE_ID),POSTER_PATH,OVERVIEW,POPULARITY,VOTE_AVERAGE);
            data.add(object);
        }
*/
        adapter = new MovieAdapter(this, data);
        mMovieGridView.setAdapter(adapter);

//        mMovieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent i = new Intent();
//                i.setClass(MainActivity.this, SingleMovieActivity.class);
//                Movie clickedBatch = (Movie)parent.getAdapter().getItem(position);
//                i.putExtra("batch_id", clickedBatch.getId());
//                startActivity(i);
//            }
//        });
        mMovieGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                Intent i = new Intent();
                i.setClass(MainActivity.this,SingleMovieActivity.class);
                Movie clickedMovie = (Movie)parent.getAdapter().getItem(position);
                i.putExtra(IntentConstants.MOVIE_OVERVIEW,clickedMovie.getOverview());
                i.putExtra(IntentConstants.SINGLE_MOVIE_ID,clickedMovie.getId());
                startActivity(i);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MovieDbOpenHelper helper = new MovieDbOpenHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] coloumns = new String[]{MovieDbOpenHelper.MOVIE_ID,MovieDbOpenHelper.POSTER_PATH, MovieDbOpenHelper.OVERVIEW,MovieDbOpenHelper.POPULARITY, MovieDbOpenHelper.VOTE_AVERAGE};
        Cursor c = null;

        if (item.getItemId() == R.id.popularity) {

                Log.i("Database", "Sort Popularity Entered");

                sortedinformation.setText("SORTED BY POPULARITY");
                c = db.rawQuery("SELECT * FROM"+MovieDbOpenHelper.TABLE_NAME+"ORDER BY" +MovieDbOpenHelper.POPULARITY +"DESC", null);
           /*  c = db.query(" Movie", coloumns, null, null, null, null, "POPULARITY" +
                    "" + " DESC" , null);
*/

        } else if (item.getItemId() == R.id.voteaverage) {
            sortedinformation.setText("SORTED BY VOTE AVERAGE");
             c = db.query(" Movie ", coloumns, null, null, null, null, " VOTE_AVERAGE " + "DESC" , null);
        }

        data.clear();
        while (c.moveToNext()) {
            if(c.getString(c.getColumnIndex(MovieDbOpenHelper.POPULARITY))!=null  && c.getString(c.getColumnIndex(MovieDbOpenHelper.VOTE_AVERAGE))!=null) {
                Integer MOVIE_ID = c.getInt(c.getColumnIndex(MovieDbOpenHelper.MOVIE_ID));
                String OVERVIEW = c.getString(c.getColumnIndex(MovieDbOpenHelper.OVERVIEW));
                String POSTER_PATH = c.getString(c.getColumnIndex(MovieDbOpenHelper.POSTER_PATH));
                Integer POPULARITY = c.getInt(c.getColumnIndex(MovieDbOpenHelper.POPULARITY));
                Integer VOTE_AVERAGE = c.getInt(c.getColumnIndex(MovieDbOpenHelper.VOTE_AVERAGE));
                Movie object = new Movie(String.valueOf(MOVIE_ID), POSTER_PATH, OVERVIEW, POPULARITY, VOTE_AVERAGE);
                data.add(object);
            }
        }
        adapter.notifyDataSetChanged();
        return true;
    }

    public void processResults(Movie[] movies) {
        if (movies == null)
            return;
        data.clear();
        MovieDbOpenHelper helper = new MovieDbOpenHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(MovieDbOpenHelper.TABLE_NAME,null,null);

        for (Movie m: movies) {
            data.add(m);
            ContentValues cv = new ContentValues();
            cv.put(MovieDbOpenHelper.MOVIE_ID,Integer.parseInt(m.getId()));
            cv.put(MovieDbOpenHelper.OVERVIEW,m.getOverview());
            cv.put(MovieDbOpenHelper.POSTER_PATH,m.getPoster_path());
            cv.put(MovieDbOpenHelper.POPULARITY,m.getPopularity());
            cv.put(MovieDbOpenHelper.VOTE_AVERAGE,m.getVote_average());
            db.insert(MovieDbOpenHelper.TABLE_NAME,null,cv);
            Log.i("Database","Values added to database");
        }

        adapter.notifyDataSetChanged();
    }

    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        mMovieListView = (ListView)findViewById(R.id.batchListView);
//        dataList = new ArrayList<>();
//        dataList.add("Java1");
//        dataList.add("Java2");
//        dataList.add("Java3");
//        dataList.add("Android");
//        adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, dataList);
//        mMovieListView.setAdapter(adapter);
//
//        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
//                dialogBuilder.setTitle("Item Clicked");
//                //dialogBuilder.setMessage((String)parent.getAdapter().getItem(position));
//                View v = getLayoutInflater().inflate(R.layout.dialog_view, null);
//                TextView tv1 = (TextView) v.findViewById(R.id.firstTextView);
//                tv1.setText((String)parent.getAdapter().getItem(position));
//                dialogBuilder.setView(v);
//                dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                dialogBuilder.create().show();
//            }
//        };
//
//        mMovieListView.setOnItemClickListener(listener);
//
//        mAddButton = (Button)findViewById(R.id.button);
//        mAddButton.setOnClickListener(this);
//    }

   // int i = 0;

//    @Override
//    public void onClick(View v) {
//        //dataList.add("Blah");
//       // dataList = new ArrayList<>();
//        dataList.clear();
//        dataList.add("abc");
//        dataList.add("def");
//        i++;
//        adapter.notifyDataSetChanged();
//
//    }
}
