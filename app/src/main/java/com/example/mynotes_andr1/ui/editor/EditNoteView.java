package com.example.mynotes_andr1.ui.editor;

import android.os.Bundle;

import androidx.annotation.StringRes;

import java.util.Date;

public interface EditNoteView {

    void showProgress();

    void hideProgress();

    void setActionButtonText(@StringRes int title);

    void setTitle(@StringRes int title);

    void setName(String name);

    void setDescription(String description);

    void setCreated(Date created);

    void actionCompleted(String key, Bundle bundle);
}
