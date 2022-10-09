package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView questionTextView;

    private final Question[] questions = new Question[] {
            new Question (R.string.q_activity, true),
            new Question (R.string.q_find_resources, false),
            new Question (R.string.q_listener, true),
            new Question (R.string.q_resources, true),
            new Question (R.string.q_version, false),
    };
    private int currentIndex = 0;

    private void checkAnswer(boolean userAnswer) {
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId;
        if (userAnswer == correctAnswer) {
            resultMessageId = R.string.correct_answer;
        }
        else
            resultMessageId = R.string.incorrect_answer;
        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
    }

    private void setNextQuestion() {
        currentIndex = (currentIndex +1)%questions.length;
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button trueButton;
        Button falseButton;
        Button nextButton;

        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        questionTextView = findViewById(R.id.question_text_view);

        trueButton.setOnClickListener(v -> {
            checkAnswer(true);
            setNextQuestion();
        });

        falseButton.setOnClickListener(v -> {
            checkAnswer(false);
            setNextQuestion();
        });

        nextButton.setOnClickListener(v -> setNextQuestion());
    }
}