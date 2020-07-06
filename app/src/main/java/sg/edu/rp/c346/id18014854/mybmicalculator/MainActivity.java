package sg.edu.rp.c346.id18014854.mybmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.preference.PreferenceManager;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    String lastDate = "Last Calculated Date:";
    String lastBmi = "Last Calculated BMI:";

    EditText etWeight;
    EditText etHeight;

    Button btnCalc;
    Button btnRstData;

    TextView tvLastDate;
    TextView tvLastBMI;
    TextView tvOutcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);

        btnCalc = findViewById(R.id.buttonCalculate);
        btnRstData = findViewById(R.id.buttonResetData);

        tvLastDate = findViewById(R.id.textViewLastDate);
        tvLastBMI = findViewById(R.id.textViewLastBMI);
        tvOutcome = findViewById(R.id.textViewOutcome);

        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float weight = Float.parseFloat(etWeight.getText().toString());
                float height = Float.parseFloat(etHeight.getText().toString());
                float bmi = weight/ (height * height);

                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);

                tvLastDate.setText(lastDate + datetime);
                tvLastBMI.setText(lastBmi + bmi);

                etWeight.setText("");
                etHeight.setText("");

                if(bmi < 18.5){
                    tvOutcome.setText("You are underweight");
                } else if (bmi >= 18.5 && bmi <= 24.9){
                    tvOutcome.setText("Your BMI is normal");
                } else if (bmi >= 25 && bmi <= 29.9){
                    tvOutcome.setText("You are overweight");
                } else if (bmi >= 30){
                    tvOutcome.setText("You are Obese");
                }

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

                SharedPreferences.Editor prefEdit = prefs.edit();

                prefEdit.putFloat("bmi", bmi);
                prefEdit.putString("date", datetime);

                prefEdit.commit();
            }
        });

        btnRstData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etWeight.setText("");
                etHeight.setText("");

                tvLastDate.setText(lastDate);
                tvLastBMI.setText(lastBmi);
                tvOutcome.setText("");

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

                SharedPreferences.Editor prefEdit = prefs.edit();

                prefEdit.putFloat("bmi", 0);
                prefEdit.putString("date", "");

                prefEdit.commit();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        float bmi = prefs.getFloat("bmi", 0);
        String datetime = prefs.getString("date", "");

        tvLastDate.setText(lastDate + datetime);
        tvLastBMI.setText(lastBmi + bmi);
    }
}
