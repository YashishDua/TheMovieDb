package in.codingninjas.cn;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Lenovo on 02-07-2016.
 */
public class MovieDbOpenHelper extends SQLiteOpenHelper {
    public MovieDbOpenHelper(Context context) {
        super(context,"MovieDb", null, 1);
    }


    public static final String MOVIE_ID = " _ID ";
    public static final String POSTER_PATH = " POSTER_PATH ";
    public static final String OVERVIEW = " OVERVIEW ";
    public static final String POPULARITY = " POPULARITY ";
    public static final String VOTE_AVERAGE = " VOTE_AVERAGE ";
    public static final String TABLE_NAME = " Movie ";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create Table Movie (" +MOVIE_ID+
                " INTEGER PRIMARY KEY ,"+ POSTER_PATH+
                " TEXT ,"+ OVERVIEW+
                " TEXT ,"+ POPULARITY+
                " INTEGER ,"+ VOTE_AVERAGE+" INTEGER );");
     //   db.execSQL("Create Table SingleMovie ( _IMDB_ID INTEGER PRIMARY KEY , REVENUE TEXT , POPULARITY TEXT);");
        Log.i("Database","Table Created" + db.isDatabaseIntegrityOk());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
