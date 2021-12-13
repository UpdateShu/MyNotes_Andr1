package com.example.mynotes_andr1.domain;

import java.util.List;

public interface NotesRepository {

    List<NoteFolder> getAllFolders();

    void addFolder(NoteFolder folder);

    void deleteFolder(NoteFolder folder);

    List<Note> getAllNotes();

    void addNote(Note note);

    void deleteNote(Note note);
}
