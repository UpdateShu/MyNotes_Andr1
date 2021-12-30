package com.example.mynotes_andr1.ui.editor;

import java.util.Date;

public interface EditNotePresenter {
    void onActionPressed(String name, String link, String description, Date created);
}
