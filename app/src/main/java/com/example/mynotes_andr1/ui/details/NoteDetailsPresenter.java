package com.example.mynotes_andr1.ui.details;

import android.provider.ContactsContract;

import com.example.mynotes_andr1.domain.Note;

public class NoteDetailsPresenter {

    private NoteDetailsView view;

    private Note note;

    public NoteDetailsPresenter(NoteDetailsView view) {
        this.view = view;
    }

    public void showNote(Note note) {
        this.note = note;

        view.showNote(note);
    }
}
