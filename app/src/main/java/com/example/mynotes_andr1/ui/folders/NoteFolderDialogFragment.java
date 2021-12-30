package com.example.mynotes_andr1.ui.folders;

import android.content.res.Resources;

import com.example.mynotes_andr1.R;
import com.example.mynotes_andr1.ui.navdrawer.BaseAlertDialogFragment;
import com.example.mynotes_andr1.ui.notes.NoteDialogFragment;

public class NoteFolderDialogFragment extends BaseAlertDialogFragment {

    private static final String TAG = "NoteFolderDialogFragment";
    private static final String KEY_RESULT = "NoteFolderDialogFragment_KEY_RESULT";

    @Override
    public String getDialogTag() {
        return TAG;
    }

    @Override
    public String getKeyResult() {
        return KEY_RESULT;
    }

    public static NoteFolderDialogFragment newInstance() {
        return new NoteFolderDialogFragment();
    }

    public static NoteFolderDialogFragment newInstance(Resources res, String folderName) {
        NoteFolderDialogFragment fragment = new NoteFolderDialogFragment();
        setArguments(fragment, res.getString(R.string.delete_dialog_title),
                res.getString(R.string.delete_folder) + " '" + folderName + "'?", res.getString(R.string.delete_dialog_ok));
        return fragment;
    }
}
