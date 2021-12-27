package com.example.mynotes_andr1.ui.editor;

import android.os.Bundle;

import com.example.mynotes_andr1.R;
import com.example.mynotes_andr1.domain.CallBack;
import com.example.mynotes_andr1.domain.Note;
import com.example.mynotes_andr1.domain.NoteFolder;
import com.example.mynotes_andr1.domain.NotesRepository;

import java.util.Date;

public class UpdateNotePresenter implements NotePresenter {

    public static final String KEY = "NoteDetailsFragment_UPDATENOTE";
    public static final String ARG_NOTE = "ARG_NOTE";

    private EditNoteView view;
    private NotesRepository repository;
    private Note note;

    public UpdateNotePresenter(Note note, EditNoteView view, NotesRepository repository) {
        this.view = view;
        this.repository = repository;
        this.note = note;

        view.setActionButtonText(R.string.btn_update);
        view.setTitle(R.string.update_title);
        view.setName(note.getName());
        view.setDescription(note.getDescription());
        view.setCreated(note.getCreated());
    }

    @Override
    public void onActionPressed(String name, String message, Date created) {

        view.showProgress();

        repository.updateNote(note, name, message, created, new CallBack<Note>() {
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
