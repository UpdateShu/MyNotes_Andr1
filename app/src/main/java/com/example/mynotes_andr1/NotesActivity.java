package com.example.mynotes_andr1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.example.mynotes_andr1.domain.Note;
import com.example.mynotes_andr1.ui.details.NoteDetailsActivity;
import com.example.mynotes_andr1.ui.details.NoteDetailsFragment;
import com.example.mynotes_andr1.ui.list.NotesDetailsListFragment;
import com.example.mynotes_andr1.ui.list.NotesListFragment;
import com.example.mynotes_andr1.ui.folders.NoteFoldersListFragment;

public class NotesActivity extends AppCompatActivity {

    private static final String ARG_NOTE = "ARG_NOTE";

    public Note selectedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        if (savedInstanceState != null && savedInstanceState.containsKey(ARG_NOTE)) {
            selectedNote = savedInstanceState.getParcelable(ARG_NOTE);

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                showDetails();
            }
        }

        getSupportFragmentManager()
                .setFragmentResultListener(NotesListFragment.KEY_RESULT, this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        selectedNote = result.getParcelable(NotesListFragment.ARG_NOTE);

                                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                                    showDetails();
                                } else {
                                    Intent intent = new Intent(NotesActivity.this, NoteDetailsActivity.class);
                                    intent.putExtra(NoteDetailsActivity.EXTRA_NOTE, selectedNote);
                                    startActivity(intent);
                                }
                            }
                        });


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (selectedNote != null) {
            outState.putParcelable(ARG_NOTE, selectedNote);
        }
    }

    public void showFolders() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new NoteFoldersListFragment(), NoteFoldersListFragment.TAG);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            transaction.replace(R.id.details_container, new NotesDetailsListFragment(), NotesDetailsListFragment.TAG);
        }
        transaction.commit();
    }

    public void confirmFolderSelection() {
        showNoteList();
    }

    void showNoteList() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new NotesListFragment(), NotesListFragment.TAG);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            transaction.replace(R.id.details_container, new NoteDetailsFragment(), NoteDetailsFragment.TAG);
        }
        transaction.commit();
    }

    void showDetails() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(NoteDetailsFragment.ARG_NOTE, selectedNote);
        getSupportFragmentManager()
                .setFragmentResult(NoteDetailsFragment.KEY_RESULT, bundle);
    }
}