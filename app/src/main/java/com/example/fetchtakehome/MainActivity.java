package com.example.fetchtakehome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String fileName = "hiring.json";
    private final String Tag = "[MAINACTIVITY]";

    private ArrayList<Data> jsonDataArray = new ArrayList<>();

    private ListView dataListView;
    private ProgressBar progressBar;
    private TextView fileNameTxtView;

    private LoadJson loadJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataListView = findViewById(R.id.dataListView);
        progressBar = findViewById(R.id.progressbar);
        fileNameTxtView = findViewById(R.id.fileNameTextView);
        //if we haven't already parsed the json then we do so
        //otherwise we grab the arraylist from the bundle
        if(savedInstanceState == null){
            loadJson = new LoadJson(fileName);
            loadJson.start();
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            fileNameTxtView.setText(getResources().getString(R.string.showing_content)+ fileName);
            jsonDataArray = savedInstanceState.getParcelableArrayList("jsonDataArray", Data.class);
            dataListView.setAdapter(new ListAdapter(getApplicationContext(), jsonDataArray));
        }


    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //if we've already parsed the json then we save just save the data
        if(jsonDataArray.size() > 0){
            outState.putParcelableArrayList("jsonDataArray", jsonDataArray);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(jsonDataArray.size() == 0){
            loadJson = new LoadJson(fileName);
        }
    }

    //for junit testing
    public ArrayList<Data> getJsonDataArray(){return jsonDataArray;}
    //LoadJson
    //inner class extending thread
    //parses the Json and then updates the UI accordingly
    class LoadJson extends Thread{
        String filename;
        public LoadJson(String filename){
            this.filename = filename;
        }
        @Override
        public void run(){
            try{
                // set the textView and makes the progressbar visible
                //though you don't really see it since the data parses quickly
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fileNameTxtView.setText(getResources().getString(R.string.fetching_content)+ filename);
                        progressBar.setProgressTintList(ColorStateList.valueOf(Color.RED));
                        progressBar.setVisibility(View.VISIBLE);
                    }
                });
                // make new instance of the JsonParser class with the context and filename to parse
                Log.i(Tag,"parsing json");
                JsonParser jsonParser = new JsonParser(this.filename, getApplicationContext());
                //update the jsonDataArray
                synchronized (jsonDataArray){
                    jsonDataArray = jsonParser.getDataArrayList();
                }

                //set the adapter on the UI thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(Tag, "notifying adapter list changed");
                        Log.i(Tag, "size of jsonDataArray: " + jsonDataArray.size());
                        fileNameTxtView.setText(getResources().getString(R.string.showing_content) + filename);
                        progressBar.setVisibility(View.INVISIBLE);
                        dataListView.setAdapter(new ListAdapter(getApplicationContext(), jsonDataArray));
                    }
                });

            } catch (Exception e){
                Log.i(Tag, "oh no an exception happened :( " + e.toString());
                Log.e(Tag, e.toString());
            }

        }

    }
}