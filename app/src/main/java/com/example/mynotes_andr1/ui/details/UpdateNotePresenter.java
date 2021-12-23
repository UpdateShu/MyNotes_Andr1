package com.example.mynotes_andr1.ui.details;

import android.os.Bundle;

import com.example.mynotes_andr1.R;
import com.example.mynotes_andr1.domain.CallBack;
import com.example.mynotes_andr1.domain.Note;
import com.example.mynotes_andr1.domain.NotesRepository;

import java.util.Date;

public class UpdateNotePresenter implements NotePresenter {

    public static final String KEY = "NoteDetailsFragment_UPDATENOTE";
    public static final String ARG_NOTE = "ARG_NOTE";

    private NoteDetailsView view;
    private NotesRepository repository;

    public UpdateNotePresenter(Note note, NoteDetailsView view, NotesRepository repository) {
        this.view = view;
        this.repository = repository;

        view.setActionButtonText(R.string.btn_update);
        view.setTitle(R.string.update_title);
        view.setName(note.getName());
        view.setDescription(note.getDescription());
        view.setCreated(note.getCreated());
    }

    @Override
    public void onActionPressed(String name, String message, Date created) {

        view.showProgress();

        repository.updateNote(name, message, created, new CallBack<Note>() {
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
