package mg.studio.pedometer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.realsoc.library.RippleBackground;

public class MainActivity extends AppCompatActivity {
    RippleBackground rippleBackground;

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
    }


    public void btnStatistics(View view) {

        startActivity(new Intent(this, Statistics.class));
    }

    public void btnHistory(View view) {
        Toast.makeText(this, "History", Toast.LENGTH_SHORT).show();
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
