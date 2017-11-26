package com.manfee.myweatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    ImageView mImageView;
    TextView mTextView_City,mTextView_Temp,mTextView_Main,mTextView_Desc,mTextView_Pressure,mTextView_Humidity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.id_imageview);
        mTextView_City = (TextView) findViewById(R.id.id_textview_city);
        mTextView_Temp = (TextView) findViewById(R.id.id_textview_temp);
        mTextView_Main = (TextView) findViewById(R.id.id_textview_main);
        mTextView_Desc = (TextView) findViewById(R.id.id_textview_desc);
        mTextView_Pressure = (TextView) findViewById(R.id.id_textview_pressure);
        mTextView_Humidity = (TextView) findViewById(R.id.id_textview_humidity);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://api.openweathermap.org/data/2.5/weather?q=Kuala%20Lumpur&appid=8131be7e3e6b2014b3af931e011bd730";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.

                        try {

                            JSONObject myJSON = new JSONObject(response);
                            String name = myJSON.getString("name");
                            mTextView_City.setText(name);


                            JSONObject jObject = myJSON.getJSONObject("main");
                            String temp = jObject.getString("temp");
                            float celcius = Float.parseFloat(temp);
                            double result_celcius = celcius - 273.15;
                            String result_celcius_2f = String.format("%.1f",result_celcius);
                            mTextView_Temp.setText(result_celcius_2f+"°C");

                            JSONObject weatherObject = myJSON.getJSONArray("weather").getJSONObject(0);
                            String weather = weatherObject.getString("main");
                            mTextView_Main.setText(weather);

                            String desc = weatherObject.getString("description");
                            mTextView_Desc.setText(desc);



                            String pressure = jObject.getString("pressure");
                            mTextView_Pressure.setText(pressure);

                            String humidity = jObject.getString("humidity");
                            mTextView_Humidity.setText(humidity);


                            String photo = weatherObject.getString("icon");
                            Picasso.with(MainActivity.this).load("http://openweathermap.org/img/w/"+photo+".png").into(mImageView);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView_City.setText("That didn't work!");
                Log.d("err",error.toString());
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
}

