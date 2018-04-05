package mg.studio.pedometer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnStatistics(View view) {
        startActivity(new Intent(this, CountActivity.class));
    }

    public void btnHistory(View view) {
        Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();

    }

    public void btnSettings(View view) {
        startActivity(new Intent(this, SettingActivity.class));
    }

    public void btnStart(View view) {
        startActivity(new Intent(this, DoingActivity.class));
    }


}
