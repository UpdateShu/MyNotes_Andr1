package com.example.mynotes_andr1.ui.navdrawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.mynotes_andr1.R;
import com.example.mynotes_andr1.domain.Note;
import com.example.mynotes_andr1.domain.NoteFolder;
import com.example.mynotes_andr1.ui.editor.EditNoteFragment;
import com.example.mynotes_andr1.ui.folders.NoteFoldersFragment;
import com.example.mynotes_andr1.ui.notes.NotesFragment;
import com.example.mynotes_andr1.ui.search.SearchFragment;
import com.example.mynotes_andr1.ui.settings.SettingsFragment;
import com.google.android.material.navigation.NavigationView;

import java.io.Serializable;

public class NavDrawerActivity extends AppCompatActivity implements NavDrawerHost {

    private static final String ARG_FOLDER = "ARG_FOLDER";
    private static final String ARG_NOTE = "ARG_NOTE";
    private static final String ARG_TAB = "ARG_TAB";

    enum NotesTab implements Serializable {
        DEFAULT,
        NOTES_TAB,
        FOLDERS_TAB,
        SETTINGS_TAB,
    }

    private DrawerLayout drawer;
    private FragmentContainerView detailsView;

    private NotesTab selectedTab = NotesTab.DEFAULT;
    private NoteFolder selectedFolder = null;
    private Note selectedNote = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);

        drawer = findViewById(R.id.drawer);
        detailsView = findViewById(R.id.details_container);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(ARG_NOTE)) {
                selectedNote = savedInstanceState.getParcelable(ARG_NOTE);
            }
            if (savedInstanceState.containsKey(ARG_FOLDER)) {
                selectedFolder = savedInstanceState.getParcelable(ARG_FOLDER);
            }
            if (savedInstanceState.containsKey(ARG_TAB)) {
                NotesTab tab = (NotesTab)savedInstanceState.getSerializable(ARG_TAB);
                if (tab != selectedTab) {
                    setSelectedTab(tab);
                }
            }
        }
        else {
            setSelectedTab(NotesTab.NOTES_TAB);
        }
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_search:
                        showSearch();
                        drawer.closeDrawer(GravityCompat.START);
                        return true;

                    case R.id.action_folders:
                        setSelectedTab(NotesTab.FOLDERS_TAB);
                        drawer.closeDrawer(GravityCompat.START);
                        return true;

                    case R.id.action_notes:
                        setSelectedTab(NotesTab.NOTES_TAB);
                        drawer.closeDrawer(GravityCompat.START);
                        return true;

                    case R.id.action_settings:
                        setSelectedTab(NotesTab.SETTINGS_TAB);
                        drawer.closeDrawer(GravityCompat.START);
                        return true;

                    case R.id.action_exit:
                        ExitDialogFragment dialog = ExitDialogFragment.newInstance(getResources());
                        dialog.show(getSupportFragmentManager(), dialog.getDialogTag());
                }
                return false;
            }
        });
        getSupportFragmentManager()
                .setFragmentResultListener(ExitDialogFragment.newInstance(getResources()).getKeyResult(), this,
                        new FragmentResultListener() {
                            @Override
                            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                                switch (result.getInt(ExitDialogFragment.ARG_BUTTON)) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        finish();
                                        break;
                                    case DialogInterface.BUTTON_NEGATIVE:
                                        break;
                                }
                            }
                        });

    }

    void setSelectedTab(NotesTab tab) {
        selectedTab = tab;
        switch (tab) {
            case NOTES_TAB:
                showNoteList();
                if (selectedNote != null) {
                    showNote(selectedNote);
                }
                getSupportFragmentManager()
                        .setFragmentResultListener(NotesFragment.KEY_RESULT, this, new FragmentResultListener() {
                            @Override
                            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                                Note note = result.getParcelable(NotesFragment.ARG_NOTE);
                                if (note != null) {
                                    selectedFolder = result.getParcelable(NotesFragment.ARG_FOLDER);
                                    showNote(note);
                                }
                            }
                        });
                break;

            case FOLDERS_TAB:
                showFoldersList();
                getSupportFragmentManager()
                        .setFragmentResultListener(NoteFoldersFragment.KEY_RESULT, this, new FragmentResultListener() {
                            @Override
                            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                                NoteFolder folder = result.getParcelable(NoteFoldersFragment.ARG_FOLDER);
                                if (folder != null) {
                                    selectedFolder = folder;
                                    setSelectedTab(NotesTab.NOTES_TAB);
                                }
                            }
                        });
                break;

            case SETTINGS_TAB:
                showSettings();
                break;
        }
    }

    @Override
    public void supplyToolbar(Toolbar toolbar) {

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.nav_app_bar_open_drawer_description,
                R.string.nav_app_bar_close_drawer_description);

        drawer.addDrawerListener(toggle);

        toggle.syncState();
    }

    @Override
    public void setDetailsVisible(boolean visible) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            detailsView.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        }
    }

    void showSearch() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_search, new SearchFragment(), SearchFragment.TAG)
                .commit();
    }

    void showFoldersList() {
        selectedNote = null;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.feature_container, new NoteFoldersFragment(), NoteFoldersFragment.TAG);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //transaction.replace(R.id.details_container, new NotesEditFragment(), NotesEditFragment.TAG);
            detailsView.setVisibility(View.INVISIBLE);
        }
        transaction.commit();
    }

    void showNoteList() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.feature_container, NotesFragment.newInstance(selectedFolder), NotesFragment.TAG);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            transaction.replace(R.id.details_container, EditNoteFragment.newInstance(selectedNote, selectedFolder), EditNoteFragment.TAG);
            detailsView.setVisibility(View.VISIBLE);
        }
        if (selectedFolder != null) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    @Override
    public void showNote(Note note) {
        selectedNote = note;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.feature_container, EditNoteFragment.newInstance(selectedNote, selectedFolder), EditNoteFragment.TAG)
                    .addToBackStack(null)
                    .commit();
        }
        else {
            Bundle bundle = new Bundle();
            bundle.putParcelable(EditNoteFragment.ARG_NOTE, selectedNote);
            bundle.putParcelable(EditNoteFragment.ARG_FOLDER, selectedFolder);
            getSupportFragmentManager()
                    .setFragmentResult(EditNoteFragment.KEY_RESULT, bundle);
        }
    }

    /*@Override
    public void showFolder(NoteFolder folder) {
        selectedFolder = folder;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.feature_container, NotesEditFragment.newInstance(selectedFolder), NotesEditFragment.TAG)
                    .addToBackStack(null)
                    .commit();
        }
        else {
            Bundle bundle = new Bundle();
            bundle.putParcelable(NotesEditFragment.ARG_FOLDER, selectedFolder);
            getSupportFragmentManager()
                    .setFragmentResult(NotesEditFragment.KEY_RESULT, bundle);
        }
    }*/

    void showSettings() {
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.feature_container, new SettingsFragment(), SettingsFragment.TAG);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            detailsView.setVisibility(View.VISIBLE);
        }
        Fragment searchfragment = getSupportFragmentManager()
                .findFragmentByTag(SearchFragment.TAG);
        if (searchfragment != null) {
            trans.remove(searchfragment);
        }
        trans.commit();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (selectedNote != null) {
            outState.putParcelable(ARG_NOTE, selectedNote);
        }
        if (selectedFolder != null) {
            outState.putParcelable(ARG_FOLDER, selectedFolder);
        }
        outState.putSerializable(ARG_TAB, selectedTab);
    }
}