package com.example.meeting_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.Slider;

import java.text.NumberFormat;
import java.util.Currency;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Slider salarySlider = findViewById(R.id.salaryInput);
        salarySlider.setLabelFormatter(new LabelFormatter() {
            @NonNull
            @Override
            public String getFormattedValue(float value) {
                NumberFormat format = NumberFormat.getCurrencyInstance();
                format.setCurrency(Currency.getInstance("USD"));
                return format.format(value);
            }
        });
    }

    private boolean checkRadioAnswer(int groupId, String answer) {
        RadioGroup group = (RadioGroup) findViewById(groupId);
        if(group.getCheckedRadioButtonId() == -1) {
            return false;
        }

        RadioButton button = (RadioButton) findViewById(group.getCheckedRadioButtonId());
        return button.getText().equals(answer);
    }

    private boolean checkCheckBox(int checkBoxId) {
        CheckBox checkBox = (CheckBox) findViewById(checkBoxId);
        return checkBox.isChecked();
    }

    private void showMessage(String message) {
        TextView resultText = (TextView) findViewById(R.id.resultText);
        resultText.setText(message);
        resultText.setVisibility(View.VISIBLE);
    }

    public void submitHandler(View view) {
        int score = 0;

        EditText nameInput = (EditText) findViewById(R.id.nameInput);
        if(nameInput.getText().toString().equals("")) {
            showMessage("Введите имя.");
            return;
        }

        EditText ageInput = (EditText) findViewById(R.id.ageInput);
        if(ageInput.getText().toString().equals("")) {
            showMessage("Введите возраст.");
            return;
        }
        int age = Integer.parseInt(ageInput.getText().toString());
        if(age < 21 || age > 40) {
            showMessage("Вы не подходите по возрасту.");
            return;
        }

        Slider salaryInput = (Slider) findViewById(R.id.salaryInput);
        float salary = salaryInput.getValue();
        if(salary < 800 || salary > 1600) {
            showMessage("Вы не подходите по зарплате.");
            return;
        }

        if(checkRadioAnswer(R.id.q1, "8")) { score += 2; }
        if(checkRadioAnswer(R.id.q2, "1")) { score += 2; }
        if(checkRadioAnswer(R.id.q3, "42")) { score += 2; }
        if(checkRadioAnswer(R.id.q4, "Да")) { score += 2; }
        if(checkRadioAnswer(R.id.q5, "true")) { score += 2; }

        if(checkCheckBox(R.id.experience)) { score += 2; }
        if(checkCheckBox(R.id.teamWork)) { score += 1; }
        if(checkCheckBox(R.id.readyToTrip)) { score += 1; }

        if(score >= 10) {
            showMessage("Вы прошли набрав " + score + " очков.");
        } else {
            showMessage("Вы не прошли, так как набрали " + score + " очков вместо 10 минимальных.");
        }
    }
}