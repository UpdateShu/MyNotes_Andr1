package com.example.mynotes_andr1.ui.navdrawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.mynotes_andr1.R;
import com.example.mynotes_andr1.domain.Note;
import com.example.mynotes_andr1.domain.NoteFolder;
import com.example.mynotes_andr1.ui.details.NoteDetailsFragment;
import com.example.mynotes_andr1.ui.folders.NoteFoldersFragment;
import com.example.mynotes_andr1.ui.list.NotesInfoFragment;
import com.example.mynotes_andr1.ui.list.NotesEditFragment;
import com.example.mynotes_andr1.ui.search.SearchFragment;
import com.example.mynotes_andr1.ui.settings.SettingsFragment;
import com.google.android.material.navigation.NavigationView;

public class NavDrawerActivity extends AppCompatActivity implements NavDrawerHost {

    private static final String ARG_NOTE = "ARG_NOTE";

    private DrawerLayout drawer;
    private NoteFolder selectedFolder;
    private Note selectedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        drawer = findViewById(R.id.drawer);

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
                        showFoldersList();
                        drawer.closeDrawer(GravityCompat.START);
                        return true;

                    case R.id.action_notes:
                        showNoteList();
                        drawer.closeDrawer(GravityCompat.START);
                        return true;

                    case R.id.action_settings:
                        showSettings();
                        drawer.closeDrawer(GravityCompat.START);
                        return true;

                    case R.id.action_exit:
                        ExitDialogFragment.newInstance().show(getSupportFragmentManager(), ExitDialogFragment.TAG);
                }
                return false;
            }
        });

        getSupportFragmentManager()
                .setFragmentResultListener(NoteFoldersFragment.KEY_RESULT, this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        selectedFolder = result.getParcelable(NoteFoldersFragment.ARG_FOLDER);

                        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            showFoldersDetails();
                        } else {
                            //Intent intent = new Intent(NavDrawerActivity.this, NotesListActivity.class);
                            //intent.putExtra(NotesListActivity.EXTRA_FOLDER, selectedFolder);
                            //startActivity(intent);
                        }
                    }
                });

        getSupportFragmentManager()
                .setFragmentResultListener(NotesEditFragment.KEY_RESULT, this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        selectedNote = result.getParcelable(NotesEditFragment.ARG_NOTE);
                        showNote(selectedNote);
                    }
                });

        getSupportFragmentManager()
                .setFragmentResultListener(ExitDialogFragment.EXIT_RESULT, this,
                        new FragmentResultListener() {
                            @Override
                            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                                switch (result.getInt(ExitDialogFragment.EXIT_BUTTON)) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        System.exit(0);
                                        break;
                                    case DialogInterface.BUTTON_NEGATIVE:
                                        break;
                                }
                            }
                        });

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

    void showSearch() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_search, new SearchFragment(), SearchFragment.TAG)
                .commit();
    }

    void showNoteList() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.feature_container, new NotesEditFragment(), NotesEditFragment.TAG);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            transaction.replace(R.id.details_container, new NoteDetailsFragment(), NoteDetailsFragment.TAG);
        }
        transaction.commit();
    }

    @Override
    public void showNote(Note note) {
        selectedNote = note;
        Bundle bundle = new Bundle();
        bundle.putParcelable(NoteDetailsFragment.ARG_NOTE, selectedNote);
        getSupportFragmentManager()
            .setFragmentResult(NoteDetailsFragment.KEY_RESULT, bundle);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

        }
        else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.feature_container, new NoteDetailsFragment(), NoteDetailsFragment.TAG)
                    .addToBackStack(null)
                    .commit();
        }
    }

    void showFoldersList() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.feature_container, new NoteFoldersFragment(), NoteFoldersFragment.TAG);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            transaction.replace(R.id.details_container, new NotesInfoFragment(), NotesInfoFragment.TAG);
        }
        transaction.commit();
    }

    void showFoldersDetails() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(NotesInfoFragment.ARG_FOLDER, selectedFolder);
        getSupportFragmentManager()
                .setFragmentResult(NotesInfoFragment.KEY_RESULT, bundle);
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