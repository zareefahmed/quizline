package com.quizline;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by decimal on 29/3/18.
 */

public class QuestionScreen3 extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.txt_option1) RadioButton txtOption1;
    @BindView(R.id.txt_option2) RadioButton txtOption2;
    @BindView(R.id.txt_option3) RadioButton txtOption3;
    @BindView(R.id.txt_option4) RadioButton txtOption4;
    @BindView(R.id.txt_question) TextView question;
    @BindView(R.id.btn_continue) Button btn_continue;
    private Context context;
    private String JsonResponse = "";
    public String TAG = QuestionScreen.class
            .getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question3_screen);
        context = QuestionScreen3.this;
        ButterKnife.bind(this);

        JsonResponse = getIntent().getStringExtra("QuestionObj");
        try {
            setData(new JSONObject(JsonResponse));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        btn_continue.setOnClickListener(this);

    }

    private void setData(JSONObject response) {

        JSONArray jArray = response.optJSONArray("X_RESULT");
        String questionNo = jArray.optJSONObject(2).optString("QUESTION_NO");
        String questionMsg = jArray.optJSONObject(2).optString("QUESTION");

        JSONArray optionObj = jArray.optJSONObject(2).optJSONArray("OPTIONS");
        String option1 = optionObj.optJSONObject(0).optString("OPTION1");
        String option2 = optionObj.optJSONObject(0).optString("OPTION2");
        String option3 = optionObj.optJSONObject(0).optString("OPTION3");
        String option4 = optionObj.optJSONObject(0).optString("OPTION4");

        question.setText(questionNo+". "+questionMsg);
        txtOption1.setText(option1);
        txtOption2.setText(option2);
        txtOption3.setText(option3);
        txtOption4.setText(option4);

//        txtOption1.setOnClickListener(this);
//        txtOption2.setOnClickListener(this);
//        txtOption3.setOnClickListener(this);
//        txtOption4.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.btn_continue:
                Intent intent = new Intent(context, QuestionScreen4.class);
                intent.putExtra("QuestionObj",JsonResponse);
                startActivity(intent);

                break;
        }
    }

    public void onRadioButtonClicked(View v) {
        //is the current radio button now checked?
        boolean  checked = ((RadioButton) v).isChecked();

        //now check which radio button is selected
        //android switch statement
        switch(v.getId()){

            case R.id.txt_option1:
                if(checked)
                    SharedPreferences.savePreferences(context,"OPTION3", txtOption1.getText().toString());
                break;

            case R.id.txt_option2:
                if(checked)
                    SharedPreferences.savePreferences(context,"OPTION3", txtOption2.getText().toString());
                break;

            case R.id.txt_option3:
                if(checked)
                    SharedPreferences.savePreferences(context,"OPTION3", txtOption3.getText().toString());
                break;

            case R.id.txt_option4:
                if(checked)
                    SharedPreferences.savePreferences(context,"OPTION3", txtOption4.getText().toString());
                break;
        }
    }



}
