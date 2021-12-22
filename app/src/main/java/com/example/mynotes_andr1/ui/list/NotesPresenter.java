package com.example.mynotes_andr1.ui.list;

import android.os.Build;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;

import com.example.mynotes_andr1.domain.Note;
import com.example.mynotes_andr1.domain.NoteFolder;
import com.example.mynotes_andr1.domain.NotesRepository;
import com.example.mynotes_andr1.ui.navdrawer.BaseAlertDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class NotesPresenter {

    private NotesListView view;

    private NotesRepository repository;

    private NoteFolder folder;
    private Note selectedNote = null;

    public NotesPresenter(NotesListView view, NotesRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public NoteFolder getCurrentFolder() {
        List<NoteFolder> folders = repository.getAllFolders();
        if (folders.stream().count() != 0) {
            return folders.get(0);
        }
        return null;
    }

    public void showFolder(NoteFolder folder) {

        this.folder = folder;
        repository.loadNoteFolder(folder);

        view.showFolderNotes(folder);
    }

    public void setSelectedNote(Note note) {
        selectedNote = note;
    }

    public void addNote() {
        view.addNote();
    }

    public void deleteNote() {
        if (selectedNote != null) {
            view.deleteNote(selectedNote);
        }
    }
}
