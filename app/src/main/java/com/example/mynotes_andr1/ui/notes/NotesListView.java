package com.example.mynotes_andr1.ui.notes;

import com.example.mynotes_andr1.domain.Note;
import com.example.mynotes_andr1.domain.NoteFolder;
import com.example.mynotes_andr1.ui.adapters.NoteAdapterItem;

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
