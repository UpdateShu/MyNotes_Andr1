package com.example.mynotes_andr1.ui.folders;

import com.example.mynotes_andr1.domain.NoteFolder;
import com.example.mynotes_andr1.domain.NotesRepository;

import java.util.List;

public class NoteFoldersPresenter {
    private NoteFoldersListView view;

    private NotesRepository repository;

    public NoteFoldersPresenter(NoteFoldersListView view, NotesRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void refresh() {
        List<NoteFolder> folders = repository.getAllFolders();

        view.showNoteFolders(folders);
    }
}