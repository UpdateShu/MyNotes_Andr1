package com.example.mynotes_andr1.ui.list;

import android.os.Build;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;

import com.example.mynotes_andr1.domain.Note;
import com.example.mynotes_andr1.domain.NoteFolder;
import com.example.mynotes_andr1.domain.NotesRepository;

import java.util.ArrayList;
import java.util.List;

public class NotesListPresenter {

    private NotesListView view;

    private NotesRepository repository;

    public NotesListPresenter(NotesListView view, NotesRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void refresh() {

        List<NoteFolder> folders = repository.getAllFolders();
        long count = folders.stream().count();
        if (count > 0) {
            view.showFolderNotes(folders.get(0));
        }
    }
}
