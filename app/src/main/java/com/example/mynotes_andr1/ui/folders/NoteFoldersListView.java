package com.example.mynotes_andr1.ui.folders;

import com.example.mynotes_andr1.domain.Note;
import com.example.mynotes_andr1.domain.NoteFolder;
import com.example.mynotes_andr1.ui.adapters.NoteAdapterItem;
import com.example.mynotes_andr1.ui.adapters.NoteFolderAdapterItem;

import java.util.List;

public interface NoteFoldersListView {

    void showNoteFolders(List<NoteFolder> folders);

    void onNoteFolderAdded(NoteFolderAdapterItem adapterItem);
    void onNoteFolderRemoved(NoteFolder selectedFolder);

    void showProgress();
    void hideProgress();
    void showEmpty();
    void hideEmpty();

    void showError(String error);
}
