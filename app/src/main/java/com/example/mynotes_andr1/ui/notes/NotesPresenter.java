package com.example.mynotes_andr1.ui.notes;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.example.mynotes_andr1.R;
import com.example.mynotes_andr1.domain.CallBack;
import com.example.mynotes_andr1.domain.Note;
import com.example.mynotes_andr1.domain.NoteFolder;
import com.example.mynotes_andr1.domain.NotesRepository;
import com.example.mynotes_andr1.ui.adapters.AdapterItem;
import com.example.mynotes_andr1.ui.adapters.NoteAdapterItem;
import com.example.mynotes_andr1.ui.navdrawer.NavDrawerHost;

import java.util.ArrayList;
import java.util.List;

public class NotesPresenter {

    private final Context context;

    private final FragmentActivity activity;

    private final NotesListView view;

    private final NotesRepository repository;

    private NoteFolder currentFolder;

    private Note selectedNote = null;
    public Note getSelectedNote() {
        return selectedNote;
    }

    public NotesPresenter(Context context, FragmentActivity activity, NotesListView view, NotesRepository repository) {
        this.context = context;
        this.activity = activity;
        this.view = view;
        this.repository = repository;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void showFolder(NoteFolder folder) {
        currentFolder = folder;
        showCurrentFolder();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void showCurrentFolder() {
        view.showProgress();
        if (currentFolder == null) {
            repository.loadAllNoteFolders(new CallBack<List<NoteFolder>>() {
                @Override
                public void onSuccess(List<NoteFolder> result) {
                    if (result.stream().count() != 0) {
                        currentFolder = result.get(0);
                        loadFolder(currentFolder);
                    }
                    else {
                        view.showError(context.getString(R.string.try_again_please));
                        view.hideProgress();
                    }
                }

                @Override
                public void onError(Throwable error) {
                    view.showError(error.getMessage());
                    view.hideProgress();
                }
            });
        }
        else {
            loadFolder(currentFolder);
        }
    }

    public NoteFolder getCurrentFolder() {
        return currentFolder;
    }

    void loadFolder(NoteFolder folder) {
        repository.loadNoteFolder(folder, new CallBack<Void>() {
            @Override
            public void onSuccess(Void result) {
                view.showFolderNotes(currentFolder);
                view.hideProgress();
            }

            @Override
            public void onError(Throwable error) {
                view.showError(error.getMessage());
                view.hideProgress();
            }
        });
    }

    public List<AdapterItem> getNoteItems(NoteFolder folder) {
        ArrayList<AdapterItem> adapterItems = new ArrayList<>();
        for (Note note : folder.getNotes()) {
            adapterItems.add(new NoteAdapterItem(note,
                    note.getName(), note.getDescription(), note.getFormattedDateOfCreated()));
        }
        return adapterItems;
    }

    public void setSelectedNote(Note note) {
        selectedNote = note;
    }

    public void addNote() {
        if (activity instanceof NavDrawerHost) {
            ((NavDrawerHost)activity).showNote(null);
        }
    }

    public void onNoteAdded(Note note) {
        NoteAdapterItem adapterItem = new NoteAdapterItem(note,
                note.getName(), note.getDescription(), note.getFormattedDateOfCreated());

        view.onNoteAdded(adapterItem);
        view.hideEmpty();
    }

    public void removeNote(Note selectedNote) {

        view.showProgress();

        repository.deleteNote(selectedNote, new CallBack<Void>() {
            @Override
            public void onSuccess(Void result) {
                view.hideProgress();
                view.onNoteRemoved(selectedNote);
            }

            @Override
            public void onError(Throwable error) {
                view.hideProgress();
            }
        });
    }

    public void onNoteUpdate(Note note) {
        NoteAdapterItem adapterItem = new NoteAdapterItem(note,
                note.getName(), note.getDescription(), note.getFormattedDateOfCreated());

        view.onNoteUpdated(adapterItem);
    }
}
