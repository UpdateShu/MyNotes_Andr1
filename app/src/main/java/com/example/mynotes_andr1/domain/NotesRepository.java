package com.example.mynotes_andr1.domain;

import java.util.List;

public interface NotesRepository {

    List<Note> getAllNotes();

    void addNote(Note note);

    void deleteNote(Note note);
}
