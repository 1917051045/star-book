package com.example.starbook.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Category implements Parcelable{
    private String display_name, list_name_encoded;

    public Category() {
    }

    public Category(JSONObject json) throws JSONException {
        if (!json.isNull("display_name")){
            this.display_name = json.getString("display_name");
        }
        if (!json.isNull("list_name_encoded")){
            this.list_name_encoded = json.getString("list_name_encoded");
        }
    }

    public String getDisplay() {
        return display_name;
    }

    public void setDisplay(String display_name) {
        this.display_name = display_name;
    }

    public String getEncoded() {
        return list_name_encoded;
    }

    public void setEncoded(String list_name_encoded) {
        this.list_name_encoded = list_name_encoded;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.display_name);
        parcel.writeString(this.list_name_encoded);
    }

    protected Category(Parcel in) {
        this.display_name = in.readString();
        this.list_name_encoded = in.readString();
    }

    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}
