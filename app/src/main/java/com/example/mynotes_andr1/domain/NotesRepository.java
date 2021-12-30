package com.example.mynotes_andr1.domain;

import java.util.Date;
import java.util.List;

public interface NotesRepository {

    void loadAllNoteFolders(CallBack<List<NoteFolder>> callBack);

    void loadNoteFolder(NoteFolder folder, CallBack<Void> callBack);

    void addFolder(String name, CallBack<NoteFolder> callBack);
    void deleteFolder(NoteFolder folder, CallBack<Void> callBack);

    void addNote(NoteFolder folder, String name, String link, String description, Date date, CallBack<Note> callBack);
    void updateNote(Note note, NoteFolder folder, String name, String link, String description, Date date, CallBack<Note> callBack);
    void deleteNote(Note note, CallBack<Void> callback);
}
