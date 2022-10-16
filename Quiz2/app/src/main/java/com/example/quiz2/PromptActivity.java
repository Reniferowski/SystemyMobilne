package com.example.quiz2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class PromptActivity extends AppCompatActivity {
    private boolean correctAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt);

        correctAnswer = getIntent().getBooleanExtra(MainActivity.KEY_EXTRA_ANSWER, true);

        Button showCorrectAnswerButton = findViewById(R.id.hint_button_show);
        TextView answerTextView = findViewById(R.id.hint_text_view);

        showCorrectAnswerButton.setOnClickListener(v -> {
           int answer = correctAnswer ? R.string.button_true : R.string.button_false;
           answerTextView.setText(answer);
        });
    }

}