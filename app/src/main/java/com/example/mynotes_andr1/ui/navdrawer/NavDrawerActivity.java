package com.example.mynotes_andr1.ui.navdrawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.mynotes_andr1.R;
import com.example.mynotes_andr1.domain.Note;
import com.example.mynotes_andr1.domain.NoteFolder;
import com.example.mynotes_andr1.ui.details.NoteDetailsActivity;
import com.example.mynotes_andr1.ui.details.NoteDetailsFragment;
import com.example.mynotes_andr1.ui.folders.NoteFoldersListFragment;
import com.example.mynotes_andr1.ui.list.NotesListActivity;
import com.example.mynotes_andr1.ui.list.NotesListFragment;
import com.example.mynotes_andr1.ui.search.SearchFragment;
import com.example.mynotes_andr1.ui.settings.SettingsFragment;
import com.google.android.material.navigation.NavigationView;

public class NavDrawerActivity extends AppCompatActivity {

    private static final String ARG_NOTE = "ARG_NOTE";

    public NoteFolder selectedFolder;
    public Note selectedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);

        showNoteList();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_search:
                        showSearch();
                        return true;
                    case R.id.action_notes:
                        showNoteList();
                        return true;
                    case R.id.action_settings:
                        showSettings();
                        return true;
                }
                return false;
            }
        });

        getSupportFragmentManager()
                .setFragmentResultListener(NoteFoldersListFragment.KEY_RESULT, this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        selectedFolder = result.getParcelable(NoteFoldersListFragment.ARG_FOLDER);

                        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            showFoldersDetails();
                        } else {
                            Intent intent = new Intent(NavDrawerActivity.this, NotesListActivity.class);
                            intent.putExtra(NotesListActivity.EXTRA_FOLDER, selectedFolder);
                            startActivity(intent);
                        }
                    }
                });

        getSupportFragmentManager()
                .setFragmentResultListener(NotesListFragment.KEY_RESULT, this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        selectedNote = result.getParcelable(NotesListFragment.ARG_NOTE);

                        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            showNoteDetails();
                        } else {
                            Intent intent = new Intent(NavDrawerActivity.this, NoteDetailsActivity.class);
                            intent.putExtra(NoteDetailsActivity.EXTRA_NOTE, selectedNote);
                            startActivity(intent);
                        }
                    }
                });
    }

    void showSearch() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_search, new SearchFragment(), SearchFragment.TAG)
                .commit();
    }

    void showNoteList() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.feature_container, new NotesListFragment(), NotesListFragment.TAG);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            transaction.replace(R.id.details_container, new NoteDetailsFragment(), NoteDetailsFragment.TAG);
        }
        transaction.commit();
    }

    void showNoteDetails() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(NoteDetailsFragment.ARG_NOTE, selectedNote);
        getSupportFragmentManager()
                .setFragmentResult(NoteDetailsFragment.KEY_RESULT, bundle);
    }

    void showFoldersList() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.feature_container, new NoteFoldersListFragment(), NoteFoldersListFragment.TAG);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            transaction.replace(R.id.details_container, new NotesListFragment(), NotesListFragment.TAG);
        }
        transaction.commit();
    }

    void showFoldersDetails() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(NotesListFragment.ARG_FOLDER, selectedFolder);
        getSupportFragmentManager()
                .setFragmentResult(NotesListFragment.KEY_RESULT, bundle);
    }

    void showSettings() {
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.feature_container, new SettingsFragment(), SettingsFragment.TAG);
        Fragment searchfragment = getSupportFragmentManager()
                .findFragmentByTag(SearchFragment.TAG);
        if (searchfragment != null) {
            trans.remove(searchfragment);
        }
        trans.commit();
    }
}