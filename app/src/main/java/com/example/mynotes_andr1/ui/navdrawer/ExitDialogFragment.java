package com.example.mynotes_andr1.ui.navdrawer;

import android.content.res.Resources;

import com.example.mynotes_andr1.R;

public class ExitDialogFragment extends BaseAlertDialogFragment {

    private static final String TAG = "ExitDialogFragment";
    private static final String KEY_RESULT = "ExitDialogFragment_KEY_RESULT";

    @Override
    public String getDialogTag() {
        return TAG;
    }

    @Override
    public String getKeyResult() {
        return KEY_RESULT;
    }

    public static ExitDialogFragment newInstance() {
        return new ExitDialogFragment();
    }

    public static ExitDialogFragment newInstance(Resources res) {
        ExitDialogFragment fragment = new ExitDialogFragment();
        setArguments(fragment, res.getString(R.string.exit_dialog_title),
                res.getString(R.string.exit_dialog_message), res.getString(R.string.exit_dialog_action));
        return fragment;
    }
}
