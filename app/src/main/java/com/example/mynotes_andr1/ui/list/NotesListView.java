package com.example.mynotes_andr1.ui.list;

import com.example.mynotes_andr1.domain.Note;
import com.example.mynotes_andr1.domain.NoteFolder;
import com.example.mynotes_andr1.ui.list.adapter.NoteAdapterItem;

import java.util.List;

public interface NotesListView {

    void showFolderNotes(NoteFolder folder);
    void onNoteAdded(NoteAdapterItem adapterItem);
    void onNoteRemoved(Note selectedNote);
    void onNoteUpdated(NoteAdapterItem adapterItem);

    void showProgress();
    void hideProgress();
    void showEmpty();
    void hideEmpty();
    void showError(String error);
}
