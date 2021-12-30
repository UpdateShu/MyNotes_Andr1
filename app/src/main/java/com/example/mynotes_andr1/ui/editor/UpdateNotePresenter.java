package com.example.mynotes_andr1.ui.editor;

import android.os.Bundle;

import com.example.mynotes_andr1.R;
import com.example.mynotes_andr1.domain.CallBack;
import com.example.mynotes_andr1.domain.Note;
import com.example.mynotes_andr1.domain.NoteFolder;
import com.example.mynotes_andr1.domain.NotesRepository;

import java.util.Date;

public class UpdateNotePresenter implements EditNotePresenter {

    public static final String KEY = "NoteDetailsFragment_UPDATENOTE";
    public static final String ARG_NOTE = "ARG_NOTE";

    private EditNoteView view;
    private NotesRepository repository;
    private Note note;
    private NoteFolder folder;

    public UpdateNotePresenter(Note note, NoteFolder folder, EditNoteView view, NotesRepository repository) {
        this.view = view;
        this.repository = repository;
        this.note = note;
        this.folder = folder;

        view.setActionButtonText(R.string.btn_update);
        view.setTitle(R.string.update_title);
        view.setName(note.getName());
        view.setLink(note.getLink());
        view.setDescription(note.getDescription());
        view.setCreated(note.getCreated());
    }

    @Override
    public void onActionPressed(String name, String link, String description, Date created) {

        view.showProgress();

        repository.updateNote(note, folder, name, link, description, created, new CallBack<Note>() {
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
