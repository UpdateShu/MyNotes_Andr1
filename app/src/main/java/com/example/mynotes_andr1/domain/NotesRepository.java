package com.example.mynotes_andr1.domain;

import java.util.Date;
import java.util.List;

public interface NotesRepository {

    void loadAllNoteFolders(CallBack<List<NoteFolder>> callBack);

    void loadNoteFolder(NoteFolder folder, CallBack<Void> callBack);

    void addFolder(NoteFolder folder);

    void deleteFolder(NoteFolder folder);

    void addNote(String name, String description, Date date, CallBack<Note> note);

    void updateNote(String name, String description, Date date, CallBack<Note> note);
    void deleteNote(Note note, CallBack<Void> callback);
}
