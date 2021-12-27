package com.example.mynotes_andr1.ui.folders;

import android.widget.Toast;

import com.example.mynotes_andr1.domain.CallBack;
import com.example.mynotes_andr1.domain.Note;
import com.example.mynotes_andr1.domain.NoteFolder;
import com.example.mynotes_andr1.domain.NotesRepository;
import com.example.mynotes_andr1.ui.adapters.AdapterItem;
import com.example.mynotes_andr1.ui.adapters.NoteAdapterItem;
import com.example.mynotes_andr1.ui.adapters.NoteFolderAdapterItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NoteFoldersPresenter {
    private NoteFoldersListView view;

    private NotesRepository repository;

    private static List<NoteFolder> folders;
    public static List<NoteFolder> getFolders() {
        return folders;
    }

    public NoteFoldersPresenter(NoteFoldersListView view, NotesRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void refresh() {
        repository.loadAllNoteFolders(new CallBack<List<NoteFolder>>() {
            @Override
            public void onSuccess(List<NoteFolder> result) {
                folders = result;
                view.showNoteFolders(folders);
                view.hideProgress();
            }
            @Override
            public void onError(Throwable error) {
                view.showError(error.getMessage());
                view.hideProgress();
            }
        });
    }

    public List<AdapterItem> getNoteFoldersItems(List<NoteFolder> folders) {
        ArrayList<AdapterItem> adapterItems = new ArrayList<>();
        for (NoteFolder folder : folders) {
            adapterItems.add(new NoteFolderAdapterItem(folder, folder.getName()));
        }
        return adapterItems;
    }

    public void onNoteFolderAdded(NoteFolder folder) {
        NoteFolderAdapterItem adapterItem = new NoteFolderAdapterItem(folder, folder.getName());

        view.onNoteFolderAdded(adapterItem);
        view.hideEmpty();
    }
}
