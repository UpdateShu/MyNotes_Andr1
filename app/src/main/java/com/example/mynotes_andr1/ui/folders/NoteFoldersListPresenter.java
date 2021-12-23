package com.example.mynotes_andr1.ui.folders;

import android.widget.Toast;

import com.example.mynotes_andr1.domain.CallBack;
import com.example.mynotes_andr1.domain.NoteFolder;
import com.example.mynotes_andr1.domain.NotesRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NoteFoldersListPresenter {
    private NoteFoldersListView view;

    private NotesRepository repository;

    private static List<NoteFolder> folders;
    public static List<NoteFolder> getFolders() {
        return folders;
    }

    public NoteFoldersListPresenter(NoteFoldersListView view, NotesRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void refresh() {
        if (folders == null) {
            repository.loadAllNoteFolders(new CallBack<List<NoteFolder>>() {
                @Override
                public void onSuccess(List<NoteFolder> result) {
                    folders = result;
                    view.showNoteFolders(folders);
                }
                @Override
                public void onError(Throwable error) {
                    view.showError(error.getMessage());
                }
            });
        }
        else {
            view.showNoteFolders(folders);
        }
    }
}
