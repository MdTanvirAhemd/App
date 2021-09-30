package com.ahmed.quiztruefalseapp.model;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.util.Log;

import com.ahmed.quiztruefalseapp.R;
import com.ahmed.quiztruefalseapp.VolleySingleton;
import com.ahmed.quiztruefalseapp.controller.CardStackAdapter;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuizManager {
    Context mContext;
    private RequestQueue mRequestQueue;
    private String url;

    public QuizManager(Context context) {
        mContext = context;
        mRequestQueue = VolleySingleton.getInstance().getRequestQueue();
        url = "https://opentdb.com/api.php?amount=10&category=17&type=boolean";


    }

    public void getQuizQuestions(Activity activity) {

        List<QuizQuestion> quizQuestions = new ArrayList<>();

        JsonObjectRequest filmsJsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray results = response.getJSONArray("results");
                    for(int index= 0; index<results.length(); index++) {
                        JSONObject questionJsonObject = results.getJSONObject(index);
                        String questionText = questionJsonObject.getString("question");
                        boolean questionAnwer = questionJsonObject.getBoolean("correct_answer");
                        QuizQuestion myQuestion = new QuizQuestion(questionText, questionAnwer);
                        quizQuestions.add(myQuestion);
                    }
                    CardStackView mCardStackView = activity.findViewById(R.id.myCardStackView);
                    mCardStackView.setLayoutManager(new CardStackLayoutManager(mContext));
                    mCardStackView.setAdapter(new CardStackAdapter(mContext, quizQuestions));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mRequestQueue.add(filmsJsonObjectRequest);


    }
}