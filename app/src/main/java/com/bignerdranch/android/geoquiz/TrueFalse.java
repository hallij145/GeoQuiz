package com.bignerdranch.android.geoquiz;
/**
 * Created by wolfk_000 on 9/5/2016.
 */
public class TrueFalse{
    private int mQuestion;
    private int mAnswer;
    private boolean mTrueQuestion;

    public TrueFalse(int question, int answer, boolean trueQuestion) {
        mQuestion = question;
        mAnswer = answer;
        mTrueQuestion = trueQuestion;
    }

    public int getQuestion() {
        return mQuestion;
    }
    public void setQuestion(int question) {
        mQuestion = question;
    }

    public int getAnswer(){
        return mAnswer;
    }
    public void setAnswer(int answer){
        mAnswer = answer;
    }

    public boolean isTrueQuestion() {
        return mTrueQuestion;
    }
    public void setTrueQuestion(boolean trueQuestion) {
        mTrueQuestion = trueQuestion;
    }
}
