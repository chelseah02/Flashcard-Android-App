package com.example.myflashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        ImageView cancelBtn = findViewById(R.id.card_cancel_btn);
        ImageView saveBtn = findViewById(R.id.card_save_btn);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Grabbing the inputted text from the 2 TextView views as Strings
                String inputQuestion = ((EditText) findViewById(R.id.editQuestionField)).getText().toString();
                String inputAnswer = ((EditText) findViewById(R.id.editAnswerField)).getText().toString();

                //Putting Strings into an Intent that can be passed to Main Activity
                Intent data = new Intent();
                data.putExtra("QUESTION_KEY", inputQuestion);
                data.putExtra("ANSWER_KEY", inputAnswer);

                // Passing backwards to Main
                setResult(RESULT_OK, data);

                finish();
            }
        });
    }
}