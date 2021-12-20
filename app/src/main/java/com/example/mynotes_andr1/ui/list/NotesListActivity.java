package com.example.mynotes_andr1.ui.list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.res.Configuration;
import android.os.Bundle;

import com.example.mynotes_andr1.R;
import com.example.mynotes_andr1.domain.Note;
import com.example.mynotes_andr1.domain.NoteFolder;
import com.example.mynotes_andr1.ui.details.NoteDetailsFragment;

public class NotesListActivity extends AppCompatActivity {

    public static final String EXTRA_FOLDER = "EXTRA_FOLDER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
        }
        else {
            if (savedInstanceState == null) {
                NoteFolder folder = getIntent().getParcelableExtra(EXTRA_FOLDER);

                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.notes_list_container, NotesDetailsListFragment.newInstance(folder))
                        .commit();
            }
        }
    }
}