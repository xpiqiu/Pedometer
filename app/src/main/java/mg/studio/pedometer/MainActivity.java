package mg.studio.pedometer;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.realsoc.library.RippleBackground;

public class MainActivity extends AppCompatActivity {
    RippleBackground rippleBackground;
    private My dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rippleBackground = findViewById(R.id.content);
        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DoingActivity.class));
            }
        });
        dbHelper=new My(this,"xyz",null,1);
        dbHelper.getWritableDatabase();
    }
     public static class My extends SQLiteOpenHelper{
        public  static  final String uses="create table xyz(id integer primary key autoincrement,date varchar,step integer,km real)";
        private Context mContext;
        public My(Context context, String name, SQLiteDatabase.CursorFactory factory,int verson){
            super(context,name,factory,verson);
            mContext=context;
        }
        public void onCreate(SQLiteDatabase db){
            db.execSQL(uses);
        }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

    public void btnStatistics(View view) {

        startActivity(new Intent(this, Statistics.class));
    }

    public void btnHistory(View view) {
        startActivity(new Intent(this, CountActivity.class));
        rippleBackground.startRippleAnimation();
    }

    public void btnSettings(View view) {

        startActivity(new Intent(this, Setting.class));
    }



    public void btnProfile(View view) {

        startActivity(new Intent(this, Profile.class));
    }


    public void clickLayout(View view) {
        rippleBackground.startRippleAnimation();
    }
}
