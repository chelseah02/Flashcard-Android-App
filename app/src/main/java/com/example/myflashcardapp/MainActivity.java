package com.example.myflashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    TextView flashcardQuestion;
    TextView flashcardAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flashcardQuestion = findViewById(R.id.flashcard_question);
        flashcardAnswer = findViewById(R.id.flashcard_answer);

        flashcardQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardAnswer.setVisibility(View.VISIBLE);
                flashcardQuestion.setVisibility(View.INVISIBLE);
            }
        });
        flashcardAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardAnswer.setVisibility(View.INVISIBLE);
                flashcardQuestion.setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.card_plus_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) { // this 100 needs to match the 100 we used when we called startActivityForResult!
            if (data != null) { // check if there is an intent
                String questionString = data.getExtras().getString("QUESTION_KEY"); // 'string1' needs to match the key we used when we put the string in the Intent
                String answerString = data.getExtras().getString("ANSWER_KEY");
                flashcardQuestion.setText(questionString);
                flashcardAnswer.setText(answerString);
            }
        }
    }
}