package com.example.xpq.a1;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;

public class ProActivity extends AppCompatActivity {
    private EditText Weight;
    private EditText Height;
    private EditText st;
    private TextView bmi;
    private TextView jud;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro);
        Weight = findViewById(R.id.weight);
        Height = findViewById(R.id.height);
        st = findViewById(R.id.step);
        bmi = findViewById(R.id.BMI);
        jud = findViewById(R.id.judge);
        show();
        Weight.addTextChangedListener(watcher);
        Height.addTextChangedListener(watcher);

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
            if (!TextUtils.isEmpty(Weight.getText()) && !TextUtils.isEmpty(Height.getText())) {
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
        editor.putString("wei", Weight.getText().toString());
        editor.putString("hei", Height.getText().toString());
        editor.putString("step", st.getText().toString());
        editor.commit();
    }

    public void show() {
        SharedPreferences sharedPreferences = getSharedPreferences("huangbiao", MODE_PRIVATE);
        Weight.setText(sharedPreferences.getString("wei", "20"));
        Height.setText(sharedPreferences.getString("hei", "20"));
        st.setText(sharedPreferences.getString("step", "20"));
        if (!TextUtils.isEmpty(Weight.getText()) && !TextUtils.isEmpty(Height.getText())) {
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
