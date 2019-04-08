package com.example.hiweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText city;
    TextView result;


     String baseURL ="http://api.openweathermap.org/data/2.5/weather?q=";
     String API = "&appid=cbe98bf9373596f02ebe5eb3b2ef93cd";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        city = findViewById(R.id.city);
        result = findViewById(R.id.result);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String myURL = baseURL + city.getText().toString() + API;
                Log.i("URL","URL" + myURL);

                final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, myURL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        try{
                            String info = jsonObject.getString("weather");
                            JSONArray ar = new JSONArray(info);
                            for(int i=0; i<ar.length(); i++) {

                                JSONObject parObj =ar.getJSONObject(i);

                                String myWeather =parObj.getString("main");
                                result.setText(myWeather);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                MySingleton.getInstance(MainActivity.this).addToRequestQueue(jsonObjectRequest);

            }
        });


    }
}
