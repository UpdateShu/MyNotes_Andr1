package com.example.mynotes_andr1.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class NoteFolder implements Parcelable {

    private String name;

    private List<Note> notes;

    public NoteFolder(String name, List<Note> notes) {
        this.name = name;
        this.notes = notes;
    }

    public String getName() {
        return name;
    }

    public List<Note> getNotes() {
        return notes;
    }

    protected NoteFolder(Parcel in) {
        name = in.readString();
        notes = in.createTypedArrayList(Note.CREATOR);
    }

    public static final Creator<NoteFolder> CREATOR = new Creator<NoteFolder>() {
        @Override
        public NoteFolder createFromParcel(Parcel in) {
            return new NoteFolder(in);
        }

        @Override
        public NoteFolder[] newArray(int size) {
            return new NoteFolder[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeTypedList(notes);
    }
}
