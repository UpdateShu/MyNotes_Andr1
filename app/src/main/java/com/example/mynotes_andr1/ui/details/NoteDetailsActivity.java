package com.example.mynotes_andr1.ui.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.res.Configuration;
import android.os.Bundle;

import com.example.mynotes_andr1.R;
import com.example.mynotes_andr1.domain.Note;

public class NoteDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_NOTE = "EXTRA_NOTE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
        }
        else {
            if (savedInstanceState == null) {
                Note note = getIntent().getParcelableExtra(EXTRA_NOTE);

                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.note_details_container, NoteDetailsFragment.newInstance(note))
                        .commit();
            }
        }
    }
}