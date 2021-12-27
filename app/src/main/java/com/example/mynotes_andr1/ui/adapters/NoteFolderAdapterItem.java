package com.example.mynotes_andr1.ui.adapters;

import com.example.mynotes_andr1.domain.Note;
import com.example.mynotes_andr1.domain.NoteFolder;

public class NoteFolderAdapterItem implements AdapterItem {

    private NoteFolder folder;

    private String name;

    public NoteFolderAdapterItem(NoteFolder folder, String name) {
        this.folder = folder;
        this.name = name;
    }

    public NoteFolder getFolder() {
        return folder;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getId() {
        return folder.getId();
    }
}
