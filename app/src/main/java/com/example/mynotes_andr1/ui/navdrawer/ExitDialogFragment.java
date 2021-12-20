package com.example.mynotes_andr1.ui.navdrawer;

import android.os.Bundle;

import com.example.mynotes_andr1.R;

public class ExitDialogFragment extends BaseAlertDialogFragment {

    public static final String TAG = "ExitDialogFragment";
    public static final String EXIT_RESULT = "ExitDialogFragment_RESULT";
    public static final String EXIT_BUTTON = "EXIT_BUTTON";

    public static ExitDialogFragment newInstance() {
        KEY_RESULT = EXIT_RESULT;
        ARG_BUTTON = EXIT_BUTTON;

        ExitDialogFragment dialogFragment = new ExitDialogFragment();
        Bundle bundle = new Bundle();

        bundle.putString(ARG_TITLE, "Подтверждение выхода");//невозможно получить стат строку из R.string.exit_dialog_title));
        bundle.putString(ARG_MESSAGE, "Выйти из программы?");
        bundle.putString(ARG_POSITIVE_BUTTON, "Выход");
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }
}
