package com.quizline;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.quizline.R.id.et_number_login;

public class LoginScreen extends AppCompatActivity {
    @BindView(R.id.et_number_login)  EditText etNumberLogin;
    @BindView(R.id.et_password_login)  EditText etPasswordLogin;
    @BindView(R.id.btn_login)  Button btn_login;
    private String number,password;
    private Context context;
    public String TAG = LoginScreen.class
            .getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        ButterKnife.bind(this);
        context = LoginScreen.this;

        setListener();


    }

    private void setListener() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = etNumberLogin.getText().toString().trim();
                password = etPasswordLogin.getText().toString().trim();

                    verifyFromDBJson();

            }
        });
    }

    private void verifyFromDBJson() {

        String url = "http://128.136.227.185:9002/AuthenticatUser";

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject .put("login_id", number);
            jsonObject .put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST,url,jsonObject, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                pDialog.hide();
                SharedPreferences.savePreferences(context,"LOGIN_ID",number);
                //                    JSONObject jObj = new JSONObject(response);
                JSONArray jArray = response.optJSONArray("X_RESULT");
                String RESPONSE_TYPE = jArray.optJSONObject(0).optString("RESPONSE_TYPE");
                String RESPONSE_MESSAGE = jArray.optJSONObject(0).optString("RESPONSE_MESSAGE");
                if (RESPONSE_TYPE.equalsIgnoreCase("Y")) {
                    Intent intent = new Intent(context, QuestionScreen.class);
                    startActivity(intent);
                }else{
                    CommonClass.showToast(context,RESPONSE_MESSAGE);
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                pDialog.hide();
                CommonClass.showToast(context,error.toString());

            }

        });

        queue.add(sr);
    }

}
