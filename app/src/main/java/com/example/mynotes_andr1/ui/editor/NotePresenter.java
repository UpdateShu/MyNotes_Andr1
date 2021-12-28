package com.example.mynotes_andr1.ui.editor;

import java.util.Date;

public interface NotePresenter {
    void onActionPressed(String name, String message, Date created);
}
