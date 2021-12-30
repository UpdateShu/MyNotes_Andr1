package com.example.mynotes_andr1.ui.editor;

import android.os.Bundle;

import com.example.mynotes_andr1.R;
import com.example.mynotes_andr1.domain.CallBack;
import com.example.mynotes_andr1.domain.Note;
import com.example.mynotes_andr1.domain.NoteFolder;
import com.example.mynotes_andr1.domain.NotesRepository;

import java.util.Date;

public class AddNotePresenter implements EditNotePresenter {

    public static final String KEY = "NoteDetailsFragment_ADDNOTE";
    public static final String ARG_NOTE = "ARG_NOTE";

    private EditNoteView view;
    private NotesRepository repository;
    private NoteFolder folder;

    public AddNotePresenter(NoteFolder folder, EditNoteView view, NotesRepository repository) {
        this.view = view;
        this.repository = repository;
        this.folder = folder;

        view.setActionButtonText(R.string.btn_save);
        view.setTitle(R.string.add_title);
    }

    @Override
    public void onActionPressed(String name, String link, String description, Date created) {

        view.showProgress();

        repository.addNote(folder, name, link, description, created, new CallBack<Note>() {
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
