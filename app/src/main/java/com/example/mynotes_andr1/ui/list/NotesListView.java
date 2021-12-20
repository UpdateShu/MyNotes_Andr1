package com.example.mynotes_andr1.ui.list;

import com.example.mynotes_andr1.domain.Note;
import com.example.mynotes_andr1.domain.NoteFolder;

import java.util.List;

public interface NotesListView {
    void addNote();
    void deleteNote(Note note);
    void showFolderNotes(NoteFolder folder);
}
