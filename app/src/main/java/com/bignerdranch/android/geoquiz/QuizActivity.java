package com.bignerdranch.android.geoquiz;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String CHEAT_ARRAY = "questionsCheated";

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;

    private ImageButton mNextButton;
    private ImageButton mPrevButton;

    private TextView mQuestionTextView;
    private TextView mAnswerTextView;

    private TrueFalse[] mQuestionBank = new TrueFalse[]{
            new TrueFalse(R.string.question_oceans,R.string.def_ans, true),
            new TrueFalse(R.string.question_mideast, R.string.answer_mideast, false),
            new TrueFalse(R.string.question_africa, R.string.answer_africa, false),
            new TrueFalse(R.string.question_americas, R.string.def_ans,true),
            new TrueFalse(R.string.question_asia, R.string.def_ans,true)
    };

    private int mCurrentIndex = 0;
    private boolean [] mCheatedArray = new boolean [mQuestionBank.length];
    private boolean mIsCheater;


    ///////////////////////////////////////////METHODS//////////////////////////////////////////////

    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getQuestion();
        int clear_ans = R.string.def_ans;
        mQuestionTextView.setText(question);
        mAnswerTextView.setText(clear_ans);
        mIsCheater = false;
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
        int messageResId = 0;
        int answer = R.string.def_ans;
        if(mCheatedArray[mCurrentIndex]==true){
            messageResId = R.string.second_judgment_toast;
        }else {
            if (mIsCheater) {
                if (userPressedTrue == answerIsTrue) {
                    messageResId = R.string.judgment_toast;

                } else {
                    messageResId = R.string.incorrect_judgment_toast;
                    answer = mQuestionBank[mCurrentIndex].getAnswer();
                }
                mCheatedArray[mCurrentIndex] = true;
            } else {
                if (userPressedTrue == answerIsTrue) {
                    messageResId = R.string.correct_toast;
                } else {
                    messageResId = R.string.incorrect_toast;
                    answer = mQuestionBank[mCurrentIndex].getAnswer();
                }
            }
        }
            mAnswerTextView.setText(answer);
            Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
                    .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onStart() called");

        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mCheatedArray = savedInstanceState.getBooleanArray(CHEAT_ARRAY);
            Log.d(TAG, "SavedInstanceState restored");
        }else{
            mCurrentIndex = 0;
        }

        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
        updateQuestion();
        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(QuizActivity.this, CheatActivity.class);
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
                i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
                startActivityForResult(i,0);
            }
        });
        mNextButton = (ImageButton)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });
        updateQuestion();
        mPrevButton = (ImageButton)findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex == 0) {
                    mCurrentIndex = mQuestionBank.length - 1;
                } else {
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                }
                updateQuestion();
            }
        });
        updateQuestion();
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
            savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
            savedInstanceState.putBooleanArray(CHEAT_ARRAY,mCheatedArray);
        }
////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////LOGGING LIFECYCLE METHODS//////////////////////////////////////////////

    @Override
    public void onStart(){
            super.onStart();
        Log.d(TAG,"onStart() called");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG,"onPause() called");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG,"onResume() called");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG,"onStop() called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"onDestroy() called");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
    }
}
