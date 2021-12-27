package com.example.mynotes_andr1.ui.editor;

import android.os.Bundle;

import com.example.mynotes_andr1.R;
import com.example.mynotes_andr1.domain.CallBack;
import com.example.mynotes_andr1.domain.Note;
import com.example.mynotes_andr1.domain.NotesRepository;

import java.util.Date;
import java.util.UUID;

public class AddNotePresenter implements NotePresenter {

    public static final String KEY = "NoteDetailsFragment_ADDNOTE";
    public static final String ARG_NOTE = "ARG_NOTE";

    private EditNoteView view;
    private NotesRepository repository;

    public AddNotePresenter(EditNoteView view, NotesRepository repository) {
        this.view = view;
        this.repository = repository;

        view.setActionButtonText(R.string.btn_save);
        view.setTitle(R.string.add_title);
    }

    @Override
    public void onActionPressed(String name, String message, Date created) {

        view.showProgress();

        repository.addNote(name, message, created, new CallBack<Note>() {
            @Override
            public void onSuccess(Note result) {
                view.hideProgress();
                Bundle bundle = new Bundle();
                bundle.putParcelable(ARG_NOTE, result);
                view.actionCompleted(KEY, bundle);
            }

            @Override
            public void onError(Throwable error) {
                view.hideProgress();
            }
        });
    }
}
