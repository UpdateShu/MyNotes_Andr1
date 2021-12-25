package com.example.mynotes_andr1.ui.details;

import android.os.Bundle;

import androidx.annotation.StringRes;

import com.example.mynotes_andr1.domain.Note;

import java.util.Date;

public interface NoteDetailsView {

    void showProgress();

    void hideProgress();

    void setActionButtonText(@StringRes int title);

    void setTitle(@StringRes int title);

    void setName(String name);

    void setDescription(String description);

    void setCreated(Date created);

    void actionCompleted(String key, Bundle bundle);
}
