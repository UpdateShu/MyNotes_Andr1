package com.example.mynotes_andr1.ui.list;

import android.os.Bundle;

import com.example.mynotes_andr1.domain.Note;
import com.example.mynotes_andr1.ui.navdrawer.BaseAlertDialogFragment;

public class NoteListDialogFragment extends BaseAlertDialogFragment {
    public static final String TAG = "NoteListDialogFragment";
    public static final String NOTES_RESULT = "NoteListDialogFragment_RESULT";
    public static final String ARG_BUTTON = "ARG_BUTTON";

    private static final String ARG_TITLE = "ARG_TITLE";
    private static final String ARG_MESSAGE = "ARG_MESSAGE";
    private static final String ARG_POSITIVE_BUTTON = "ARG_POSITIVE_BUTTON";

    public static NoteListDialogFragment newRemovingDialog(Note note) {
        return newInstance("Подтверждение удаления",
                "Удалить заметку " + note.getName() +"?", "Удалить");
    }

    public static NoteListDialogFragment newInstance(String title, String message, String okText) {
        NoteListDialogFragment dialogFragment = new NoteListDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TITLE, title);
        bundle.putString(ARG_MESSAGE, message);
        bundle.putString(ARG_POSITIVE_BUTTON, okText);
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }
}
