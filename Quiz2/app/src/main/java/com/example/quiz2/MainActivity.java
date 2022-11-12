package com.example.quiz2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView questionTextView;
    private boolean answerWasShown = false;

    private final Question[] questions = new Question[] {
            new Question (R.string.q_activity, true),
            new Question (R.string.q_find_resources, false),
            new Question (R.string.q_listener, true),
            new Question (R.string.q_resources, true),
            new Question (R.string.q_version, false),
    };
    private int currentIndex = 0;

    private static final int REQUEST_CODE_PROMPT = 0;
    private static final String KEY_CURRENT_INDEX = "currentIndex";
    private static final String QUIZ_TAG = "MainActivity";
    public static final String KEY_EXTRA_ANSWER = "com.example.quiz2.correctAnswer";

    private void checkAnswer(boolean userAnswer) {
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId;
        if (answerWasShown) {
            resultMessageId = R.string.answer_was_shown;
        } else {
            if (userAnswer == correctAnswer) {
                resultMessageId = R.string.correct_answer;
            } else
                resultMessageId = R.string.incorrect_answer;
        }
        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
    }

    private void setNextQuestion() {
        currentIndex = (currentIndex +1)%questions.length;
        answerWasShown = false;
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(QUIZ_TAG, "Wywołanie metody onSaveInstanceState");
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == REQUEST_CODE_PROMPT) {
            if (data == null) return;
            answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(QUIZ_TAG, "Wywołanie metody onCreate");
        setContentView(R.layout.activity_main);

        Button trueButton = findViewById(R.id.true_button);
        Button falseButton = findViewById(R.id.false_button);
        Button nextButton = findViewById(R.id.next_button);
        Button promptButton = findViewById(R.id.hint_button);
        questionTextView = findViewById(R.id.question_text_view);

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }
        questionTextView.setText(questions[currentIndex].getQuestionId());

        trueButton.setOnClickListener(v -> {
            checkAnswer(true);
            setNextQuestion();
        });

        falseButton.setOnClickListener(v -> {
            checkAnswer(false);
            setNextQuestion();
        });

        nextButton.setOnClickListener(v -> setNextQuestion());

        promptButton.setOnClickListener(v -> {
           Intent intent = new Intent(MainActivity.this, PromptActivity.class);
           boolean correctAnswer = questions[currentIndex].isTrueAnswer();
           intent.putExtra(KEY_EXTRA_ANSWER, correctAnswer);
            startActivityForResult(intent, REQUEST_CODE_PROMPT);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(QUIZ_TAG,"Wywołanie metody onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(QUIZ_TAG,"Wywołanie metody onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(QUIZ_TAG,"Wywołanie metody onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(QUIZ_TAG,"Wywołanie metody onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(QUIZ_TAG,"Wywołanie metody onDestroy");
    }
}