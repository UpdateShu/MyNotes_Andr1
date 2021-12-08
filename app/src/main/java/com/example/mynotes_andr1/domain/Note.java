package com.example.mynotes_andr1.domain;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Note implements Parcelable {

    private String name;

    private String description;

    private Date created;

    public Note(String name, String description, Date created) {
        this.name = name;
        this.description = description;
        this.created = created;
    }

    protected Note(Parcel in) {
        name = in.readString();
        description = in.readString();
        created = new Date();
        try {
            created = (new SimpleDateFormat()).parse(in.readString());
        }
        catch (Exception ex) {
            Log.d("Note constructor", ex.toString());
        }
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFormattedDateOfCreated()
    {
        return "\t" + Locale.getDefault() + ":\t" + new SimpleDateFormat().format(created);
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(new SimpleDateFormat().format(created));
    }
}
