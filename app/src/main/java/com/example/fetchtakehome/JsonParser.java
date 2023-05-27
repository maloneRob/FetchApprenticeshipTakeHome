package com.example.fetchtakehome;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

public class JsonParser {
    String Tag = "[JSONPARSER]";
    String filename;
    Context context;
    ArrayList<Data> dataArrayList;
    public JsonParser(String filename, Context context){
        this.filename = filename;
        this.context = context;
        this.dataArrayList = loadDataFromJson(getJsonString());
    }
    //loadJson(string):
    //takes a filename as an argument, reads the file, creates a json string
    // if exception is thrown then returns a null string
    private String getJsonString(){
        try{
            InputStream inputStream = context.getAssets().open(filename);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String jsonString = new String(buffer, "UTF-8");
            Log.i(Tag, jsonString);
            return jsonString;

        } catch (Exception e){
            Log.e(Tag, e.toString());
            return null;
        }
    }
    //loadDataFromJson(string):ArrayList<Data>
    //takes a jsonString as an argument
    //iterates through the json array and builds Data objects ignoring empty names and null names
    //inserting them into jsonDataArray
    //if exception thrown returns empty arraylist
    private ArrayList<Data> loadDataFromJson(String jsonString){
        ArrayList<Data> jsonDataArray = new ArrayList<>();
        Log.i(Tag,"Loading data from json");
        try {
            //iterate through array
            JSONArray jsonArray = new JSONArray(jsonString);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                int listId = jsonObject.getInt("listId");
                String name = jsonObject.getString("name");

                //filter empty names and null names
                if(!TextUtils.isEmpty(name) && !TextUtils.equals(name,"null") ){
                    Data data = new Data(id, listId, name);
                    jsonDataArray.add(data);
                }

            }
//             //sort the list
//            //Data class implements comparable to sort by listId and then name
            Collections.sort(jsonDataArray);
            return jsonDataArray;

        } catch (Exception e) {
            Log.i(Tag, "oh no an exception happened :( " + e.toString());
            return new ArrayList<>();
        }

    }

    public ArrayList<Data> getDataArrayList(){return this.dataArrayList;}
}
