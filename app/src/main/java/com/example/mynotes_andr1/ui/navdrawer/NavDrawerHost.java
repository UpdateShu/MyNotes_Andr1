package com.example.mynotes_andr1.ui.navdrawer;

import androidx.appcompat.widget.Toolbar;

import com.example.mynotes_andr1.domain.Note;
import com.example.mynotes_andr1.domain.NoteFolder;

public interface NavDrawerHost {

    //void showFolder(NoteFolder folder);
    void showNote(Note note);
    void supplyToolbar(Toolbar toolbar);
    void setDetailsVisible(boolean visible);
}
