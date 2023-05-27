package com.example.fetchtakehome;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

public class Data implements  Comparable<Data>, Parcelable {
    private int id, listId;
    private String name;
    public Data (int id, int listId, String name){
        this.id = id;
        this.listId = listId;
        this.name = name;
    }

    public int getId(){
        return this.id;
    }
    public int getListId(){
        return this.listId;
    }
    public String getName(){
        return this.name;
    }

    //implement compareTo for using Collections.sort() in MainActivity
    @Override
    public int compareTo(Data other) {
//        Log.i("[DATA", "in compare to");
        //Compare this.listId with other.listId
        int listItemComparison = Integer.compare(this.listId, other.getListId());
        if(listItemComparison != 0){
            return listItemComparison;
        }
        //if they're the same listId then we compare their names
        //split the name by spaces and compare the int value of the name
        int thisNameNumber = Integer.parseInt(this.name.split(" ")[1]);
        int otherNameNumber = Integer.parseInt(other.getName().split(" ")[1]);
        return Integer.compare(thisNameNumber,otherNameNumber);
    }

    @Override
    public int describeContents() {return 0;}

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int flags) {
        parcel.writeInt(this.id);
        parcel.writeInt(this.listId);
        parcel.writeString(this.name);
    }

    public static final Parcelable.Creator<Data> CREATOR
            = new Parcelable.Creator<Data>(){

        @Override
        public Data createFromParcel(Parcel source) {
            return new Data(source);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[0];
        }
    };

    private Data(Parcel in){
        this.id = in.readInt();
        this.listId = in.readInt();
        this.name = in.readString();
    }
}
