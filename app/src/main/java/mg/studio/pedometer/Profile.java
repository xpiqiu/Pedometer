package mg.studio.pedometer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;

public class Profile extends AppCompatActivity {
    private EditText weight;
    private EditText height;
    private EditText stride;
    private TextView bmi;
    private TextView jud;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        weight = findViewById(R.id.weight);
        height = findViewById(R.id.height);
        stride = findViewById(R.id.stride);
        bmi = findViewById(R.id.bmi);
        jud = findViewById(R.id.judge);
        show();
        weight.addTextChangedListener(watcher);
        weight.addTextChangedListener(watcher);

        //得到键为key的值，如果没有temp值为default，如果存在temp为key的值

    }

    private TextWatcher watcher = new TextWatcher() {


        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }


        public void afterTextChanged(Editable s) {
            save();
            if (!TextUtils.isEmpty(weight.getText()) && !TextUtils.isEmpty(height.getText())) {
                SharedPreferences sharedPreferences = getSharedPreferences("huangbiao", MODE_PRIVATE);
                double temp = Double.parseDouble(sharedPreferences.getString("wei", "20")) / Double.parseDouble(sharedPreferences.getString("hei", "20")) /
                        Double.parseDouble(sharedPreferences.getString("hei", "20"));
                BigDecimal bd = new BigDecimal(temp);
                bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                bmi.setText("Your BMI is " + bd);
                if (temp < 18.5) {
                    jud.setText("Underweight");
                }
                if (temp >= 18.5 && temp < 24) {
                    jud.setText("Normal");
                }
                if (temp >= 24) {
                    jud.setText("Overweight");
                }
            }
        }
    };

    public void onBackPressed() {
        super.onBackPressed();
        save();
    }

    public void save() {
        SharedPreferences sharedPreferences = getSharedPreferences("huangbiao", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("wei", weight.getText().toString());
        editor.putString("hei", height.getText().toString());
        editor.putString("step", stride.getText().toString());
        editor.commit();
    }

    public void show() {
        SharedPreferences sharedPreferences = getSharedPreferences("huangbiao", MODE_PRIVATE);
        weight.setText(sharedPreferences.getString("wei", "20"));
        height.setText(sharedPreferences.getString("hei", "20"));
        stride.setText(sharedPreferences.getString("step", "20"));
        if (!TextUtils.isEmpty(weight.getText()) && !TextUtils.isEmpty(height.getText())) {
            double temp = Double.parseDouble(sharedPreferences.getString("wei", "20")) / Double.parseDouble(sharedPreferences.getString("hei", "20")) /
                    Double.parseDouble(sharedPreferences.getString("hei", "20"));
            BigDecimal bd = new BigDecimal(temp);
            bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
            bmi.setText("Your BMI is " + bd);
            if (temp < 18.5) {
                jud.setText("Underweight");
            }
            if (temp >= 18.5 && temp < 24) {
                jud.setText("Normal");
            }
            if (temp >= 24) {
                jud.setText("Overweight");
            }
        }
    }
}
