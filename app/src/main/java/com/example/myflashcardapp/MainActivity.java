package com.example.myflashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView flashcardQuestion;
    TextView flashcardAnswer;

    // Create flashcard Database variable
    FlashcardDatabase flashcardDatabase;

    // Holds a list of flashcards
    List<Flashcard> allFlashcards;

    // Variable for index tracking
    int currentCardDisplayedIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flashcardQuestion = findViewById(R.id.flashcard_question);
        flashcardAnswer = findViewById(R.id.flashcard_answer);

        // Initialize the flashcard database variable
        flashcardDatabase = new FlashcardDatabase(this);

        // Saving data to the database
        allFlashcards = flashcardDatabase.getAllCards();
        if (allFlashcards != null && allFlashcards.size() > 0) {
            ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(0).getAnswer());
        }

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
        findViewById(R.id.card_next_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Won't go to the next card if there are no other cards
                if (allFlashcards.size() == 0) {
                    return;
                }

                // Advances pointer index to show next card
                currentCardDisplayedIndex++;

                // Checks for IndexOutOfBoundsError
                if (currentCardDisplayedIndex >= allFlashcards.size()) {
                    Snackbar.make(flashcardQuestion, "You've reached the end of the cards, going back to start.",
                            Snackbar.LENGTH_SHORT)
                            .show();
                    currentCardDisplayedIndex = 0;
                }

                // Set the question and answer
                allFlashcards = flashcardDatabase.getAllCards();
                Flashcard flashcard = allFlashcards.get(currentCardDisplayedIndex);

                ((TextView) findViewById(R.id.flashcard_question)).setText(flashcard.getAnswer());
                ((TextView) findViewById(R.id.flashcard_answer)).setText(flashcard.getQuestion());
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

                // New code from Lab 3
                flashcardDatabase.insertCard(new Flashcard(questionString, answerString));
                allFlashcards = flashcardDatabase.getAllCards();
            }
        }

    }
}