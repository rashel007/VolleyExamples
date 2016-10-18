package com.synergyforce.rashel.volleydemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button button;

    RequestQueue customRequestQueue;

    final static String URL = "http://192.168.1.8/volley/index.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //creating custom RequestQueue
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);// 1 MB cap
        //set up the network to use HttpUrlConnection as the HTTP client
        Network network = new BasicNetwork(new HurlStack());

        customRequestQueue = new RequestQueue(cache, network);
        customRequestQueue.start();

        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);

        //
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // defaultRequestQueue();
                customRequestQueue();
            }
        });

    }

    private void defaultRequestQueue() {
        final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                textView.setText(response);
                requestQueue.stop();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("Something Went Wrong ....");
                error.printStackTrace();
                requestQueue.stop();
            }
        });
        requestQueue.add(stringRequest);
    }

    private void customRequestQueue() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        textView.setText(response);
                        customRequestQueue.stop();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("Error. . . .");
                error.printStackTrace();
            }
        });

        customRequestQueue.add(stringRequest);
    }
}
