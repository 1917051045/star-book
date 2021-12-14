package com.example.starbook.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Book implements Parcelable {
    private String id, title, author, cover, desc, publisher, isbn, date;

    public Book() {
    }

    public Book(JSONObject json) throws JSONException {
        if (!json.isNull("title")) {
            this.title = json.getString("title");
        }
        if (!json.isNull("contributor")){
            this.author = json.getString("contributor");
        }
        else if (!json.isNull("publisher"))
            this.author = json.getString("publisher");

        if (!json.isNull("book_image")){
            this.cover = json.getString("book_image");
        }
        if (!json.isNull("description")){
            this.desc = json.getString("description");
        }
        if (!json.isNull("publisher")){
            this.publisher = json.getString("publisher");
        }
        if (!json.isNull("primary_isbn13")){
            this.isbn = json.getString("primary_isbn13");
        }
    }

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public String getAuthor() {return author;}

    public void setAuthor(String author) {this.author = author;}

    public String getCover() { return cover; }

    public void setCover(String cover) { this.cover = cover; }

    public String getDescription() {return desc;}

    public void setDescription(String desc) {this.desc = desc;}

    public String getPublisher() {return publisher;}

    public void setPublisher(String publisher) {this.publisher = publisher;}

    public String getIsbn() {return isbn;}

    public void setIsbn(String isbn) {this.isbn = isbn;}

    public String getDate() {return date;}

    public void setDate(String date) {this.date = date;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.title);
        parcel.writeString(this.author);
        parcel.writeString(this.cover);
        parcel.writeString(this.desc);
        parcel.writeString(this.publisher);
        parcel.writeString(this.isbn);
    }

    protected Book(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.author = in.readString();
        this.cover = in.readString();
        this.desc = in.readString();
        this.publisher = in.readString();
        this.isbn = in.readString();
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

}

