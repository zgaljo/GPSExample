package com.example.adrianzgaljic.gpsexample;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class DBUpdatePosition extends AsyncTask<String,Void,String> {
    private TextView statusField, roleField;
    private String username;
    private Double longitude;
    private Double latitude;



    public static String TAG = "logIspis";

    //flag 0 means get and 1 means post.(By default it is get.)
    public DBUpdatePosition(String username, Double longitude, Double latitude) {

        this.username = username;
        this.longitude = longitude;
        this.latitude = latitude;


    }

    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... arg0) {


        try {


            ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();


            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://192.168.5.84:80/android_connect/update_position.php");
            pairs.add(new BasicNameValuePair("user",username ));
            pairs.add(new BasicNameValuePair("long",Double.toString(longitude)));
            pairs.add(new BasicNameValuePair("lat",Double.toString(latitude) ));

            post.setEntity(new UrlEncodedFormEntity(pairs));
            HttpResponse response = client.execute(post);




        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
        return "";
    }


    @Override
    protected void onPostExecute(String result){

    }
}