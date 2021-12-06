package com.example.mynotes_andr1.ui.list;

import android.widget.LinearLayout;

import com.example.mynotes_andr1.domain.Note;
import com.example.mynotes_andr1.domain.NotesRepository;

import java.util.List;

public class NotesListPresenter {

    private NotesListView view;

    private NotesRepository repository;

    public NotesListPresenter(NotesListView view, NotesRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void refresh() {
        List<Note> result = repository.getAllNotes();

        view.showNotes(result);
    }
}
