package org.example.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    public static final String KEY_URL = "&appid=bcbfb6324977c6fd50fa679111b778fe";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        final EditText editText = findViewById(R.id.cityEt);
        final TextView mainTv = findViewById(R.id.mainTv);
        final TextView tempTv = findViewById(R.id.tempTv);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userCity = editText.getText().toString().trim();

                if (userCity.trim().length() > 0) {

                    String userUrl = BASE_URL + userCity + KEY_URL;
                    Log.i("USER_URL", userUrl);

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, userUrl, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    try {

                                        String info = response.getString("weather");

                                        JSONArray ar = new JSONArray(info);

                                        for (int i = 0; i < ar.length(); i++) {

                                            JSONObject parObj = ar.getJSONObject(i);

                                            String mainResult = parObj.getString("main");

                                            mainTv.setText(mainResult);

                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Log.i("CATCH", String.valueOf(e));
                                    }

                                    try {

                                        String temp = response.getString("main");
                                        //Log.i("TEMP", temp);

                                        JSONObject tempObj = new JSONObject(temp);

                                        String tempResult = tempObj.getString("temp");

                                        double celsius = (Double.parseDouble(tempResult) - 273.15);

                                        String celTemp = String.valueOf(celsius).replace(".", ",");

                                        String[] aux = celTemp.split(",");

                                        tempTv.setText(aux[0] + " grados");
                                        tempTv.setVisibility(View.VISIBLE);

                                        Log.i("TEMP", tempObj.getString("temp"));


                                    } catch (JSONException e) {

                                        e.printStackTrace();
                                        Log.i("CATCH", String.valueOf(e));
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                            Log.i("ERROR", String.valueOf(error));
                        }
                    }
                    );
                    //Inside if
                    //TODO REQUEST
                    SingleTon.getInstance(MainActivity.this).addToRequestQue(jsonObjectRequest);

                } else {

                    Toast.makeText(MainActivity.this, "Invalid city name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
