package com.example.mynotes_andr1.ui.navdrawer;

import androidx.appcompat.widget.Toolbar;

import com.example.mynotes_andr1.domain.Note;

public interface NavDrawerHost {

    void showNote(Note note);
    void supplyToolbar(Toolbar toolbar);
}
