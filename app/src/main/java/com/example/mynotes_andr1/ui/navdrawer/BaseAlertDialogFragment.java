package com.example.mynotes_andr1.ui.navdrawer;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mynotes_andr1.R;

public abstract class BaseAlertDialogFragment extends DialogFragment {

    public static final String ARG_BUTTON = "ARG_BUTTON";

    public abstract String getDialogTag();
    public abstract String getKeyResult();

    private static String ARG_TITLE = "ARG_TITLE";
    private static String ARG_MESSAGE = "ARG_MESSAGE";
    private static String ARG_POSITIVE_BUTTON = "ARG_POSITIVE_BUTTON";

    protected static void setArguments(DialogFragment fragment, String title, String message, String okTitle) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TITLE, title);
        bundle.putString(ARG_MESSAGE, message);
        bundle.putString(ARG_POSITIVE_BUTTON, okTitle);
        fragment.setArguments(bundle);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Bundle bundle = new Bundle();
                bundle.putInt(ARG_BUTTON, which);

                getParentFragmentManager()
                        .setFragmentResult(getKeyResult(), bundle);
            }
        };

        String title = requireArguments().getString(ARG_TITLE);
        String message = requireArguments().getString(ARG_MESSAGE);
        String ok = requireArguments().getString(ARG_POSITIVE_BUTTON);
        return new AlertDialog.Builder(requireContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(ok, clickListener)
                .setNegativeButton(R.string.negative_button, clickListener)
                .setCancelable(false)
                .create();
    }
}
