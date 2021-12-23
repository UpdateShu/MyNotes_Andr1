package com.example.mynotes_andr1.ui.list;

import android.content.Context;
import android.content.res.Resources;

import com.example.mynotes_andr1.R;
import com.example.mynotes_andr1.ui.navdrawer.BaseAlertDialogFragment;

public class NoteDialogFragment extends BaseAlertDialogFragment {

    private static final String TAG = "NoteDialogFragment";
    private static final String KEY_RESULT = "NoteDialogFragment_KEY_RESULT";

    @Override
    public String getDialogTag() {
        return TAG;
    }

    @Override
    public String getKeyResult() {
        return KEY_RESULT;
    }

    public static NoteDialogFragment newInstance() {
        return new NoteDialogFragment();
    }

    public static NoteDialogFragment newInstance(Resources res, String note) {
        NoteDialogFragment fragment = new NoteDialogFragment();
        setArguments(fragment, res.getString(R.string.delete_dialog_title),
                res.getString(R.string.delete_note) + " '" + note + "'?", res.getString(R.string.delete_dialog_ok));
        return fragment;
    }
}
