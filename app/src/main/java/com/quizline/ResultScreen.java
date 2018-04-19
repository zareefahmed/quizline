package com.quizline;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

/**
 * Created by decimal on 29/3/18.
 */

public class ResultScreen extends AppCompatActivity  {
    
    @BindView(R.id.txt_score) TextView txtScore;
    Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        context = ResultScreen.this;
        
        getScore();
    }

    private void getScore() {

        String url = "http://128.136.227.185:9002/GETQUIZSCORE";

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject .put("login_id", "TEST01");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        RequestQueue queue = Volley.newRequestQueue(ResultScreen.this);
        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST,url,jsonObject, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("JsonObjectReq", response.toString());
                pDialog.hide();
                SharedPreferences.savePreferences(context,"LOGIN_ID","TEST01");
                //                    JSONObject jObj = new JSONObject(response);
                JSONArray jArray = response.optJSONArray("X_RESULT");
                String SCORE = jArray.optJSONObject(0).optString("SCORE");
                String STATUS = jArray.optJSONObject(0).optString("STATUS");

                txtScore.setText(SCORE);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Volleyerror", "Error: " + error.getMessage());
                pDialog.hide();
                CommonClass.showToast(context,error.toString());

            }

        });

        queue.add(sr);
    }
}
